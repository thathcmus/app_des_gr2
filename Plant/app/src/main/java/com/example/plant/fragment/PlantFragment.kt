package com.example.plant.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.adapter.PlantRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentPlantBinding
import com.example.plant.model.Plant
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.plant

class PlantFragment : Fragment(), PlantRecyclerAdapter.MyClickListener  {
    private lateinit var binding: FragmentPlantBinding
    private var speciesName: String = ""
    private var plantList: MutableList<Plant> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlantBinding.inflate(layoutInflater)
        //get data
        getData()
        listenEvent()
        return binding.root
    }
    fun getData () {
        speciesName = arguments?.getString(constant.SPECIES).toString();
        binding.tvPlantName.text = speciesName
            FirebaseFirestore.getInstance().collection("plant")
            .whereEqualTo("species", speciesName)
            .get()
            .addOnSuccessListener { plant ->
                plantList  = plant.toObjects(Plant::class.java)
                //show into recycleview
                binding.rcPlant.adapter = this.activity?.let {
                    PlantRecyclerAdapter(plantList as ArrayList<Plant>,this@PlantFragment)
                }
                binding.rcPlant.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rcPlant.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
            }
    }
    fun listenEvent() {
        binding.ivBacktoSpecies.setOnClickListener() {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }
    override fun onClick(position: Int) {
        val plantDetailFragment = PlantDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(constant.PLANT, plantList[position])
        plantDetailFragment.arguments = bundle
        FragmentUtil(this.activity).replaceFragment(plantDetailFragment,
            R.id.HomeFrameLayout,true)
    }

}