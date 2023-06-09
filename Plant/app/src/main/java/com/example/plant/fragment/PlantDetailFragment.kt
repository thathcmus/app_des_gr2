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
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Plant
import com.example.plant.model.User
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.reflect.KClass

class PlantDetailFragment : Fragment() {
    private lateinit var binding: FragmentPlantDetailBinding
    private var plantDetail = Plant()
    private val userId: String =  Firestore().getCurrentUserAuth()!!.uid
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
            binding.rating.rating = plantDetail.star.toFloat()
            }
        //get liked btn status
        val plantLikeRef = Firebase.firestore.collection("likedPlant").document(plantDetail.id)
        plantLikeRef
            .get()
            .addOnSuccessListener { documentSnapshot ->
                binding.plantLikeBtn.isChecked = documentSnapshot.exists() && documentSnapshot.contains("${userId}") && documentSnapshot.getBoolean("${userId}")!!
            }
            .addOnFailureListener { exception ->
                Log.e("exception", "exception: ${exception}")
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
        binding.plantLikeBtn.setOnClickListener() {
            val plantLikeRef = Firebase.firestore.collection("likedPlant").document(plantDetail.id)
            plantLikeRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // document not exits on firestore
                        if(binding.plantLikeBtn.isChecked){
                            plantLikeRef.update("${userId}", true)
                        } else
                        {
                            plantLikeRef.update("${userId}", false)
                        }
                    } else {
                        // document not exits on firestore, set new doucment
                        plantLikeRef.set( hashMapOf<String, Any>(
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