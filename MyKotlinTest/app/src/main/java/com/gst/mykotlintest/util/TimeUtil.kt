package com.gst.mykotlintest.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * author: GuoSongtao on 2017/12/20 16:09
 * email: 157010607@qq.com
 */
class TimeUtil {

    companion object {

        fun getTimeString(date: Date, pattern: String): String = SimpleDateFormat(pattern).format(date)

    }
}