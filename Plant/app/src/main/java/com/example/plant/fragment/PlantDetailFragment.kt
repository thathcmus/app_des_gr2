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
import com.example.plant.databinding.FragmentPlantDetailBinding
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Plant
import com.example.plant.model.User
import com.example.plant.util.FragmentUtil
import kotlin.reflect.KClass

class PlantDetailFragment : Fragment() {
    private lateinit var binding: FragmentPlantDetailBinding
    private var plantDetail = Plant()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetailBinding.inflate(layoutInflater)
        //get data
        getPlantDetail()
        listenEvent()
        return binding.root
    }
    private fun getPlantDetail() {
        val bundle = this@PlantDetailFragment.arguments
        if (bundle != null) {
            plantDetail = bundle?.let { Plant::class.getParcelable(it, constant.PLANT) }!!
            GlideLoader(this.requireContext()).loadUserPictureFromUrl(plantDetail.image,binding.ivPlantDetail, R.drawable.placeholderloading)
            binding.plantStatus1.text = plantDetail.status[0]
            binding.plantStatus2.text = plantDetail.status[1]
            binding.namePlant.text = plantDetail.name
            binding.kingdomPlant.text = plantDetail.kingdom
            binding.familyPlant.text = plantDetail.family
            binding.plantContent.text = plantDetail.description
        }
        }

    inline fun <reified T : Any> KClass<T>.getParcelable(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, T::class.java)
        else
            bundle.getParcelable(key)

    fun listenEvent() {
        binding.ivBackPlantDetail.setOnClickListener() {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }

}