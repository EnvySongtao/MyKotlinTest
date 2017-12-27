package com.gst.mykotlintest.ui.mvptest

import com.gst.mykotlintest.dagger2demo.api.GithubAPI
import com.gst.mykotlintest.dagger2demo.rx.RxThread
import javax.inject.Inject

/**
 * author: GuoSongtao on 2017/12/7 10:42
 * email: 157010607@qq.com
 */

/**
 * open关键字跟Java中的final是恰恰相反的。
 * 对于可以重写的函数，都需要显示的指明，使用的是open关键字。如果没有，在子类中声明跟父类相同的方法是非法的。
 */
open class MVPPresenter @Inject
constructor(private val api: GithubAPI, private val rxThread: RxThread) {

    private lateinit var mView: View

    interface View {
        fun displayUser(user: MVPUserBeen)
    }


    fun injectView(view: View) {
        this.mView = view
    }

    fun getUser(userName: String) {
        api.getMVPUser(userName)
                .compose(rxThread.applyAsync())
                .doOnTerminate { }
                .onErrorReturnItem(MVPUserBeen())
                .subscribe {
                    mView.displayUser(it)
                }

    }
}
