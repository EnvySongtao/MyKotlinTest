package com.gst.mykotlintest.dagger2demo.view.activity

import android.app.Activity
import android.os.Bundle
import com.gst.mykotlintest.dagger2demo.MainApplication
import com.gst.mykotlintest.dagger2demo.di.component.ActivityComponent
import com.gst.mykotlintest.dagger2demo.di.component.DaggerActivityComponent
import com.gst.mykotlintest.dagger2demo.di.module.ActivityModule

/**
 * author: GuoSongtao on 2017/11/17 17:06
 * email: 157010607@qq.com
 */
class ActTestNew : Activity() {
    lateinit var component: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent(((application as MainApplication).component))
                .build()

//        component.provideActivityModule(ActivityModule(this))
    }
}