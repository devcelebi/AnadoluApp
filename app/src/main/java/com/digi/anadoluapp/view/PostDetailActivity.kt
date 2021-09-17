package com.digi.anadoluapp.view

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.digi.anadoluapp.R
import com.digi.anadoluapp.network.Post
import com.digi.anadoluapp.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.content_post_detail.*

class PostDetailActivity : AppCompatActivity() {
    private var infoMenuItem: MenuItem? = null
    private var sharedPreference: SharedPreference? = null
    private var post: Post? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        post = intent.getSerializableExtra("keyDetail") as Post?
        sharedPreference = SharedPreference()
        tvDetail.text = (post as Post).body

        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_fav, menu)
        infoMenuItem = menu?.findItem(R.id.action_favorite)
        if (isFavorite())
            infoMenuItem?.icon = getDrawable(android.R.drawable.star_on)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.action_favorite -> {
                addFavorite()
                if (!isFavorite())
                    item.icon = getDrawable(android.R.drawable.star_off)
                else
                    item.icon = getDrawable(android.R.drawable.star_on)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isFavorite(): Boolean {
        if (sharedPreference?.getFavorites(this)?.contains(post) == true) {
            return true
        }
        return false
    }

    private fun addFavorite() {
        if (sharedPreference?.getFavorites(this)?.contains(post) == true) {
            sharedPreference?.removeFavorite(this, post)
        } else {
            sharedPreference?.addFavorite(this, post);
        }
    }

}