package com.gst.mykotlintest.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.gst.mykotlintest.R
import com.gst.mykotlintest.util.LogUtil

/**
 * author: GuoSongtao on 2017/12/19 09:46
 * email: 157010607@qq.com
 */
class LoadingView2 @JvmOverloads
constructor(var mCtx: Context, var attrs: AttributeSet? = null, var defStyle: Int = 1)
    : View(mCtx, attrs, defStyle) {
    var mPaint: Paint
    var mBitmapHeight: Int = 0
    var mBitmapWidth: Int = 0
    private var currAnimStatus: AnimationStatus? = null
    private var isStart = false

    init {
        mPaint = Paint()
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        if (currAnimStatus == null) {
            currAnimStatus = RotateStatus()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (currAnimStatus == null) {
            currAnimStatus = RotateStatus()
        }

        LogUtil.i("onDraw currAnimStatus" + currAnimStatus)
        currAnimStatus!!.drawCanvas(canvas)
        super.onDraw(canvas)
    }

    fun startAnimator() {
        currAnimStatus!!.start()
//        when (currAnimStatus) {
//            is RotateStatus -> (currAnimStatus as RotateStatus).start()
//            is RotateStatus2 -> (currAnimStatus as RotateStatus2).start()
//        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    interface AnimationStatus {
        fun drawCanvas(canvas: Canvas?)
        fun cancel()
        fun start()
    }


    /**
     * //第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
     * firstAnim = ObjectAnimator.ofFloat(text, "alpha", 0f, 1f);
     * animator属性动画，view的属性会发生变化  Animation视图动画，属性会不发生变化
     * 所谓属性动画：改变一切能改变的对象的属性值，不同于补间动画：只能改变 alpha，scale（scaleX,scaleY），rotate（rotationX，rotationY），translate（），backgroundColor。听着有点抽象，举例子说明
     */
    internal inner class RotateStatus : AnimationStatus {
        var vAnim: ValueAnimator
        var mCurrentRotationAngle: Float = 0F

        init {
            LogUtil.i("RotateStatus_init  mCurrentRotationAngle=" + mCurrentRotationAngle)
            vAnim = ValueAnimator.ofFloat(0F, 360F)
            vAnim.duration = 1000
            vAnim.repeatCount = 5
            vAnim.interpolator = LinearInterpolator()
            vAnim.addUpdateListener { animation ->
                mCurrentRotationAngle = animation!!.animatedValue as Float
                invalidate()
                LogUtil.i("RotateStatus_onAnimationUpdate  mCurrentRotationAngle=" + mCurrentRotationAngle)
            }

            //动画结束开始反方向旋转
            vAnim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    currAnimStatus = RotateStatus2()
                    isStart = false
                    currAnimStatus!!.start()
                }

            })
            vAnim.startDelay = 500
        }

        override fun start() {
            if (!isStart) {
                isStart = true
                vAnim.start()
            }
        }

        override fun cancel() {
            vAnim!!.cancel()
            isStart = false
        }

        override fun drawCanvas(canvas: Canvas?) {
            clearCanvas(canvas)

            var matrix = Matrix()
            matrix.preRotate(mCurrentRotationAngle)

            var bitmapRes = BitmapFactory.decodeResource(mCtx.resources, R.mipmap.default_downloading)
            mBitmapWidth = bitmapRes.width
            mBitmapHeight = bitmapRes.height
            var dstBitmap = Bitmap.createBitmap(bitmapRes, 0, 0, mBitmapWidth, mBitmapHeight, matrix, true)

            var left = (canvas!!.width - dstBitmap!!.width) / 2
            var top = (canvas!!.height - dstBitmap!!.height) / 2
            var srcRect = Rect(0, 0, dstBitmap!!.width, dstBitmap!!.height)
            var dstRect = Rect(left, top, left + dstBitmap!!.width, top + dstBitmap!!.height)
            canvas!!.drawBitmap(dstBitmap, srcRect, dstRect, mPaint)

            if (dstBitmap != null && !dstBitmap!!.isRecycled()) {
                dstBitmap!!.recycle()
                dstBitmap = null
            }
            if (bitmapRes != null && !bitmapRes!!.isRecycled()) {
                bitmapRes!!.recycle()
                bitmapRes = null
            }

        }

        fun rotate1(canvas: Canvas?) {

            var left = (canvas!!.width - mBitmapWidth) / 2
            var top = (canvas!!.height - mBitmapHeight) / 2
            var srcRect = Rect(0, 0, mBitmapWidth, mBitmapHeight)
            var dstRect = Rect(left, top, left + mBitmapWidth, top + mBitmapHeight)
//            canvas!!.drawBitmap(bitmapRes, srcRect, dstRect, mPaint)
        }


        fun rotate(canvas: Canvas?) {
            val x = canvas!!.width / 2F //画布宽度的一半
            val y = canvas!!.height / 2F//画布高度的一半

            clearCanvas(canvas)
//            canvas!!.clear(0, 0, canvas.width, canvas.height)//先清掉画布上的内容
            canvas!!.translate(x, y)//将绘图原点移到画布中点
            canvas!!.rotate((Math.PI / 180 * 5).toFloat())//旋转角度
            canvas!!.translate(-x, -y)//将画布原点移动
//            canvas!!.drawBitmap(bitmapRes, Matrix(), mPaint)//绘制图片
        }

    }


    /**
     * //第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
     * firstAnim = ObjectAnimator.ofFloat(text, "alpha", 0f, 1f);
     * animator属性动画，view的属性会发生变化  Animation视图动画，属性会不发生变化
     * 所谓属性动画：改变一切能改变的对象的属性值，不同于补间动画：只能改变 alpha，scale（scaleX,scaleY），rotate（rotationX，rotationY），translate（），backgroundColor。听着有点抽象，举例子说明
     */
    internal inner class RotateStatus2 : AnimationStatus {
        var vAnim: ValueAnimator
        var mCurrentRotationAngle: Float = 360F

        init {
            LogUtil.i("RotateStatus_init  mCurrentRotationAngle=" + mCurrentRotationAngle)
            vAnim = ValueAnimator.ofFloat(360F, 0F)
            vAnim.duration = 1000
            vAnim.repeatCount = ValueAnimator.INFINITE
            vAnim.interpolator = LinearInterpolator()
            vAnim.addUpdateListener { animation ->
                mCurrentRotationAngle = animation!!.animatedValue as Float
                invalidate()
                LogUtil.i("RotateStatus_onAnimationUpdate  mCurrentRotationAngle=" + mCurrentRotationAngle)
            }
            vAnim.startDelay = 500
        }

        override fun start() {
            if (!isStart) {
                isStart = true
                vAnim.start()
            }
        }

        override fun cancel() {
            vAnim!!.cancel()
            isStart = false
        }

        override fun drawCanvas(canvas: Canvas?) {
            clearCanvas(canvas)

            var matrix = Matrix()
            matrix.preRotate(mCurrentRotationAngle)

            var bitmapRes = BitmapFactory.decodeResource(mCtx.resources, R.mipmap.default_downloading)
            mBitmapWidth = bitmapRes.width
            mBitmapHeight = bitmapRes.height
            var dstBitmap = Bitmap.createBitmap(bitmapRes, 0, 0, mBitmapWidth, mBitmapHeight, matrix, true)

            var left = (canvas!!.width - dstBitmap!!.width) / 2
            var top = (canvas!!.height - dstBitmap!!.height) / 2
            var srcRect = Rect(0, 0, dstBitmap!!.width, dstBitmap!!.height)
            var dstRect = Rect(left, top, left + dstBitmap!!.width, top + dstBitmap!!.height)
            canvas!!.drawBitmap(dstBitmap, srcRect, dstRect, mPaint)

            if (dstBitmap != null && !dstBitmap!!.isRecycled()) {
                dstBitmap!!.recycle()
                dstBitmap = null
            }
            if (bitmapRes != null && !bitmapRes!!.isRecycled()) {
                bitmapRes!!.recycle()
                bitmapRes = null
            }

        }
    }

    fun clearCanvas(canvas: Canvas?) {
        canvas!!.drawColor(Color.WHITE)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}