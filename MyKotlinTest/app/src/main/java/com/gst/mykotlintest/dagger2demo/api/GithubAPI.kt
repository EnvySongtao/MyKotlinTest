package com.gst.mykotlintest.dagger2demo.api

import com.gst.mykotlintest.dagger2demo.model.GithubUserCollection
import com.gst.mykotlintest.dagger2demo.model.RepoCollection
import com.gst.mykotlintest.ui.mvptest.MVPUserBeen
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Observable<GithubUserCollection>

    @GET("users/{user}/repos")
    fun getRepo(@Path("user") user: String): Observable<List<RepoCollection>>


    @GET("users/{user}")
    fun getMVPUser(@Path("user") user: String): Observable<MVPUserBeen>
}
