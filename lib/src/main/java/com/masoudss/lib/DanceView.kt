package com.masoudss.lib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import com.masoudss.lib.audiovisulizer.base.BaseVisualizer
import com.masoudss.lib.utils.Utils

/**
 * create by 胡汉君  date 2021/4/7-18-23
 *email ：huhanjun@bytedance.com
 */

/***********************************************************
 * * Copyright (C), 2020-2030, Bytedance edu Corp., Ltd.
 * * File:  - DanceView.kt
 * * Description: build this module.
 * * Date : 2021/4/7-18-23
 * * Author: 胡汉君@Apps.minddance

 ****************************************************************/
class DanceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseVisualizer(context, attrs, defStyleAttr) {

    private lateinit var mPaint: Paint//画笔
    private var danceColor = Color.YELLOW

    /**
     * 画布的宽度
     */
    private var mCanvasWidth = 0

    /**
     *画布的高度
     */
    private var mCanvasHeight = 0

    /**
     *音频块高度
     */
    private var danceHeight = Utils.dp(context, 4).toInt()

    /**
     *音频块的宽度
     */
    private var danceWidth = Utils.dp(context, 12).toInt()
    private var miniNum = 1
    private var maxNum = 40
    private val mDanceRect = RectF()
    private var colorStart = Color.WHITE
    private var colorCenter = Color.YELLOW
    private var colorEnd = Color.WHITE

    /**
     *默认的渐变个数
     */
    private var shaderNum = 3

    /**
     * 获取可以用的宽度
     */
    private fun getAvailableWith() = mCanvasWidth - paddingLeft - paddingRight

    /**
     *获取可以用的高度
     */
    private fun getAvailableHeight() = mCanvasHeight - paddingTop - paddingBottom

    /**
     *音频块之间的间距
     */
    var danceGap: Float = Utils.dp(context, 4)
        set(value) {
            field = value
            invalidate()
        }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DanceView, 0, 0)
        danceColor = ta.getColor(R.styleable.DanceView_dance_color, danceColor)
        danceWidth =
            ta.getDimension(R.styleable.DanceView_dance_width, danceWidth.toFloat()).toInt()
        danceGap = ta.getDimension(R.styleable.DanceView_dance_gap, danceGap)
        colorStart = ta.getColor(R.styleable.DanceView_color_start, colorStart)
        colorEnd = ta.getColor(R.styleable.DanceView_color_end, colorEnd)
        colorCenter = ta.getColor(R.styleable.DanceView_color_center, colorCenter)
        shaderNum = ta.getInteger(R.styleable.DanceView_shader_num, shaderNum)
        maxNum = ta.getInteger(R.styleable.DanceView_max_dance_num, maxNum)
        miniNum = ta.getInteger(R.styleable.DanceView_min_dance_num, miniNum)
        ta.recycle()
        init()
    }

    private fun init() {
        mPaint = Paint()//初始化画笔工具
        mPaint.isAntiAlias = true//抗锯齿
        mPaint.color = danceColor//画笔颜色
        mPaint.strokeJoin = Paint.Join.ROUND//频块圆角
        mPaint.strokeCap = Paint.Cap.ROUND//频块圆角
        mPaint.strokeWidth = 10F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasWidth = w
        mCanvasHeight = h
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "mRawAudioBytes ---- mRawAudioBytes size ${mRawAudioBytes?.size}")
        /**
         * 先计算当前宽度能够放下多少个音频块
         */
        val widthNum = (getAvailableWith() / (danceGap + danceWidth)).toInt()
        Log.d(
            TAG,
            "widthNum $widthNum"
        )
        /**
         * 算出横向能放多少后，进行绘制
         */

        /**
         * 绘制的时候用于标记开始绘制的位置
         */
        var lastDanceRight = paddingLeft.toFloat()
        if (widthNum > 0 && mRawAudioBytes != null && mRawAudioBytes.isNotEmpty())
            for (i in 0 until widthNum) {
                //先算出当前高度，然后再算这个高度能放下多少个音频块
                val num = (mCanvasHeight / (danceHeight + danceGap)).toInt()
                val index = (mRawAudioBytes.size) * (i.toFloat() / widthNum)
                val b = (mRawAudioBytes[index.toInt()] + 128).toFloat() / 255f
                var heightNum =
                    (b * num).toInt()
                if (heightNum < miniNum) {
                    heightNum = miniNum
                }
                if (heightNum > maxNum) {
                    heightNum = maxNum
                }
                //拿到最顶部的高度
                var lastHeight = mCanvasHeight - paddingStart.toFloat()
                Log.d(
                    TAG,
                    "heightNum $heightNum lastHeight $lastHeight lastDanceRight $lastDanceRight ${mRawAudioBytes[i]} $num $b $index"
                )
                for (j in 0 until heightNum) {
                    mDanceRect.set(
                        lastDanceRight,
                        lastHeight - danceHeight,
                        lastDanceRight + danceWidth,
                        lastHeight
                    )
                    mPaint.shader = null
                    if (j >= heightNum - shaderNum) {
                        val backGradient = LinearGradient(
                            lastDanceRight,
                            lastHeight - danceHeight,
                            lastDanceRight + danceWidth,
                            lastHeight,
                            intArrayOf(colorStart, colorCenter, colorEnd),
                            null,
                            Shader.TileMode.CLAMP
                        )
                        mPaint.shader = backGradient
                    }
                    canvas?.drawRoundRect(mDanceRect, 8f, 8f, mPaint)
                    lastHeight -= (danceHeight + danceGap)
                }
                lastDanceRight += danceWidth + danceGap
            }

    }
}