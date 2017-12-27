package com.gst.mykotlintest.ui.mvptest

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.view.TextureView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.gst.mykotlintest.R
import com.gst.mykotlintest.dagger2demo.MainApplication
import com.gst.mykotlintest.dagger2demo.util.Constant
import com.gst.mykotlintest.util.MainHandler
import com.gst.mykotlintest.util.ToastUtil
import kotlinx.android.synthetic.main.act_mvp_test.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * author: GuoSongtao on 2017/12/7 10:18
 * email: 157010607@qq.com
 */

class ActMVPTest : Activity(), MVPPresenter.View {
    @Inject
    lateinit var presenter: MVPPresenter //private
    //    lateinit var componnent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_mvp_test)
        ButterKnife.bind(this)

        /***
         * 方法一
        var api = buildOkhttpBuild().create(GithubAPI::class.java)
        //        var api = HttpModule_ProvideApiServiceFactory()
        var rxThread = RxThread(AndroidSchedulers.mainThread(), Schedulers.io())
        presenter = MVPPresenter(api, rxThread)
         */

        //方法二
        (application as MainApplication).component.inject(this)
        presenter.injectView(this)
        presenter.getUser("Envy")

        setOnClickListener()
    }


    override fun displayUser(user: MVPUserBeen) {
        tvIdShow.text = user.id.toString()
        tvNameShow.text = user.nameRepo
        tvLoginNameShow.text = user.loginName
        tvLoactionShow.text = user.location
        tvLanguageShow.text = user.language
    }

    fun buildOkhttpBuild(): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
                .readTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
//                .cookieJar(CookiesManager())
        val okHttpClient = builder.build()
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.client(okHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(StringConverterFactory.create())
        return retrofitBuilder.build()
    }


    fun setOnClickListener() {
        tvIdShow.setOnClickListener { Thread { ToastUtil.show("你点击了  tvIdShow ") }.start() }
        tvNameShow.setOnClickListener { ToastUtil.show("你点击了  tvNameShow ") }
        tvLoginNameShow.setOnClickListener { ToastUtil.longShow("你点击了  tvLoginNameShow ") }
    }

    //    @BindView(R.id.tvIdShow) lateinit var tvIdShow1: TextView
//    @OnClick(R.id.tvIdShow)
//    fun ontvIdShowClick() {
//        Thread { ToastUtil.show("你点击了  tvIdShow ") }.start()
//    }
//
//    @OnClick(R.id.tvNameShow)
//    fun ontvNameShowClick() {
//        ToastUtil.show("你点击了  tvNameShow ")
//    }
//
//    @OnClick(R.id.tvLoginNameShow)
//    fun ontvLoginNameShowClick() {
//        ToastUtil.longShow("你点击了  tvLoginNameShow ")
//    }
}