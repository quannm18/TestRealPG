package com.example.testrealpg

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.testrealpg.CommonUtils.Companion.screenHeight
import com.example.testrealpg.CommonUtils.Companion.screenWidth
import kotlin.math.min

class CustomProgressBar2(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    val widthScreen: Float = context.screenWidth.toFloat()
    val heightScreen: Float = context.screenHeight.toFloat()
    private var redProcess: Float = 0f
    private var orangeProcess: Float = 0f
    val total = 100f

    var mRectWhite = RectF(0f, 0f, widthScreen, height.toFloat())
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

        mRectWhite = RectF(0f, 0f, widthScreen, height.toFloat())
        //draw rect
        canvas.drawRoundRect(mRectWhite, 100f, 100f, mainPaint)
        canvas.drawRoundRect(mRectOrange, 100f, 100f, orangePaint)
        canvas.drawRoundRect(mRectRed!!, 100f, 100f, redPaint)
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


    fun setProgress(redProgress: Float = 0f, orangeProgress: Float = 0f) {
        this.redProcess = redProgress * widthScreen
        this.orangeProcess = redProcess + orangeProgress * widthScreen
        mRectRed = RectF(0f, 0f, redProcess, height.toFloat())
        mRectOrange = RectF(0f, 0f, orangeProcess, height.toFloat())
        invalidate()
    }

}