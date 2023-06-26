package com.example.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.adapter.SpeciesRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentSpeciesBinding
import com.example.plant.databinding.FragmentSpeciesTestForSearchBinding
import com.example.plant.model.Species
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class SpeciesFragment :  Fragment(), SpeciesRecyclerAdapter.MyClickListener {

    private lateinit var binding: FragmentSpeciesBinding
    private var speciesList: MutableList<Species> = mutableListOf()

    // biến này copy speciesList, dùng trong event của searchView
    private var speciesListOrigin: MutableList<Species> = mutableListOf()

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
                speciesListOrigin = speciesList
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
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }
    override fun onClick(position: Int) {
        val PlantFragment = PlantFragment()
        val bundle = Bundle()
        bundle.putString(constant.SPECIES, speciesList[position].name)
        PlantFragment.arguments = bundle
        FragmentUtil(this.activity).replaceFragment(PlantFragment,
            R.id.HomeFrameLayout,true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // androidx Search view to species
        binding.searchSpecies.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                // speciesList nhận lại danh sách dữ liệu đã lấy ban đầu trên firestore
                speciesList = speciesListOrigin

                // Tạo filteredSpeciesList dựa trên speciesList và input vào search
                val filteredSpeciesList = speciesList.filter { species ->
                    species.name.contains(newText.trim(), ignoreCase = true)
                }

                // đưa adapter filteredSpeciesList vào rcSpecies để hiển thị danh sách tìm kiếm
                binding.rcSpecies.adapter =
                    SpeciesRecyclerAdapter(filteredSpeciesList as MutableList<Species>,
                    this@SpeciesFragment)

                // gán filteredSpeciesList vào speciesList, việc này nhằm
                // khiến cho hành động click vào items sẽ là click vào filteredSpeciesList
                // speciesList sẽ được gán lại dữ liệu ban đầu sau mỗi lần thay đổi nội dung nhập
                // trên search
                speciesList = filteredSpeciesList


                return false
            }

        })
    }

}
