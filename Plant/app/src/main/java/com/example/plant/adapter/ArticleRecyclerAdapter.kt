package com.example.plant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Article
import kotlinx.android.synthetic.main.articles_item.view.ivArticles
import kotlinx.android.synthetic.main.articles_item.view.ivPosterAvatar
import kotlinx.android.synthetic.main.articles_item.view.titleArticles
import kotlinx.android.synthetic.main.articles_item.view.tvPostDay
import kotlinx.android.synthetic.main.articles_item.view.tvPosterName
import kotlinx.android.synthetic.main.plant_type_item.view.ivPlantType

class ArticleRecyclerAdapter(val articleList: ArrayList<Article>, val listener: MyClickListener) : RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onClick(position)

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.articles_item, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = articleList[position]
        holder.itemView.apply {
            GlideLoader(context).loadUserPictureFromUrl(articleList[position].image,holder.itemView.ivArticles, R.drawable.placeholderloading)
            titleArticles.text = articleList[position].title
            GlideLoader(context).loadUserPictureFromUrl(articleList[position].posterAvatar,holder.itemView.ivPosterAvatar, R.drawable.placeholderloading)
            tvPosterName.text = articleList[position].posterName
            tvPostDay.text = articleList[position].createdAt
        }
    }
    override fun getItemCount(): Int {
        return articleList.size
    }
    interface MyClickListener{
        fun onClick(position: Int){
        }
    }

}