package com.example.plant.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.plant.AddingNewActivity
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
            uploadImage()
        }

        storageRef = FirebaseStorage.getInstance().reference.child("Photography")
        firebaseFirestore = FirebaseFirestore.getInstance()



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