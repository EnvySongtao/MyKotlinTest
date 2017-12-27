package com.gst.mykotlintest.framework.image

import android.content.Context
import android.widget.ImageView

/**
 * author: GuoSongtao on 2017/11/16 14:53
 * email: 157010607@qq.com
 */
interface IImageLoader {

    /**
     * 指定路径导入图片
     * uri 包括网络地址 手机文件夹路径 URI等
     */
    fun displayImageFromPath(ctx: Context, uri: String, iv: ImageView)

    /**
     * 带刷新的图片加载
     */
    fun displayImageFromPathRefresh(ctx: Context, uri: String, iv: ImageView)

    fun displayImageFromPathByScale(ctx: Context, uri: String, iv: ImageView, scale: Double)

    fun displayImageFromDrawable(context: Context, imageName: Int, imageView: ImageView)

    fun displayGifFromDrawable(context: Context, imageName: Int, imageView: ImageView)

    fun displayImageFromUrl(context: Context, url: String, imageView: ImageView)

    fun clearOldImage(context: Context)
}