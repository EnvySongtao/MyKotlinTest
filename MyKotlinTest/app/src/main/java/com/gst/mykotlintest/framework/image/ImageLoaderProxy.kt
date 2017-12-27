package com.gst.mykotlintest.framework.image

import android.content.Context
import android.widget.ImageView

/**
 * author: GuoSongtao on 2017/11/16 15:14
 * email: 157010607@qq.com
 */
class ImageLoaderProxy : IImageLoader {

    companion object {
        private class Inner {
            companion object {
                val instance = ImageLoaderProxy()
            }
        }

        fun getInstance(): IImageLoader {
            return Inner.instance
        }
    }

    val glideUtil = GlideUtil.getInstance()

    override fun displayImageFromPath(ctx: Context, uri: String, iv: ImageView) {
        glideUtil.displayImageFromPath(ctx, uri, iv)
    }

    override fun displayImageFromPathRefresh(ctx: Context, uri: String, iv: ImageView) {
        glideUtil.displayImageFromPathRefresh(ctx, uri, iv)
    }

    override fun displayImageFromPathByScale(ctx: Context, uri: String, iv: ImageView, scale: Double) {
        glideUtil.displayImageFromPathByScale(ctx, uri, iv, scale)
    }

    override fun displayImageFromDrawable(context: Context, imageName: Int, imageView: ImageView) {
        glideUtil.displayImageFromDrawable(context, imageName, imageView)
    }

    override fun displayGifFromDrawable(context: Context, imageName: Int, imageView: ImageView) {
        glideUtil.displayImageFromDrawable(context, imageName, imageView)
    }

    override fun displayImageFromUrl(context: Context, url: String, imageView: ImageView) {
        glideUtil.displayImageFromUrl(context, url, imageView)
    }

    override fun clearOldImage(context: Context) {
        glideUtil.clearOldImage(context)
    }
}