package com.digi.anadoluapp.network

import java.io.Serializable

data class Post(val userId:String,val id:String,val title:String,val body:String):Serializable
