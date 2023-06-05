package com.example.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.adapter.ArticleRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentArticlesBinding
import com.example.plant.model.Article
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore

class ArticlesFragment : Fragment(), ArticleRecyclerAdapter.MyClickListener  {
    private lateinit var binding: FragmentArticlesBinding
    private var articleList: MutableList<Article> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticlesBinding.inflate(layoutInflater)
        //get data
        getData()
        listenEvent()
        return binding.root
    }
    fun getData () {

        FirebaseFirestore.getInstance().collection("articles")
            .get()
            .addOnSuccessListener { articles ->
                articleList  = articles.toObjects(Article::class.java)
                //show into recycleview
                binding.rcArticles.adapter = this.activity?.let {
                    ArticleRecyclerAdapter(articleList as ArrayList<Article>,this@ArticlesFragment)
                }
                binding.rcArticles.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rcArticles.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
            }
    }
    fun listenEvent() {

    }
    override fun onClick(position: Int) {
        val ArticlesDetailFragment = ArticlesDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(constant.ARTICLE, articleList[position])
        ArticlesDetailFragment.arguments = bundle
        FragmentUtil(this.activity).replaceFragment(ArticlesDetailFragment,R.id.HomeFrameLayout,false)
    }

}