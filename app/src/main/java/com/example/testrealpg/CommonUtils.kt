package com.example.testrealpg

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class CommonUtils(var context: Context) {
    val EXTRA_LAST_APP = "EXTRA_LAST_APP"


    lateinit var usageStatsManager: UsageStatsManager

    fun getLauncherTopApp(): String {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val taskInfoList = manager.getRunningTasks(1)
            if (taskInfoList != null && taskInfoList.isNotEmpty()) {
                return taskInfoList[0].topActivity!!.packageName
            }
        } else {
            val endTime: Long = System.currentTimeMillis()
            val beginTime = endTime - 100000

            var result = ""
            val event: UsageEvents.Event = UsageEvents.Event()
            val usageEvents = usageStatsManager.queryEvents(beginTime, endTime)
            grantPermission()
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event)
                if (event.eventType != UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    result = event.packageName
                }
            }

            if (!TextUtils.isEmpty(result)) {
                return result
            }
        }
        return ""
    }

    private fun grantPermission() {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                "android:get_usage_stats",
                android.os.Process.myUid(),
                context.packageName
            )
        } else {
            appOps.checkOpNoThrow(
                "android:get_usage_stats",
                android.os.Process.myUid(),
                context.packageName
            )
        }
        if (mode != AppOpsManager.MODE_ALLOWED) {
            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }


    companion object {
        val listDangerousPermission: List<String> = arrayListOf(
            "READ_CALENDAR",
            "WRITE_CALENDAR",
            "CAMERA",
            "READ_CONTACTS",
            "WRITE_CONTACTS",
            "GET_ACCOUNTS",
            "ACCESS_FINE_LOCATION",
            "ACCESS_COARSE_LOCATION",
            "RECORD_AUDIO",
            "READ_PHONE_STATE",
            "READ_PHONE_NUMBERS ",
            "CALL_PHONE",
            "ANSWER_PHONE_CALLS ",
            "READ_CALL_LOG",
            "WRITE_CALL_LOG",
            "ADD_VOICEMAIL",
            "USE_SIP",
            "PROCESS_OUTGOING_CALLS",
            "BODY_SENSORS",
            "SEND_SMS",
            "RECEIVE_SMS",
            "READ_SMS",
            "RECEIVE_WAP_PUSH",
            "RECEIVE_MMS",
            "READ_EXTERNAL_STORAGE",
            "WRITE_EXTERNAL_STORAGE",
            "ACCESS_MEDIA_LOCATION",
            "ACCEPT_HANDOVER",
            "ACCESS_BACKGROUND_LOCATION",
            "ACTIVITY_RECOGNITION"
        )

//        fun setAnimRecycleView(v: RecyclerView) {
//            val animator = v.itemAnimator
//            if (null != animator) {
//                if (animator is SimpleItemAnimator) {
//                    animator.supportsChangeAnimations = false
//                }
//            }
//        }

        fun pxToDp(px: Int): Float {
            return px / Resources.getSystem().displayMetrics.density
        }

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }

        fun uninstallApp(context: Context, packageName: String) {
            val i = Intent(Intent.ACTION_DELETE)
            i.data = Uri.parse("package:${packageName}")
            i.putExtra(Intent.EXTRA_RETURN_RESULT, true)
            context.startActivity(i)
        }

        fun View.createBitmap(): Bitmap {
            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            Canvas(bitmap).apply {
                background?.draw(this) ?: this.drawColor(Color.WHITE)
                draw(this)
            }
            return bitmap
        }
        val Context.screenWidth: Int
            get() = resources.displayMetrics.widthPixels

        val Context.screenHeight: Int
            get() = resources.displayMetrics.heightPixels

        /**
         * Pixel and Dp Conversion
         */
        val Float.toPx get() = this * Resources.getSystem().displayMetrics.density
        val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

        val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()
        val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    }
}