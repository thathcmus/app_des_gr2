package com.example.plant.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.adapter.ArticleRecyclerAdapter
import com.example.plant.adapter.PlantRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentProfileBinding
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Article
import com.example.plant.model.Plant
import com.example.plant.model.User
import com.example.plant.util.FragmentUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.ivAvatarUser
import kotlinx.android.synthetic.main.fragment_profile.ivLocationIcon
import kotlinx.android.synthetic.main.fragment_profile.tvLocationUser
import kotlinx.android.synthetic.main.fragment_profile.tvNameUser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private var currentUser = User()
    private var isArticleSelected: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        Firestore().getUserDetail(this@ProfileFragment)

        listenerEvent()
        initButtonNav()
        return binding.root
    }

    fun setCurrentUser(currentUser: User){
        this.currentUser = currentUser
    }
    fun showUIInfo(UserInfo : User){
        this.activity?.let { GlideLoader(it).loadUserPictureFromUrl(UserInfo.avatar,binding.ivAvatarUser, R.drawable.placeholder) }
        binding.tvNameUser.text = UserInfo?.fullName
        binding.tvLocationUser.text = UserInfo?.location
        if (UserInfo?.location != "") {
            binding.ivLocationIcon.isVisible = true
        }
    }
    fun initButtonNav() {
        if(isArticleSelected){
            binding.buttonChoose.setItemSelected(R.id.article_action, true)
        }else{
            binding.buttonChoose.setItemSelected(R.id.plant_action, true)
        }
    }
    private fun listenerEvent() {
        binding.editUser.setOnClickListener() {
            val updateProfileFrag = UpdateProfileFragment()
            val bundle = Bundle()
            bundle.putParcelable(constant.USER_DETAIL, currentUser)
            updateProfileFrag.arguments = bundle
            FragmentUtil(this.activity).replaceFragment(updateProfileFrag, R.id.HomeFrameLayout)
        }
        binding.tvSignOut.setOnClickListener() {
            val stillLoginPre = this.activity?.getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
            val editor = stillLoginPre?.edit()
            editor?.putBoolean("isLoggedIn", false)
            editor?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonChoose.setOnItemSelectedListener {
            when (it) {
                R.id.article_action -> {
                    isArticleSelected = true
                    GlobalScope.launch(Dispatchers.Main) {
                        var articleIds: ArrayList<String> = ArrayList()
                        var articleList : ArrayList<Article> = ArrayList()
                        articleIds = getArticleIds()
                        for(articleId in articleIds){
                            val currArticle =  getArticleFromId(articleId)
                            articleList.add(currArticle)
                        }
                        Log.e("Teset", "articles : ${articleList}")
                        binding.likedOfProfileRecyclerView.apply {
                            adapter = ArticleRecyclerAdapter(this@ProfileFragment,articleList)
                            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                            setHasFixedSize(true)
                        }
                    }
                }

                R.id.plant_action -> {
                    isArticleSelected = false
                    GlobalScope.launch(Dispatchers.Main) {
                        var plantIds: ArrayList<String> = ArrayList()
                        var plantList : ArrayList<Plant> = ArrayList()
                        plantIds = getPlantIds()
                        for(plantId in plantIds){
                            val currPlant =  getPlantFromId(plantId)
                            plantList.add(currPlant)
                        }
                        Log.e("Teset", "plant : ${plantList}")
                        binding.likedOfProfileRecyclerView.apply {
                            adapter = PlantRecyclerAdapter(this@ProfileFragment,plantList)
                            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                            setHasFixedSize(true)
                        }
                    }
                }
            }
        }
    }
    private suspend fun getArticleIds(): ArrayList<String> = suspendCoroutine { continuation ->
        val articleIds: ArrayList<String> = ArrayList()
        val currUserId: String = Firebase.auth.currentUser!!.uid
        Log.e("userId", "user Id: ${currUserId}")
        FirebaseFirestore.getInstance().collection("likedArticles")
            .whereEqualTo(currUserId, true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    articleIds.add(document.id)
                }
                continuation.resume(articleIds)
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents from Firestore: ", exception)
            }
    }
    private suspend fun getPlantIds(): ArrayList<String> = suspendCoroutine { continuation ->
        val plantIds: ArrayList<String> = ArrayList()
        FirebaseFirestore.getInstance().collection("likedPlant")
            .whereEqualTo("${currentUser.id}", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    plantIds.add(document.id)
                }
                continuation.resume(plantIds)
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents from Firestore: ", exception)
            }
    }
    private suspend fun getArticleFromId(articleId: String): Article = suspendCoroutine { continuation ->
        var currArticle: Article = Article()
        FirebaseFirestore.getInstance().collection("articles").document(articleId)
            .get()
            .addOnSuccessListener { article ->
                if(article != null){
                    currArticle = article.toObject(Article::class.java)!!
                    continuation.resume(currArticle)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting article from Firestore: ", exception)
            }
    }
    private suspend fun getPlantFromId(plantId: String): Plant = suspendCoroutine { continuation ->
        var currPlant: Plant = Plant()
        FirebaseFirestore.getInstance().collection("plant").document(plantId)
            .get()
            .addOnSuccessListener { plant ->
                if(plant != null){
                    currPlant = plant.toObject(Plant::class.java)!!
                    continuation.resume(currPlant)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting Plant from Firestore: ", exception)
            }
    }
}