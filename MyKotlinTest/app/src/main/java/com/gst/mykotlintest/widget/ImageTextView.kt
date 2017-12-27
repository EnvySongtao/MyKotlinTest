package com.gst.mykotlintest.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gst.mykotlintest.R
import com.gst.mykotlintest.util.LogUtil

/**
 * author: GuoSongtao on 2017/12/19 16:01
 * email: 157010607@qq.com
 */
class ImageTextView
@JvmOverloads constructor(var mCtx: Context, var attrs: AttributeSet? = null, var defStyleAttr: Int = 0)
    : LinearLayout(mCtx, attrs, defStyleAttr) {
    //图片的资源文件引用
    private var imageSrc = -1
    //图片的宽度
    private var imageWidth = 60
    //图片的高度
    private var imageHeight = 60
    //显示的文字
    private var textStr: String? = null
    //显示的文字的大小
    private var textSize = 18F
    //显示的文字的颜色
    private var textColor: Int = 0
    //是否选中图片
    var isImageSelect: Boolean = false
    //图片view
    private var imageView: ImageView? = null
    //标题view
    private var textView: TextView? = null

    init {
        initAttrs(mCtx, attrs, defStyleAttr)
        initParams(mCtx)
        setViewValue()
    }

    fun setTextString(text: String) {
        textStr = text
        if (textView != null) textView!!.setText(text)
    }

    fun setImageResource(resourceId: Int) {
        imageSrc = resourceId
        if (imageView != null) imageView!!.setImageResource(imageSrc)
    }

    private fun setViewValue() {
        if (imageView != null) {
            if (imageSrc > 0) imageView!!.setImageResource(imageSrc)
            if (imageWidth > 0 && imageHeight > 0) {
                var layoutParams = imageView!!.layoutParams
                layoutParams.width = imageWidth
                layoutParams.height = imageHeight
                imageView!!.layoutParams = layoutParams
            }
        }

        if (textView != null) {
            textView!!.setTextColor(textColor)
            if (!TextUtils.isEmpty(textStr)) textView!!.setText(textStr)
            LogUtil.i("textSize=" + textSize + ";imageWidth=" + imageWidth + ";imageHeight=" + imageHeight)
            if (textSize > 0) textView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

    }

    /**
     * 1, var contentView = LayoutInflater.from(mCtx).inflate(R.layout.image_with_text_view, this)
     *
     * 2,var contentView = LayoutInflater.from(mCtx).inflate(R.layout.image_with_text_view, null)
     * this.addView(contentView)
     */
    private fun initParams(mCtx: Context) {

//        var contentView = LayoutInflater.from(mCtx).inflate(R.layout.image_with_text_view, this)
        var contentView = LayoutInflater.from(mCtx).inflate(R.layout.image_with_text_view, null)
        imageView = contentView.findViewById(R.id.iv_image)
        textView = contentView.findViewById(R.id.tv_text)
        this.addView(contentView)
    }

    private fun initAttrs(mCtx: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var typedArray = mCtx.theme.obtainStyledAttributes(attrs, R.styleable.ImageTextView, defStyleAttr, 0)

        for (i in 0..typedArray.indexCount) {
            var itemId = typedArray.getIndex(i)
            when (itemId) {
                R.styleable.ImageTextView_imageSrc -> imageSrc = typedArray.getResourceId(itemId, -1)
                R.styleable.ImageTextView_imageHeight -> imageHeight = typedArray.getDimension(itemId, 60F).toInt()
                R.styleable.ImageTextView_imageWidth -> imageWidth = typedArray.getDimension(itemId, 60F).toInt()
                R.styleable.ImageTextView_isImageSelect -> isImageSelect = typedArray.getBoolean(itemId, false)
                R.styleable.ImageTextView_textColor -> textColor = typedArray.getColor(itemId, 0)
                R.styleable.ImageTextView_textSize -> textSize = typedArray.getDimension(itemId, 18F)
                R.styleable.ImageTextView_text -> textStr = typedArray.getString(itemId)
            }
        }
    }


}