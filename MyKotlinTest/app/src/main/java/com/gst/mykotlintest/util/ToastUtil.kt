package com.gst.mykotlintest.util

import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.gst.mykotlintest.MyApp
import com.gst.mykotlintest.dagger2demo.MainApplication

/**
 * author: GuoSongtao on 2017/11/16 17:56
 * email: 157010607@qq.com
 */
class ToastUtil {
    var mCtx: Context
    var t: Toast

    companion object {
        // kotlin.UninitializedPropertyAccessException: lateinit property instance has not been initialized
//        lateinit var instance: ToastUtil
        var instance: ToastUtil? = null

        fun initToastUtil(ctx: Context) {
            if (instance == null) {
                instance = ToastUtil(ctx)
            }
        }

        /**
         * @param msg
         * @param time 多少时间
         */
        fun showAsTime(msg: CharSequence, time: Int) {
            //!! 运行前判断 instance 是否为空
            instance!!.instanceShow(msg, -1, -1, time)
        }

        fun show(msg: CharSequence) {
            instance!!.instanceShow(msg, Toast.LENGTH_SHORT, -1, -1)
        }

        fun longShow(msg: CharSequence) {
            instance!!.instanceShow(msg, Toast.LENGTH_LONG, -1, -1)
        }


        /**
         * 4.0后不适用 预留改为handler
         * @param resId
         * @param time
         */
        fun showAsTime(resId: Int, time: Int) {
            var msg: CharSequence = ""
            try {
                msg = instance!!.mCtx.getText(resId)
            } catch (e: Exception) {
                msg = resId.toString() + ""
                e.printStackTrace()
            }
            instance!!.instanceShow(msg, -1, -1, time)
        }

        fun show(msg: CharSequence, gravity: Int) {
            instance!!.instanceShow(msg, Toast.LENGTH_SHORT, gravity, -1)
        }

        fun show(resId: Int) {
            var msg: CharSequence = ""
            try {
                msg = instance!!.mCtx.getText(resId)
            } catch (e: Exception) {
                msg = resId.toString() + ""
                e.printStackTrace()
            }
            instance!!.instanceShow(msg, Toast.LENGTH_SHORT, -1, -1)
        }

        fun longShow(msg: CharSequence, gravity: Int) {
            instance!!.instanceShow(msg, Toast.LENGTH_LONG, gravity, -1)
        }

        fun longShow(resId: Int) {
            var msg: CharSequence = ""
            try {
                msg = instance!!.mCtx.getText(resId)
            } catch (e: Exception) {
                msg = resId.toString() + ""
                e.printStackTrace()
            }
            instance!!.instanceShow(msg, Toast.LENGTH_LONG, -1, -1)
        }

    }

    fun instanceShow(msg: CharSequence, duration: Int, gravity: Int, time: Int) {
        MainHandler.postMain(Runnable {
            t.setText(msg)

            if (gravity > 0) {
                t.setGravity(gravity, 0, 0)
            }

            if (duration == Toast.LENGTH_LONG || duration == Toast.LENGTH_SHORT) {
                t.duration = duration
            } else if (time > 0 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                t.duration = time
            } else {
                t.duration = Toast.LENGTH_SHORT
            }

            t.show()
        })
    }

    private constructor(ctx: Context) {
        mCtx = ctx
        t = Toast.makeText(mCtx, "", Toast.LENGTH_SHORT)
    }
}