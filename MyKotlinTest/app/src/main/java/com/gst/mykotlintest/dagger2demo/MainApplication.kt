package com.gst.mykotlintest.dagger2demo

import android.app.Application
import com.gst.mykotlintest.dagger2demo.di.component.AppComponent
import com.gst.mykotlintest.dagger2demo.di.component.DaggerAppComponent
import com.gst.mykotlintest.dagger2demo.di.module.AndroidModule
import com.gst.mykotlintest.dagger2demo.di.module.ApplicationModule
import com.gst.mykotlintest.dagger2demo.di.module.HttpModule
import com.gst.mykotlintest.util.UtilsHelper

class MainApplication : Application() {


    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        instanceReal = this

        component = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .androidModule(AndroidModule())
                .httpModule(HttpModule())
                .build()

        UtilsHelper.initUtils(this)

    }


    companion object {
        private lateinit var instanceReal: Application

        fun getInstance(): Application = instanceReal
    }
}

