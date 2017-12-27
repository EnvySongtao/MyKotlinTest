package com.gst.mykotlintest.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.gst.mykotlintest.R
import com.gst.mykotlintest.config.BuildConfig
import com.gst.mykotlintest.framework.image.IImageLoader
import com.gst.mykotlintest.framework.image.ImageLoaderProxy
import com.gst.mykotlintest.util.ToastUtil
import kotlinx.android.synthetic.main.act_image_test.*
import java.io.File

/**
 * author: GuoSongtao on 2017/11/16 16:17
 * email: 157010607@qq.com
 */
class ActCamera : Activity() {
    lateinit var imageLoader: IImageLoader
    val photoPath = BuildConfig.ImagePath + "20171116001.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_image_test)


        if (!File(BuildConfig.ImagePath).exists()) {
            try {
                File(BuildConfig.ImagePath).mkdirs()
            } catch (e: Exception) {
            }
        }

        imageLoader = ImageLoaderProxy.getInstance()
        imageLoader.displayImageFromPath(this, photoPath, iv_show)

        iv_show.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                    .setTitle("是否去拍照")
                    .setPositiveButton("算了", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    .setNegativeButton("拍照", DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(photoPath)))// /mnt/sdcard/test.jpg
                        // 是照片存储目录
                        try {
                            if (File(photoPath).exists()) {
                                if (!File(photoPath).delete()) ToastUtil.show("删除图片失败")
                            }
                            Log.i("setOnClickListener", "File(photoPath).exists()= ${File(photoPath).exists()}")
                            startActivityForResult(intent, 0x001)
                        } catch (e: Exception) {
                            ToastUtil.show("没有拍照权限")
                        }
                        dialog.dismiss()
                    })

            dialog.show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("onActivityResult", "requestCode=" + requestCode + ";resultCode=" + resultCode + "data=" + data)
        if (RESULT_OK === resultCode) {
            when (requestCode) {
                0x001 -> {
//                    imageLoader.clearOldImage(this)
                    imageLoader.displayImageFromPathRefresh(this, photoPath, iv_show)
                }
            }
        }
    }
}