package com.gst.mykotlintest.dagger2demo.di.module

import android.app.Activity
import android.content.Context

import com.gst.mykotlintest.dagger2demo.di.ActivityContext
import com.gst.mykotlintest.dagger2demo.di.PerActivity

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @PerActivity
    @Provides
    @ActivityContext
    fun provideContext(): Context = activity
}
