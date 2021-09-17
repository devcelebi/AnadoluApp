package com.digi.anadoluapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRepository {
    fun fetchAllPosts(): LiveData<List<Post>> {
        val data = MutableLiveData<List<Post>>()
        val apiInstance = ApiInstance.getApiClient().create(ApiService::class.java)
        apiInstance?.fetchAllPosts()?.enqueue(object : Callback<List<Post>> {

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }
}