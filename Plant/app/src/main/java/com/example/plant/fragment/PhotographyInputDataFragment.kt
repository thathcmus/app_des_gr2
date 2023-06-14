package com.example.plant.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.plant.activities.Home.HomeActivity
import com.example.plant.databinding.FragmentPhotographyInputDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PhotographyInputDataFragment(private val imageUri: Uri?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPhotographyInputDataBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotographyInputDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            if(isValidateInput()) {
                uploadImage()
            }
        }

        storageRef = FirebaseStorage.getInstance().reference.child("Photography")
        firebaseFirestore = FirebaseFirestore.getInstance()

    }

    companion object{
        const val ERROR_PLANT_NAME_EMPTY = "The plant name must not be empty"
        const val ERROR_PLANT_NAME_LENGTH = "The plant name cannot exceed 30 characters"
        const val ERROR_PLANT_DESCRIPTION_EMPTY = "The plant description must not be empty"
        const val ERROR_PLANT_DESCRIPTION_LENGTH = "This field requires a minimum of 50 characters"
        const val ERROR_PLANT_FAMILY_EMPTY = "The plant family must not be empty"
        const val ERROR_PLANT_KINGDOM_EMPTY = "The plant kingdom must not be empty"
        const val ERROR_PLANT_TYPE_EMPTY = "The type of plant must not be empty"
        const val ERROR_PLANT_STAR_EMPTY = "The plant star must not be empty"
        const val ERROR_PLANT_SPECIES_RANGE = "This field only accepts a number from 0 to 5"
    }

    private fun validatePlantName(plantName: String): Pair<Boolean, String> {
        if (plantName.isEmpty()) {
            return Pair(false, ERROR_PLANT_NAME_EMPTY)
        } else if (plantName.length > 30) {
            return Pair(false, ERROR_PLANT_NAME_LENGTH)
        }
        return Pair(true, "")
    }

    private fun validatePlantDescription(plantDescription: String): Pair<Boolean, String> {
        if (plantDescription.isEmpty()) {
            return Pair(false, ERROR_PLANT_DESCRIPTION_EMPTY)
        } else if (plantDescription.length < 50) {
            return Pair(false, ERROR_PLANT_DESCRIPTION_LENGTH)
        }
        return Pair(true, "")
    }

    private fun validatePlantFamily(plantFamily: String): Pair<Boolean, String> {
        if (plantFamily.isEmpty()) {
            return Pair(false, ERROR_PLANT_FAMILY_EMPTY)
        }
        return Pair(true, "")
    }

    private fun validatePlantKingdom(plantKingdom: String): Pair<Boolean, String> {
        if (plantKingdom.isEmpty()) {
            return Pair(false, ERROR_PLANT_KINGDOM_EMPTY)
        }
        return Pair(true, "")
    }

    private fun validatePlantType(plantType: String): Pair<Boolean, String> {
        if (plantType.isEmpty()) {
            return Pair(false, ERROR_PLANT_TYPE_EMPTY)
        }
        return Pair(true, "")
    }

    private fun validatePlantStar(plantStar: String): Pair<Boolean, String> {
        if (plantStar.isEmpty()) {
            return Pair(false, ERROR_PLANT_STAR_EMPTY)
        }
        return Pair(true, "")
    }

    private fun validatePlantSpecies(PlantSpecies: String): Pair<Boolean, String> {
        if (PlantSpecies.isEmpty()) {
            return Pair(false, ERROR_PLANT_SPECIES_RANGE)
        }
        return Pair(true, "")
    }

    private fun isValidateInput(): Boolean {
        val plantName = binding.photoName.text?.trim().toString()
        val (isValidatePlantName, PlantNameMessError) = validatePlantName(plantName)
        if (!isValidatePlantName) {
            binding.photoName.error = PlantNameMessError
        }

        val plantDescription = binding.photoDescription.text?.trim().toString()
        val (isValidatePlantDescription, PlantDescriptionMessError) = validatePlantDescription(plantDescription)
        if (!isValidatePlantDescription) {
            binding.photoDescription.error = PlantDescriptionMessError
        }

        val plantFamily = binding.photoFamily.text?.trim().toString()
        val (isValidatePlantFamily, PlantFamilyMessError) = validatePlantFamily(plantFamily)
        if (!isValidatePlantFamily) {
            binding.photoFamily.error = PlantFamilyMessError
        }

        val plantKingdom = binding.photoKingdom.text?.trim().toString()
        val (isValidatePlantKingdom, PlantKingdomMessError) = validatePlantKingdom(plantKingdom)
        if (!isValidatePlantKingdom) {
            binding.photoKingdom.error = PlantKingdomMessError
        }

        val plantType = binding.photoPlantType.text?.trim().toString()
        val (isValidatePlantType, PlantTypeMessError) = validatePlantType(plantType)
        if (!isValidatePlantType) {
            binding.photoPlantType.error = PlantTypeMessError
        }

        val plantStar = binding.photoStar.text?.trim().toString()
        val (isValidatePlantStar, PlantStarMessError) = validatePlantStar(plantStar)
        if (!isValidatePlantStar) {
            binding.photoStar.error = PlantStarMessError
        }

        val plantSpecies = binding.photoSpecies.text?.trim().toString()
        val (isValidatePlantSpecies, PlantSpeciesMessError) = validatePlantSpecies(plantSpecies)
        if (!isValidatePlantSpecies) {
            binding.photoSpecies.error = PlantSpeciesMessError
        }

        if(isValidatePlantName && isValidatePlantDescription
            && isValidatePlantFamily && isValidatePlantKingdom
            && isValidatePlantType && isValidatePlantStar
            && isValidatePlantSpecies){
            return true
        }

        return false
    }

    private fun uploadImage() {
        // Tạo tài liệu mới trong Firestore
        val newDocumentRef = firebaseFirestore.collection("plant").document()

        // Lấy ID của tài liệu mới tạo
        val documentId = newDocumentRef.id

        // Tạo đường dẫn con trong Storage dựa trên thời gian hiện tại
        val storageRef = storageRef.child(System.currentTimeMillis().toString())

        // Kiểm tra imageUri có tồn tại hay không với 'let'
        imageUri?.let { uri ->
            // Tệp ảnh được tải lên Storage với putFile()
            storageRef.putFile(uri).addOnCompleteListener { task ->
                if (task.isSuccessful) { // Nếu tải lên thành công
                    // Dùng 'downloadUrl' để lấy đường dẫn tới tệp đã tải lên
                    storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        val map = HashMap<String, Any>() // Sử dụng HashMap để lưu đường dẫn và các trường khác
                        map["image"] = imageUrl.toString()
                        map["name"] = binding.photoName.text.toString()
                        map["description"] = binding.photoDescription.text.toString()
                        map["plantType"] = binding.photoPlantType.text.toString()
                        map["family"] = binding.photoFamily.text.toString()
                        map["kingdom"] = binding.photoKingdom.text.toString()
                        map["species"] = binding.photoSpecies.text.toString()
                        map["star"] = binding.photoStar.text.toString()
                        map["id"] = documentId

                        // Cập nhật dữ liệu vào tài liệu trên Firestore
                        newDocumentRef.set(map).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful) {
                                Toast.makeText(requireContext(), "Adding new plant successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            // đóng bottom sheet
                            dismiss()
                            startActivity(Intent(requireContext(), HomeActivity::class.java))
                        }
                    }
                } else { // Nếu tải lên thất bại
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                    // đóng bottom sheet
                    dismiss()
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                }
            }
        }

    }


}