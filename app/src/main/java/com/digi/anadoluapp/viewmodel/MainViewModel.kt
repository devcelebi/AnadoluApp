package com.digi.anadoluapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digi.anadoluapp.R
import com.digi.anadoluapp.network.Post
import com.digi.anadoluapp.network.PostRepository

class MainViewModel : ViewModel() {
    var postModelListLiveData : LiveData<List<Post>>?=null
    var posList : ArrayList<Post>? = arrayListOf()
    var postRepository : PostRepository? = null

    init{
        postRepository = PostRepository()
        postModelListLiveData = MutableLiveData()
    }

    fun fetchAllPosts(){
        postModelListLiveData = postRepository?.fetchAllPosts()
    }


}