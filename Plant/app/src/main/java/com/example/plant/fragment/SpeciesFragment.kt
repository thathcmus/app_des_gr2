package com.example.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.adapter.SpeciesRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentSpeciesBinding
import com.example.plant.model.Species
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SpeciesFragment :  Fragment(), SpeciesRecyclerAdapter.MyClickListener {

    private lateinit var binding: FragmentSpeciesBinding
    private var speciesList: MutableList<Species> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpeciesBinding.inflate(layoutInflater)
        //get data
        getData()
        listenEvent()
        return binding.root
    }
    fun getData () {

        FirebaseFirestore.getInstance().collection("species")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { species ->
                speciesList  = species.toObjects(Species::class.java)
                //show into recycleview
                binding.rcSpecies.adapter = this.activity?.let {
                    SpeciesRecyclerAdapter( speciesList , this@SpeciesFragment)
                }
                binding.rcSpecies.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rcSpecies?.apply {
                    setIndexTextSize(16)
                    setIndexBarTextColor("#838383");
                    setIndexBarColor(R.color.Home_background_color);
                    setIndexBarCornerRadius(4)
                    setIndexBarTransparentValue(0.4.toFloat())
                    setPreviewPadding(0)
                    setPreviewTextSize(60)
                    setPreviewTransparentValue(0.6f)
                    setIndexBarVisibility(true)
                    setIndexBarStrokeVisibility(true)
                    setIndexBarStrokeWidth(0)
                    setIndexBarHighLightTextVisibility(true)
                }
                binding.rcSpecies.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
                }
    }
    fun listenEvent() {
        binding.ivBacktoHome.setOnClickListener(){
            FragmentUtil(activity).replaceFragment(HomeFragment(),R.id.HomeFrameLayout,false )
        }
    }
    override fun onClick(position: Int) {
        val PlantFragment = PlantFragment()
        val bundle = Bundle()
        bundle.putParcelable(constant.SPECIES, speciesList[position])
        PlantFragment.arguments = bundle
        FragmentUtil(this.activity).replaceFragment(PlantFragment,
            R.id.HomeFrameLayout,false)
    }
}
