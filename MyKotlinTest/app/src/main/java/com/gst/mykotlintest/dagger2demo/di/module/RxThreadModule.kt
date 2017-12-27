package com.gst.mykotlintest.dagger2demo.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class RxThreadModule {

    @Singleton
    @Provides
    @Named("mainThread")
    fun provideAndroidSchedulers() = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @Named("ioThread")
    fun provideSchedulersIO() = Schedulers.io()
}