package com.gst.mykotlintest.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gst.mykotlintest.config.BuildConfig

/**
 * author: GuoSongtao on 2017/12/20 16:18
 * email: 157010607@qq.com
 */
class SPUtil private constructor(ctx: Context) {

    var mCtx: Context
    var mSp: SharedPreferences
    var mEditor: SharedPreferences.Editor

    companion object {
        var instance: SPUtil? = null

        fun initSPUtil(ctx: Context) {
            if (instance == null) {
                instance = SPUtil(ctx)
            }
        }


        fun changeByName(spName: String) {
            instance!!.mSp = instance!!.mCtx.getSharedPreferences(spName, Context.MODE_PRIVATE)
            instance!!.mEditor = instance!!.mSp.edit()
        }
    }

    init {
        mCtx = ctx
        mSp = mCtx.getSharedPreferences(BuildConfig.BASE_SP_NAME, Context.MODE_PRIVATE)
        mEditor = mSp.edit()
    }


    /**
     * Set<String>  putStringSet有问题
     */
    fun <V> putApply(map: Map<String, V>) {
        for ((key, value) in map) {
            when (value) {
                is Boolean -> mEditor.putBoolean(key, value)
                is Int -> mEditor.putInt(key, value)
                is Long -> mEditor.putLong(key, value)
                is Float -> mEditor.putFloat(key, value)
                is String -> mEditor.putString(key, value)
            }
        }
        mEditor.apply()
    }

    fun <V> putApply(key: String, value: V) {
        when (value) {
            is Boolean -> mEditor.putBoolean(key, value).apply()
            is Int -> mEditor.putInt(key, value).apply()
            is Long -> mEditor.putLong(key, value).apply()
            is Float -> mEditor.putFloat(key, value).apply()
            is String -> mEditor.putString(key, value).apply()
        }
    }


    fun <V> putCommit(map: Map<String, V>) {
        for ((key, value) in map) {
            when (value) {
                is Boolean -> mEditor.putBoolean(key, value)
                is Int -> mEditor.putInt(key, value)
                is Long -> mEditor.putLong(key, value)
                is Float -> mEditor.putFloat(key, value)
                is String -> mEditor.putString(key, value)
            }
        }
        mEditor.commit()
    }

    fun <V> putCommit(key: String, value: V) {
        when (value) {
            is Boolean -> mEditor.putBoolean(key, value).commit()
            is Int -> mEditor.putInt(key, value).commit()
            is Long -> mEditor.putLong(key, value).commit()
            is Float -> mEditor.putFloat(key, value).commit()
            is String -> mEditor.putString(key, value).commit()
        }
    }

    fun removeApply(key: String) {
        mEditor.remove(key).apply()
    }

    fun removeCommit(key: String) {
        mEditor.remove(key).commit()
    }

    fun <T> getDefVal(key: String, defVal: T): T? {
        var result: T = defVal
        try {
            when (defVal) {
                is Boolean -> result = mSp.getBoolean(key, defVal) as T
                is Int -> result = mSp.getInt(key, defVal) as T
                is Long -> result = mSp.getLong(key, defVal) as T
                is Float -> result = mSp.getFloat(key, defVal) as T
                is String -> result = mSp.getString(key, defVal) as T
            }
        } catch (e: Exception) {
            //可能key存储的值是Intger 却想获取String
            return null
        }
        return result
    }

    fun <T> getDefValNoNull(key: String, defVal: T): T {
        var result: T = if (getDefVal(key, defVal) == null) defVal else getDefVal(key, defVal) as T
        return result
    }

//    fun <T> get(key: String): T? {
//        var result: T
//    }


    fun clear() {
        mEditor.clear().apply()
    }

    fun test() {
        clear()

        val map = hashMapOf("name" to "小明", "gender" to "男", "old" to "13", "class" to "6年3班")
        println("key is name and value is ${getDefVal("name", "不知道")}")
        putCommit(map)
        println("key is name and value is ${getDefVal("name", "不知道")}")
        println("key is name1 and value is ${getDefVal("name1", "不知道")}")
        println("key is name and value to Int is ${getDefVal("name", 250)}")
        println("key is name and value to Int and no null is ${getDefValNoNull("name", 250)}")
        removeApply("name")
        println("key is name and value is ${getDefVal("name", "不知道")}")

    }
}