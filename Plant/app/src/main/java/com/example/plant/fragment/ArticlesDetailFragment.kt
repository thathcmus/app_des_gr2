package com.example.plant.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentArticlesDetailBinding
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Article
import com.example.plant.util.FragmentUtil
import kotlinx.android.synthetic.main.articles_item.view.ivArticles
import kotlin.reflect.KClass

class ArticlesDetailFragment : Fragment() {
    private lateinit var binding: FragmentArticlesDetailBinding
    private var articleDetail = Article()
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
            binding.titleArticlesDetail.text = articleDetail.title
            GlideLoader(this.requireContext()).loadUserPictureFromUrl(articleDetail.posterAvatar,binding.ivPosterAvatarDetail, R.drawable.placeholderloading)
            binding.tvPosterNameDetail.text = articleDetail.posterName
            binding.tvPostDayDetail.text = articleDetail.createdAt
            binding.articleContent.text = articleDetail.content
        }
    }

    inline fun <reified T : Any> KClass<T>.getParcelable(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, T::class.java)
        else
            bundle.getParcelable(key)

    fun listenEvent() {
        binding.ivBackArticle.setOnClickListener() {
            FragmentUtil(this.activity).replaceFragment(ArticlesFragment(),R.id.HomeFrameLayout,false)
        }
    }
}