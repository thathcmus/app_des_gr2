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
        showUI()
        listenEvent()
        return binding.root
    }
    fun showUI() {

        //show into photograply recylceview
        binding.rcPhotography.adapter = this.activity?.let {
            PhotographyAdaper(photographyList,
                it
            )
        }
        binding.rcPhotography.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcPhotography.setHasFixedSize(true)
        //show into plant type recylceview
        binding.rcPlantTypes.adapter = this.activity?.let{
            PlantTypeAdapter(plantTypeList,
            it
            )
        }
        binding.rcPlantTypes.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcPlantTypes.setHasFixedSize(true)

    }
    private fun getData() {
        //get user info
        Firestore().getUserDetail(this)


        ///get photography list
        photographyList.add(Photography(R.drawable.photography_item, "#mini"))
        photographyList.add(Photography(R.drawable.photography_item2, "#cute"))
        photographyList.add(Photography(R.drawable.photography_item3, "#small"))
        photographyList.add(Photography(R.drawable.photography_item4, "#danger"))
        ///get plant type list
        plantTypeList.add(PlantType(R.drawable.home_plants,"Home Plants"))
        plantTypeList.add(PlantType(R.drawable.huge_plants,"Huge Plants"))
        plantTypeList.add(PlantType(R.drawable.climbing_plants,"Climbing Plants"))
        plantTypeList.add(PlantType(R.drawable.outdoor_plants,"Outdoor Plants"))

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
        this.activity?.let { GlideLoader(it).loadUserPictureFromUrl(UserInfo.avatar,ivAvatar) }
        tvNameUserHome.text = UserInfo?.fullName
    }
}