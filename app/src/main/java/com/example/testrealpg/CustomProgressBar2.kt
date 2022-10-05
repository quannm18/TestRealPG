package com.example.testrealpg

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.min


class CustomProgressBar2(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    private var redProcess: Float = 0f
    private var orangeProcess: Float = 0f

    var mRectWhite = RectF(0f, 0f, width.toFloat(), height.toFloat())
    var mRectRed = RectF(0f, 0f, redProcess, height.toFloat())
    var mRectOrange = RectF(0f, 0f, orangeProcess, height.toFloat())

    init {
        redProcess = 500f
        orangeProcess = redProcess + 160f
    }

    private val mainPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.BUTT
            color = resources.getColor(R.color.teal_200)
            strokeWidth = (10).toFloat()
        }
    }
    private val redPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.BUTT
            color = resources.getColor(android.R.color.holo_red_dark)
            strokeWidth = (10).toFloat()
        }
    }
    private val orangePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.BUTT
            color = resources.getColor(android.R.color.holo_orange_dark)
            strokeWidth = (10).toFloat()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRectWhite = RectF(0f, 0f, width.toFloat() , height.toFloat())
        canvas.drawRoundRect(mRectWhite, 100f, 100f, mainPaint)
        canvas.drawRoundRect(mRectOrange, 100f, 100f, orangePaint)
        canvas.drawRoundRect(mRectRed, 100f, 100f, redPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 100
        val desiredHeight = 6
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> widthSize
        }
        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> heightSize
        }
        setMeasuredDimension(width, height)
    }

    var tempRed = 0f
    var tempOrange = 0f

    fun setProgress(redProgress: Long = 0L, total: Long = 0L, freeSpace: Long = 0L) {
        val percentRed = redProgress.toFloat() * width.toFloat() / total.toFloat()
        val percentFree = freeSpace.toFloat() * width.toFloat() / total.toFloat()
        val percentOrange = (total - freeSpace).toFloat() * width.toFloat() / total.toFloat()

        object : CountDownTimer(2000, 1) {
            override fun onTick(p0: Long) {
                CoroutineScope(Dispatchers.Default).launch {
                    if (tempRed < percentRed) {
                        tempRed += (percentRed / 70)
                    }
                    redProcess = tempRed
                    if (tempOrange < percentOrange) {
                        tempOrange += percentOrange / 70
                    }
                    orangeProcess = tempOrange
                    mRectOrange = RectF(0f, 0f, orangeProcess, height.toFloat())
                    mRectRed = RectF(0f, 0f, redProcess as Float, height.toFloat())
                    invalidate()
                }
//                setProgressAnimate(percent = redProcess, percentRed.toFloat())
            }
            override fun onFinish() {

            }

        }.start()
    }

    private fun setProgressAnimate(percent: Float, total: Float) {
        val animation = ValueAnimator.ofFloat(total, percent)
        animation.duration = 10000
        animation.addUpdateListener {
            mRectRed = RectF(0f, 0f, it.animatedValue as Float, height.toFloat())
        }
        animation.start()
    }

}