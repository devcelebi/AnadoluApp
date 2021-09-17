package com.digi.anadoluapp.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    fun fetchAllPosts(): Call<List<Post>>
}