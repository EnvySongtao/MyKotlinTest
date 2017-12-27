package com.gst.mykotlintest.util

import android.app.Application

/**
 * author: GuoSongtao on 2017/12/20 15:56
 * email: 157010607@qq.com
 */
class UtilsHelper {

    companion object {

        fun initUtils(mCtx: Application) {

            //子线程也可以发送Toast的 toast工具类
            ToastUtil.initToastUtil(mCtx)

            MainHandler.initMainHandler()

            SPUtil.initSPUtil(mCtx)
        }
    }
}