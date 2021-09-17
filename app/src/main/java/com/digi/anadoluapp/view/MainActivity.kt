package com.digi.anadoluapp.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digi.anadoluapp.R
import com.digi.anadoluapp.adapter.PostListAdapter
import com.digi.anadoluapp.network.Post
import com.digi.anadoluapp.utils.SharedPreference
import com.digi.anadoluapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var adapter: PostListAdapter? = null
    private var searchView: SearchView? = null
    lateinit var viewModel: MainViewModel
    private var sharedPreference: SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference()
        loadPosts()
        loadFavoritePosts()
        initUi()
        bottom_navigatin_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.list -> {
                    recyclerViewPost.visibility = VISIBLE
                    recyclerViewPostFav.visibility = GONE
                }
                R.id.favorite -> {
                    recyclerViewPostFav.visibility = VISIBLE
                    recyclerViewPost.visibility = GONE
                    loadFavoritePosts()
                }
            }
            true
        }

    }

    override fun onResume() {
        loadFavoritePosts()
        super.onResume()
    }

    private fun initUi() {
        recyclerViewPost.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerViewPostFav.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    fun navigateToDetail(pos: Int) {
        val intent = Intent(this, PostDetailActivity::class.java)
        intent.putExtra("keyDetail", viewModel.posList?.get(pos))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView!!.maxWidth = Int.MAX_VALUE
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter?.filter?.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun loadFavoritePosts() {
        recyclerViewPostFav.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
        var posts = sharedPreference?.getFavorites(this)

        if (posts != null && posts.size > 0) {
            adapter = PostListAdapter(this, sharedPreference?.getFavorites(this) as ArrayList<Post>)
            adapter?.setOnItemClickListener(object : PostListAdapter.ClickListener {
                override fun onClick(pos: Int, aView: View) {
                    navigateToDetail(pos)
                }
            })
            recyclerViewPostFav.adapter = adapter
        } else {
            Toast.makeText(this, getString(R.string.not_found_fav), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadPosts() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchAllPosts()
        viewModel.postModelListLiveData?.observe(this, Observer {
            if (it != null) {
                viewModel.posList = it as ArrayList<Post>
                recyclerViewPost.layoutManager = LinearLayoutManager(
                    this,
                    RecyclerView.VERTICAL, false
                )
                adapter = PostListAdapter(this, it)
                adapter?.setOnItemClickListener(object : PostListAdapter.ClickListener {
                    override fun onClick(pos: Int, aView: View) {
                        navigateToDetail(pos)
                    }
                })
                recyclerViewPost.adapter = adapter
            } else {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT)
                    .show()
            }

        })


    }


}