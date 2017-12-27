package com.gst.mykotlintest

import android.app.Application
import com.gst.mykotlintest.util.UtilsHelper

/**
 * author: GuoSongtao on 2017/11/16 18:24
 * email: 157010607@qq.com
 */
class MyApp : Application() {

    companion object {
        private lateinit var instance: MyApp
        fun getInstance(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        UtilsHelper.initUtils(this)
    }

}