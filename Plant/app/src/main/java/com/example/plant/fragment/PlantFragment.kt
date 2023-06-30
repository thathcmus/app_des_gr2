package com.example.plant.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.adapter.PlantRecyclerAdapter
import com.example.plant.adapter.SpeciesRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentPlantBinding
import com.example.plant.model.Plant
import com.example.plant.model.Species
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.plant

class PlantFragment : Fragment() {
    private lateinit var binding: FragmentPlantBinding
    private var speciesName: String = ""
    private var plantList: MutableList<Plant> = mutableListOf()
    private var plantListOfType: ArrayList<Plant>  = ArrayList()

    private var plantListOrigin: ArrayList<Plant>  = ArrayList()
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
        speciesName = arguments?.getString(constant.SPECIES).toString()
        plantListOfType = arguments?.getParcelableArrayList<Plant>(constant.PLANT) ?: ArrayList()
        val typeName = arguments?.getString(constant.PLANT_TYPE)
        binding.tvPlantName.text = typeName
        //get data from SpeciesFragement
        if(speciesName != "null"){
            binding.tvPlantName.text = speciesName
            FirebaseFirestore.getInstance().collection("plant")
                .whereEqualTo("species", speciesName)
                .get()
                .addOnSuccessListener { plant ->
                    plantList  = plant.toObjects(Plant::class.java)
                    plantListOrigin = plantList as ArrayList<Plant>
                    //show into recycleview
                    binding.rcPlant.adapter = this.activity?.let {
                        PlantRecyclerAdapter(this@PlantFragment, plantList as ArrayList<Plant>)
                    }
                    binding.rcPlant.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.rcPlant.setHasFixedSize(true)

                }
                .addOnFailureListener { exception ->
                }
        }
        // get plant List from HomeFragment
        if(plantListOfType.size != 0) {
            binding.rcPlant.apply {
                adapter = PlantRecyclerAdapter(this@PlantFragment, plantListOfType)
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
            plantListOrigin = plantListOfType
        }

    }
    fun listenEvent() {
        //click back btn
        binding.ivBacktoSpecies.setOnClickListener() {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // androidx Search view to species
        binding.searchPlants.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var filteredSpeciesList: ArrayList<Plant> = ArrayList()
                if(plantListOfType.size != 0){
                    filteredSpeciesList = plantListOfType.filter { species ->
                        species.name.contains(newText.trim(), ignoreCase = true)
                    } as ArrayList<Plant>
                }else {
                    filteredSpeciesList = plantList.filter { species ->
                        species.name.contains(newText.trim(), ignoreCase = true)
                    } as ArrayList<Plant>
                }
                binding.rcPlant.adapter =
                    PlantRecyclerAdapter(this@PlantFragment,
                        filteredSpeciesList as ArrayList<Plant>
                    )

                return false
            }

        })
    }
}