package com.gst.mykotlintest.framework.image

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import android.os.AsyncTask
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.gst.mykotlintest.R
import java.util.*


/**
 * author: GuoSongtao on 2017/11/16 15:17
 * email: 157010607@qq.com
 */
class GlideUtil : IImageLoader {

    companion object {
        private class Inner {
            companion object {
                val instance = GlideUtil()
            }
        }

        fun getInstance(): GlideUtil {
            return Inner.instance
        }
    }

    var option: RequestOptions

    /**
     * 相关方法
    with():指定了声明周期
    load():加载资源，String/Uri/File/Integer/URL/byte[]/T,或者 loadFromMediaStore(Uri uri)
    placeholder(resourceId/drawable)： 设置资源加载过程中的占位Drawable。
    error()：load失败时显示的Drawable。
    crossFade()/crossFade(int duration)：imageView改变时的动画，version 3.6.1后默认开启300毫秒
    dontAnimate()：移除所有的动画。
    override() ：调整图片大小
    centerCrop()：图片裁剪，ImageView 可能会完全填充，但图像可能不会完整显示。
    fitCenter()： 图像裁剪，图像将会完全显示，但可能不会填满整个 ImageView。
    animate(): 指定加载动画。
    transform():图片转换。
    bitmapTransform(): bitmap转换，不可以和(centerCrop() 或 fitCenter())共用。
    priority(Priority priority):当前线程的优先级,Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL(default)，Priority.LOW
    thumbnail(): 缩略图.
    listener():异常监听
    preload(int width, int height): 预加载resource到缓存中（单位为pixel）
    fallback(Drawable drawable):设置model为空时显示的Drawable。
    using() ：为单个的请求指定一个 model
    asGif()：Gif 检查，如果是图片且加了判断，则会显示error占位图，否则会显示图片
    asBitmap()：bitmap转化，如果是gif，则会显示第一帧
    Glide 可以以load(File)的形式播放本地视频，但是如果需要播放网络视屏，则要用VideoView
     */
    init {
        //4.0.0 后 将部分设置 移动到 RequestOptions 中设置了
        option = RequestOptions().signature(ObjectKey(UUID.randomUUID()))
                .centerCrop() //
                .placeholder(R.mipmap.ic_launcher_round)
//                .circleCrop()  //图片切圆型
                .timeout(3000)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        println("RequestOptions create successfully!")
    }

    /**
     * 都是标准路径
     * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
     * load assets资源：load("file:///android_asset/f003.gif")
     * load raw资源：load("android.resource://"+ctx.packageName+"/raw/raw_1")或load("android.resource://"+ctx.packageName+"/raw/"+R.raw.raw_1)
     * load drawable资源：load("android.resource://"+ctx.packageName+"/drawable/news")或load("android.resource://"+ctx.packageName+"/drawable/"+R.drawable.news)
     * load ContentProvider资源：load("content://media/external/images/media/139469")
     * load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
     * load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
     */
    override fun displayImageFromPath(ctx: Context, uri: String, iv: ImageView) {
        Glide.with(ctx).load(uri).into(iv)
        ctx.packageName
    }

    override fun displayImageFromPathRefresh(ctx: Context, uri: String, iv: ImageView) {
//        Glide.with(ctx).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)  4.0.0 不可用
        Glide.with(ctx).setDefaultRequestOptions(refreshOption())
                .load(uri)
                .into(iv)
    }

    //每次刷新图片都要 signature(Key()) Key new 一次
    fun refreshOption(): RequestOptions {
        return option.signature(ObjectKey(UUID.randomUUID()))
    }

    override fun displayImageFromPathByScale(ctx: Context, uri: String, iv: ImageView, scale: Double) {
        Glide.with(ctx)
                .load(uri)
                .into(iv)
    }

    override fun displayImageFromDrawable(context: Context, imageName: Int, imageView: ImageView) {
        Glide.with(context)
                .load("android.resource://" + context.packageName + "/drawable/" + Int).into(imageView)
    }

    override fun displayGifFromDrawable(context: Context, imageName: Int, imageView: ImageView) {
        Glide.with(context)
                .asGif()
                .load("android.resource://" + context.packageName + "/drawable/" + Int)
                .into(imageView)
    }

    override fun displayImageFromUrl(context: Context, url: String, imageView: ImageView) {

    }

    override fun clearOldImage(context: Context) {
        Glide.get(context).clearMemory()
        AsyncTask.execute { Glide.get(context).clearDiskCache() }
    }


}