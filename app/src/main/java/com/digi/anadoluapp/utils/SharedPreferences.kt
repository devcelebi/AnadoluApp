package com.digi.anadoluapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.digi.anadoluapp.network.Post
import com.google.gson.Gson
import java.util.*


class SharedPreference {
    private fun saveFavorites(context: Context, favorites: List<Post?>?) {
        val editor: SharedPreferences.Editor
        val settings: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        val gson = Gson()
        val jsonFavorites = gson.toJson(favorites)
        editor.putString(FAVORITES, jsonFavorites)
        editor.commit()
    }

    fun addFavorite(context: Context, product: Post?) {
        var favorites: MutableList<Post?>? = getFavorites(context)
        if (favorites == null) favorites = ArrayList<Post?>()
        favorites!!.add(product)
        saveFavorites(context, favorites)
    }

    fun removeFavorite(context: Context, product: Post?) {
        val favorites: ArrayList<Post?>? = getFavorites(context)
        if (favorites != null) {
            favorites.remove(product)
            saveFavorites(context, favorites)
        }
    }

    fun getFavorites(context: Context): ArrayList<Post?>? {
        var favorites: List<Post?>?
        val settings: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        if (settings.contains(FAVORITES)) {
            val jsonFavorites = settings.getString(FAVORITES, null)
            val gson = Gson()
            val favoriteItems: Array<Post> = gson.fromJson<Array<Post>>(
                jsonFavorites,
                Array<Post>::class.java
            )
            favorites = Arrays.asList(*favoriteItems)
            favorites = ArrayList<Post>(favorites)
        } else return null
        return favorites as ArrayList<Post?>?
    }

    companion object {
        const val PREFS_NAME = "PRODUCT_APP"
        const val FAVORITES = "Product_Favorite"
    }
}