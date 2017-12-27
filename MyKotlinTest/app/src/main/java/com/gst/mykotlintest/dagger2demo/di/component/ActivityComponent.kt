package com.gst.mykotlintest.dagger2demo.di.component

import com.gst.mykotlintest.dagger2demo.di.PerActivity
import com.gst.mykotlintest.dagger2demo.di.module.ActivityModule
import dagger.Component

/**
 * 类名前面的 Component 会 在 DaggerActivityComponent(build生成，实现了ActivityComponent接口 的类)
 * Preconditions.checkNotNull(activityModule);
 *
 * modules 实现方法类的列表 ；例如：ActivityModule 实现了 fun provideContext(): Context = activity(构造方法传入了)
 * dependencies 依赖于，Builder中的成员变量，也等价于实现了ActivityComponent的成员变量，执行时，需要在builder()后设置
 * 例如：AppComponent 在DaggerActivityComponent中s实现了 appComponent()方法
 * 且appComponent() 返回了DaggerActivityComponent的Builder()
 * 执行时会将 AppComponent 赋值到 Builder 中
 *
 * 待续.......
 */
/***
 * 2017-12-7 for ActMVPTest and MVPPesenter add MVPTestModule::class
 */
@PerActivity
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    //    fun provideActivityModule(module: ActivityModule)
//    fun provideActivityModule(module: ActivityModule)
}
