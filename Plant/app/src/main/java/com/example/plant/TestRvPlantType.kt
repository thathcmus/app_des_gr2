package com.example.plant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.databinding.ActivityTestRvPlantTypeBinding
import com.google.firebase.firestore.FirebaseFirestore

class TestRvPlantType : AppCompatActivity() {

    private lateinit var binding: ActivityTestRvPlantTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestRvPlantTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPlant.apply{
            layoutManager = LinearLayoutManager(this@TestRvPlantType, LinearLayoutManager.HORIZONTAL, false)
        }

        fetchData()
    }

    private fun fetchData() {
        //tham chiếu collection plantType
        val plantTypeCollection = FirebaseFirestore.getInstance().collection("plantType")
        //tham chiếu collection plant
        val plantCollection = FirebaseFirestore.getInstance().collection("plant")

        plantTypeCollection.get()
            .addOnSuccessListener { plantTypeDocuments ->
                val plantTypes = plantTypeDocuments.toObjects(PlantTypeModel::class.java)
                for (plantType in plantTypes) {
                    //tìm type của plant trùng với type của plantType
                    plantCollection.whereEqualTo("plantType", plantType.name)
                        .get()
                        .addOnSuccessListener { plantDocuments ->
                            plantType.count = plantDocuments.size()
                            binding.rvPlant.adapter?.notifyDataSetChanged()
                        }
                        .addOnFailureListener { exception ->
                            // Xử lý khi truy vấn thất bại
                        }
                }

                binding.rvPlant.adapter = PlantTypeAdapter(this, plantTypes)
            }
            .addOnFailureListener { exception ->
                // Xử lý khi truy vấn thất bại
            }
    }

}