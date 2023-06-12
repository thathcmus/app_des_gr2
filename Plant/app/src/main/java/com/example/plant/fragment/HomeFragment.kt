package com.example.plant.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.NavigationTestActivity
import com.example.plant.R
import com.example.plant.adapter.PhotographyAdaper
import com.example.plant.adapter.PlantTypeAdapter
import com.example.plant.databinding.FragmentHomeBinding
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Photography
import com.example.plant.model.PlantType
import com.example.plant.model.User
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.ivAvatar
import kotlinx.android.synthetic.main.fragment_home.tvNameUserHome

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private var currentUser = User()
    private var photographyList: MutableList<Photography> = mutableListOf()
    private var plantTypeList: MutableList<PlantType> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        //get data
        getData()
        listenEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addingBtn.setOnClickListener {
            startActivity(Intent(requireContext(), NavigationTestActivity::class.java))
        }
    }

    private fun getData() {
        //get user info
        Firestore().getUserDetail(this)
        ///get photography list
        FirebaseFirestore.getInstance().collection("photography")
                .get()
                .addOnSuccessListener { photoDocuments ->
                    photographyList  = photoDocuments.toObjects(Photography::class.java)
                    //show into recycleview
                    binding.rcPhotography.adapter = this.activity?.let {
                        PhotographyAdaper(photographyList,
                                it
                        )
                    }
                    binding.rcPhotography.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.rcPhotography.setHasFixedSize(true)

                }
                .addOnFailureListener { exception ->
                }

        //get plant type list
        //get an Instance
        val plantTypeCollection = FirebaseFirestore.getInstance().collection("plantType")
        val plantCollection = FirebaseFirestore.getInstance().collection("plant")

        plantTypeCollection.get()
                .addOnSuccessListener { plantTypeDocuments ->
                    plantTypeList = plantTypeDocuments.toObjects(PlantType::class.java)
                    for (plantType in plantTypeList) {
                        //find plants match name field of plantType document
                        plantCollection.whereEqualTo("plantType", plantType.name)
                                .get()
                                .addOnSuccessListener { plantDocuments ->
                                    plantType.count = plantDocuments.size()
                                    binding.rcPlantTypes.adapter?.notifyDataSetChanged()
                                }
                                .addOnFailureListener { exception ->
                                    // Xử lý khi truy vấn thất bại
                                }
                    }
                    binding.rcPlantTypes.adapter = this.activity?.let {
                        PlantTypeAdapter(plantTypeList,
                                it
                        )
                    }
                    binding.rcPhotography.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.rcPhotography.setHasFixedSize(true)

                }
                .addOnFailureListener { exception ->
                    // Xử lý khi truy vấn thất bại
                }


    }
    private fun listenEvent() {
        binding.ivAvatar.setOnClickListener() {
            FragmentUtil(this.activity).replaceFragment(ProfileFragment(),R.id.HomeFrameLayout,false)
        }
    }
    fun setCurrentUser(currentUser: User){
        this.currentUser = currentUser
    }

    fun ShowUIInfo(UserInfo : User){
        this.activity?.let { GlideLoader(it).loadUserPictureFromUrl(UserInfo.avatar,ivAvatar, R.drawable.placeholder) }
        tvNameUserHome.text = UserInfo?.fullName
    }
}