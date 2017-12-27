package com.gst.mykotlintest.dagger2demo.di.component

import com.gst.mykotlintest.dagger2demo.di.module.AndroidModule
import com.gst.mykotlintest.dagger2demo.di.module.ApplicationModule
import com.gst.mykotlintest.dagger2demo.di.module.HttpModule
import com.gst.mykotlintest.dagger2demo.di.module.RxThreadModule
import com.gst.mykotlintest.dagger2demo.view.activity.ActTestNew
import com.gst.mykotlintest.dagger2demo.view.activity.MainActivity
import com.gst.mykotlintest.dagger2demo.view.fragment.MainFragment
import com.gst.mykotlintest.dagger2demo.view.fragment.RepoFragment
import com.gst.mykotlintest.ui.mvptest.ActMVPTest
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(HttpModule::class,
        ApplicationModule::class, AndroidModule::class,
        RxThreadModule::class))
interface AppComponent {
    fun inject(fragment: MainFragment)

    fun inject(fragment: MainActivity)

    fun inject(activity: ActTestNew)

    fun inject(activity: ActMVPTest)

    fun inject(fragment: RepoFragment)
}
