package com.example.plant.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentArticlesDetailBinding
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Article
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.articles_item.view.ivArticles
import kotlinx.android.synthetic.main.articles_item.view.likeBtn
import kotlin.reflect.KClass

class ArticlesDetailFragment : Fragment() {
    private lateinit var binding: FragmentArticlesDetailBinding
    private var articleDetail = Article()
    private val userId: String =  Firestore().getCurrentUserAuth()!!.uid
    private var articleId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticlesDetailBinding.inflate(layoutInflater)
        //get data
        getArticleDetail()
        listenEvent()
        return binding.root
    }
    private fun getArticleDetail () {
        val bundle = this.arguments
        if (bundle != null) {
            articleDetail = bundle?.let { Article::class.getParcelable(it, constant.ARTICLE) }!!
            GlideLoader(this.requireContext()).loadUserPictureFromUrl(articleDetail.image,binding.ivArticlesDetail, R.drawable.placeholderloading)
            binding.articlesStatus1.text = articleDetail.status[0]
            binding.articlesStatus2.text = articleDetail.status[1]
            binding.titleArticlesDetail.text = articleDetail.title
            GlideLoader(this.requireContext()).loadUserPictureFromUrl(articleDetail.posterAvatar,binding.ivPosterAvatarDetail, R.drawable.placeholderloading)
            binding.tvPosterNameDetail.text = articleDetail.posterName
            binding.tvPostDayDetail.text = articleDetail.createdAt
            binding.articleContent.text = articleDetail.content
            articleId = articleDetail.id
            //get liked btn status
            val likedArticleRef = Firebase.firestore.collection("likedArticles").document(articleId)
            likedArticleRef
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            binding.likedBtnDetail.isChecked = documentSnapshot.exists() && documentSnapshot.contains("${userId}") && documentSnapshot.getBoolean("${userId}")!!
                        }
                        .addOnFailureListener { exception ->
                    Log.e("exception", "exception: ${exception}")
                }
        }
    }

    inline fun <reified T : Any> KClass<T>.getParcelable(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, T::class.java)
        else
            bundle.getParcelable(key)

    fun listenEvent() {
        binding.ivBackArticle.setOnClickListener() {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

        binding.likedBtnDetail.setOnClickListener() {
            val likedArticleRef = Firebase.firestore.collection("likedArticles").document(articleId)
            likedArticleRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // document not exits on firestore
                        if(binding.likedBtnDetail.isChecked){
                            likedArticleRef.update("${userId}", true)
                        }else
                        {
                            likedArticleRef.update("${userId}", false)
                        }
                    } else {
                        // document not exits on firestore, set new doucment
                        likedArticleRef.set( hashMapOf<String, Any>(
                            "${userId}" to true
                        ))
                    }
                }
                .addOnFailureListener { exception ->
                    // resolve exception
                }
        }
    }
}