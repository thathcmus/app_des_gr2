package com.example.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.adapter.RecyclerViewAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentSpeciesBinding
import com.example.plant.model.Species
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView
import java.util.Objects

class SpeciesFragment :  Fragment(), RecyclerViewAdapter.MyClickListener {

    private lateinit var binding: FragmentSpeciesBinding
    private var speciesList: MutableList<Species> = mutableListOf()

    var mRecyclerView: IndexFastScrollRecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpeciesBinding.inflate(layoutInflater)
        initialiseUI()
        //get data
        getData()
        listenEvent()
        return binding.root
    }
    fun getData () {

        FirebaseFirestore.getInstance().collection("species")
            .get()
            .addOnSuccessListener { species ->
                speciesList  = species.toObjects(Species::class.java)
                //show into recycleview
                binding.rcSpecies.adapter = this.activity?.let {
                    RecyclerViewAdapter( speciesList as ArrayList<Species>, this@SpeciesFragment)
                }
                binding.rcSpecies.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rcSpecies.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
            }
    }
    fun listenEvent() {

    }
    override fun onClick(position: Int) {
        val PlantFragment = PlantFragment()
        val bundle = Bundle()
        bundle.putParcelable(constant.SPECIES, speciesList[position])
        PlantFragment.arguments = bundle
        FragmentUtil(this.activity).replaceFragment(PlantFragment,
            R.id.HomeFrameLayout,false)
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate(savedInstanceState)
        setContentView(R.layout.fragment_species)
        mRecyclerView = findViewById(R.id.rcSpecies)
        initialiseUI()
    }*/
    private fun initialiseUI() {
        mRecyclerView?.apply {
            //layoutManager = LinearLayoutManager(this@SpeciesFragment)
            adapter = RecyclerViewAdapter(speciesList as ArrayList<Species>, this@SpeciesFragment)
            setIndexTextSize(12)
            setIndexBarCornerRadius(0)
            setIndexBarTransparentValue(0.4.toFloat())
            setPreviewPadding(0)
            setPreviewTextSize(60)
            setPreviewTransparentValue(0.6f)
            setIndexBarVisibility(true)
            setIndexBarStrokeVisibility(true)
            setIndexBarStrokeWidth(1)
            setIndexBarHighLightTextVisibility(true)
        }
        Objects.requireNonNull<RecyclerView.LayoutManager>(mRecyclerView?.layoutManager)
            .scrollToPosition(0)
    }

}
