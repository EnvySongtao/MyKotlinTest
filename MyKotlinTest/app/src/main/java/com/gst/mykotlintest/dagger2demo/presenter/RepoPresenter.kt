package com.gst.mykotlintest.dagger2demo.presenter

import com.gst.mykotlintest.dagger2demo.rx.RxThread
import com.gst.mykotlintest.dagger2demo.api.GithubAPI
import com.gst.mykotlintest.dagger2demo.model.RepoCollection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


open class RepoPresenter @Inject
constructor(private val api: GithubAPI, private val rxThread: RxThread) {

    private lateinit var view: View
    private val subscription = CompositeDisposable()

    interface View {
        fun loading()

        fun displayRepo(repos: List<RepoCollection>)

        fun loadComplete()
    }

    fun injectView(view: View) {
        this.view = view
    }

    fun getRepo(user: String) {
        view.loading()
        subscription.add(api.getRepo(user)
                .compose(rxThread.applyAsync())
                .doOnTerminate { view.loadComplete() }
                .onErrorReturnItem(emptyList())
                .subscribe({ view.displayRepo(it) }))
    }


    fun onStop() {
        subscription.clear()
    }
}
