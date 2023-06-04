package com.example.plant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class FragmentHomeActivity : AppCompatActivity() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPlant.apply{
            layoutManager = LinearLayoutManager(this@FragmentHomeActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvPhotography.apply{
            layoutManager = LinearLayoutManager(this@FragmentHomeActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        fetchData()
    }

    private fun fetchData() {
        //Lấy dữ liệu của collection plant và plantType đưa vào rvPlant
        //tham chiếu collection plantType
        val plantTypeCollection = FirebaseFirestore.getInstance().collection("plantType")
        //tham chiếu collection plant
        val plantCollection = FirebaseFirestore.getInstance().collection("plant")

        plantTypeCollection.get()
            .addOnSuccessListener { plantTypeDocuments ->
                val plantTypes = plantTypeDocuments.toObjects(PlantTypeHomeModel::class.java)
                for (plantType in plantTypes) {
                    //tìm plantType của plant trùng giá trị với type của plantType
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

                binding.rvPlant.adapter = PlantTypeHomeAdapter(this, plantTypes)
            }
            .addOnFailureListener { exception ->
                // Xử lý khi truy vấn thất bại
            }


        // Lấy dữ liệu của collection photography đưa vào RecyclerView rvPhotography
        FirebaseFirestore.getInstance().collection("photography")
            .get()
            .addOnSuccessListener { photoDocuments ->
                for(document in photoDocuments) {
                    val photography = photoDocuments.toObjects(PhotographyHomeModel::class.java)
                    binding.rvPhotography.adapter = PhotographyHomeAdapter(this, photography)
                }
            }
            .addOnFailureListener { exception ->
                // Xử lý khi truy vấn thất bại
            }
    }

}