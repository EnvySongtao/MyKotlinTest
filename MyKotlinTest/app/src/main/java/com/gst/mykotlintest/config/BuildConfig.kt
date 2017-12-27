package com.gst.mykotlintest.config

import android.os.Environment

/**
 * author: GuoSongtao on 2017/11/16 10:25
 * email: 157010607@qq.com
 */
class BuildConfig {
    //静态变量
    companion object {
        val BASE_SP_NAME = "myKotlinTestSp"
        val BasePath = Environment.getExternalStorageDirectory().absolutePath// SD卡根目录
        val DataBasePath = BasePath + "/myKotlinTest/"// 数据文件根目录
        val ImagePath = DataBasePath + "image/"// 图片数据路径

        val DEBUG = true// 图片数据路径
    }

}