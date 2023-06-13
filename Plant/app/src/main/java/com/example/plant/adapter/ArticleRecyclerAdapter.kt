package com.example.plant.adapter

import android.support.annotation.NonNull
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Article
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.articles_item.view.ivArticles
import kotlinx.android.synthetic.main.articles_item.view.ivPosterAvatar
import kotlinx.android.synthetic.main.articles_item.view.likeBtn
import kotlinx.android.synthetic.main.articles_item.view.titleArticles
import kotlinx.android.synthetic.main.articles_item.view.tvPostDay
import kotlinx.android.synthetic.main.articles_item.view.tvPosterName

class ArticleRecyclerAdapter(val articleList: ArrayList<Article>, val listener: MyClickListener) : RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder>() {
   private val userId: String =  Firestore().getCurrentUserAuth()!!.uid
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun getLikeBtnStatus(userId: String, postKey: String, holder: ViewHolder, position: Int) {
            val mFireStore = Firebase.firestore
            mFireStore.collection("likedArticles").document(postKey)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    holder.itemView.likeBtn.isChecked = documentSnapshot.exists() && documentSnapshot.contains("${userId}") && documentSnapshot.getBoolean("${userId}")!!
                    }
                .addOnFailureListener { exception ->
                    Log.e("exception", "exception: ${exception}")
                }
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onClick(position)

            }
            itemView.likeBtn.setOnClickListener {
                val position = adapterPosition
                val postKey : String = articleList[position].id
                val likedArticleRef = Firebase.firestore.collection("likedArticles").document(postKey)
                likedArticleRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            // Tài liệu đã tồn tại trong Firestore, thực hiện cập nhật
                            if(itemView.likeBtn.isChecked){
                                likedArticleRef.update("${userId}", true)
                            }else
                            {
                                likedArticleRef.update("${userId}", false)
                            }
                        } else {
                            // Tài liệu không tồn tại trong Firestore, ta có thể tạo mới và cập nhật
                            likedArticleRef.set( hashMapOf<String, Any>(
                                    "${userId}" to true
                            ))
                        }
                    }
                    .addOnFailureListener { exception ->
                        // resolv exception
                    }
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
        // get like btn status
        val postKey : String = articleList[position].id
        holder.getLikeBtnStatus( userId,  postKey, holder, position)

    }
    override fun getItemCount(): Int {
        return articleList.size
    }
    interface MyClickListener{
        fun onClick(position: Int){
        }
    }

}