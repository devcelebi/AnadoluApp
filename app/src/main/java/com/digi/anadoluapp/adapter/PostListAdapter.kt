package com.digi.anadoluapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.digi.anadoluapp.R
import com.digi.anadoluapp.network.Post
import kotlinx.android.synthetic.main.row_item.view.*

class PostListAdapter(val activity: FragmentActivity, private val postList: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var postFilterList = ArrayList<Post>()

    init {
        postFilterList = postList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(postFilterList[position])
    }
    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(activity)
        return ViewHolder(layoutInflater.inflate(R.layout.row_item, parent, false))
    }

    override fun getItemCount(): Int {
        return postFilterList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(model: Post): Unit {
            itemView.txt.text = model.title
        }
        override fun onClick(p0: View?) {
            mClickListener.onClick(adapterPosition, itemView)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    postFilterList = postList as ArrayList<Post>
                } else {
                    val resultList = ArrayList<Post>()
                    for (row in postList) {
                        if (row.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    postFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = postFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                postFilterList = results?.values as ArrayList<Post>
                notifyDataSetChanged()
            }
        }
    }

}