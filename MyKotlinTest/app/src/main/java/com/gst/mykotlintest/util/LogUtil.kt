package com.gst.mykotlintest.util

import android.text.TextUtils
import android.util.Log

/**
 * author: GuoSongtao on 2017/12/12 17:34
 * email: 157010607@qq.com
 */
class LogUtil {
    companion object {
        var allowLog = true
        var customTagPrefix = ""

        fun i(content: String) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.i(tag, content)
            }
        }

        fun i(content: String, thx: Throwable) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.i(tag, content, thx)
            }
        }

        fun v(content: String) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.v(tag, content)
            }
        }

        fun v(content: String, thx: Throwable) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.v(tag, content, thx)
            }
        }

        fun d(content: String) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.d(tag, content)
            }
        }

        fun d(content: String, thx: Throwable) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.d(tag, content, thx)
            }
        }

        fun w(content: String) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.w(tag, content)
            }
        }

        fun w(content: String, thx: Throwable) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.w(tag, content, thx)
            }
        }

        fun e(content: String) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.e(tag, content)
            }
        }

        fun e(content: String, thx: Throwable) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.e(tag, content, thx)
            }
        }

        fun wtf(content: String) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.wtf(tag, content)
            }
        }

        fun wtf(content: String, thx: Throwable) {
            if (allowLog) {
                var caller = Thread.currentThread().stackTrace[3]
                var tag = generateTag(caller)
                Log.wtf(tag, content, thx)
            }
        }

        fun generateTag(caller: StackTraceElement): String {
            var tag = "%s.%s(L:%d)"
            var clazzName = caller.className
            clazzName = clazzName.substring(clazzName.lastIndexOf('.') + 1)
            tag = String.format(tag, clazzName, caller.methodName, caller.lineNumber)
            tag = if (TextUtils.isEmpty(customTagPrefix)) tag else customTagPrefix + ":" + tag
            return tag
        }

    }

    private constructor()
}