package com.gst.mykotlintest.util

import android.os.Handler
import android.os.Looper

/**
 * author: GuoSongtao on 2017/12/12 14:13
 * email: 157010607@qq.com
 */
class MainHandler private constructor() : Handler(Looper.getMainLooper()) {
    companion object {
        private var instance: MainHandler? = null

        fun initMainHandler() {
            if (instance == null) {
                instance = MainHandler()
                LogUtil.i("初始化MainHandler，防止第一次在子线程调用出错")
            }
        }

        fun postMain(runnable: Runnable) {
            instance!!.post(runnable)
        }
    }
}