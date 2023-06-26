package com.example.plant.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.adapter.ArticleRecyclerAdapter
import com.example.plant.adapter.SpeciesRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentArticlesBinding
import com.example.plant.model.Article
import com.example.plant.model.Species
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore

class ArticlesFragment : Fragment()  {
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
                    ArticleRecyclerAdapter(this@ArticlesFragment,articleList as ArrayList<Article>)
                }
                binding.rcArticles.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rcArticles.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
            }
    }
    fun listenEvent() {
        binding.ivBacktoHome.setOnClickListener() {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchSpecies.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // thiết lập search cho article lại không cần phải lưu List vào 1 biến khác để khôi
                // phục dữ liệu như bên SpeciesFragment, không biết rõ tại sao

                val filteredArticleList = articleList.filter { article ->
                    newText?.let { article.title.contains(it.trim(), ignoreCase = true) } == true ||
                            newText?.let { article.posterName.contains(it.trim(), ignoreCase = true) } == true
                }

                // đưa adapter filteredArticleList vào rcArticle để hiển thị danh sách tìm kiếm
                binding.rcArticles.adapter =
                    ArticleRecyclerAdapter(this@ArticlesFragment,filteredArticleList as ArrayList<Article>)

                return false
            }

        })
    }

}