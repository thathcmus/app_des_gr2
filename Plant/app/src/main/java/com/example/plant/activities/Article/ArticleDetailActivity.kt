package com.example.plant.activities.Article

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.model.Article
import kotlinx.android.synthetic.main.activity_article_detail.descriptioArticles
import kotlinx.android.synthetic.main.activity_article_detail.detailArticles
import kotlinx.android.synthetic.main.activity_article_detail.imageViewArticles
import kotlinx.android.synthetic.main.activity_article_detail.titleArticles
import kotlin.reflect.KClass

class ArticleDetailActivity : AppCompatActivity() {
    private var articleDetail = Article()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        supportActionBar?.hide()
        getArticleDetail()
    }

    private fun getArticleDetail () {
        val bundle = intent.extras
        articleDetail = bundle?.let { Article::class.getParcelable(it, constant.ARTICLE) }!!

        titleArticles.text = articleDetail.title
        descriptioArticles.text = articleDetail.descriptionArticles
        imageViewArticles.setImageResource(articleDetail.image)
        detailArticles.text = articleDetail.detailArticles
    }

    inline fun <reified T : Any> KClass<T>.getParcelable(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, T::class.java)
        else
            bundle.getParcelable(key)

}

