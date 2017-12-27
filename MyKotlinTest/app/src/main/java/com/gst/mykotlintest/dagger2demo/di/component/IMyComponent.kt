package com.gst.mykotlintest.dagger2demo.di.component

import com.gst.mykotlintest.dagger2demo.di.PerActivity
import dagger.Component

/**
 * author: GuoSongtao on 2017/11/20 10:21
 * email: 157010607@qq.com
 */
@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface IMyComponent {
}