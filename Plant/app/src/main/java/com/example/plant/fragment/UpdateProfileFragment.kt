package com.example.plant.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentUpdateProfileBinding
import com.example.plant.firebaseStorage.FirebaseStorage
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.User
import com.example.plant.util.FragmentUtil
import com.example.plant.util.ProgressBarLoading
import com.example.plant.util.ResolveImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.reflect.KClass

class UpdateProfileFragment : Fragment(),View.OnClickListener {
    private lateinit var binding: FragmentUpdateProfileBinding
    private var currentUser = User()
    private var selectedImageFileUri: Uri? = null
    private var selectedImageUrl: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        updateFields()
        binding.ivUpdateUserAvatar.setOnClickListener(this@UpdateProfileFragment)
        listenerEvent()
        return binding.root
    }

    private fun updateFields() {
        val bundle = this.arguments
        if (bundle != null) {
            currentUser = bundle?.let { User::class.getParcelable(it, constant.USER_DETAIL) }!!
            this.activity?.let { GlideLoader(it).loadUserPictureFromUrl(currentUser.avatar, binding.ivUpdateUserAvatar,R.drawable.placeholder) }
            binding.etUpdateName.setText(currentUser?.fullName)
            binding.etUpdateEmail.setText(currentUser?.email)
            binding.etUpdateEmail.isEnabled = false
            if (currentUser.phone != 0L) {
                binding.etUpdatePhone.setText(currentUser?.phone.toString())
            }
            binding.etUpdateGender.setText(currentUser?.gender)
            binding.etUpdateLocation.setText(currentUser?.location)
        }
    }

    private inline fun <reified T : Any> KClass<T>.getParcelable(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, T::class.java)
        else
            bundle.getParcelable(key)

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.ivUpdateUserAvatar -> {
                    // check if the permission is already allowed or need to request for it
                    if (ContextCompat.checkSelfPermission(
                            this.requireActivity(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //choose Image from extenal storage
                        ResolveImage().showImageChooser(requireActivity())
                    } else {
                        requestPermissions(
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            constant.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == constant.READ_STORAGE_PERMISSION_CODE) {
            //if permisstion is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "The storage permission is granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    activity,
                    "You just denied the permission for storage. You can also allow it from setting! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun setAvatarProfile(uri: Uri) {
        selectedImageFileUri = uri
        Glide
            .with(requireContext())
            .load(selectedImageFileUri)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.ivUpdateUserAvatar);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("Data", "DATA2")
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == constant.PICK_IMAGE_REQUEST_CODE) {
                Log.e("Data", "DATA: ${data?.data}")
                if (data != null) {
                    try {
                        selectedImageFileUri = data.data!!
                        Log.e("URI", "URI: ${selectedImageFileUri}")
                        GlideLoader(this.requireActivity()).LoadUserPicture(
                            selectedImageFileUri!!,
                            binding.ivUpdateUserAvatar
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this.requireActivity(), "Image selection failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun listenerEvent() {
        binding.btnUpdateInfo.setOnClickListener() {
            val userName = binding.etUpdateName.text.trim().toString()
            val phoneNum = binding.etUpdatePhone.text.trim().toString()
            val gender = binding.etUpdateGender.text.trim().toString()
            val location = binding.etUpdateLocation.text.trim().toString()
            val (isValidateName, NameMessError) = validateName(userName)
            val (isValidatePhoneNum, PhoneNumMessError) = validatePhoneNum(phoneNum)
            val (isValidateGender, GenderMessError) = validateGender(gender)
            val (isValidateLocation, LocationMessError) = validateLocation(location)

            if (!isValidateName) {
                binding.etUpdateName.error = NameMessError
            }
            if (!isValidatePhoneNum) {
                binding.etUpdatePhone.error = PhoneNumMessError
            }
            if (!isValidateGender) {
                binding.etUpdateGender.error = GenderMessError
            }
            if (!isValidateLocation) {
                binding.etUpdateLocation.error = LocationMessError
            }
            if (isValidateName && isValidatePhoneNum && isValidateGender && isValidateLocation) {
                val loadingDialog = ProgressBarLoading(activity)
                loadingDialog.startLoading()
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    val imageUrlDeferred = async(Dispatchers.Main) {
                        this@UpdateProfileFragment.activity?.let { it1 ->
                            FirebaseStorage().uploadImageToCloundStorage(
                                it1,
                                selectedImageFileUri
                            )
                        }
                    }
                    val userHashMap = HashMap<String, Any>()
                    selectedImageUrl = imageUrlDeferred.await()!!
                    if(selectedImageUrl.isNotEmpty()){
                        userHashMap[constant.USER_AVATAR] = selectedImageUrl
                    }
                    userHashMap[constant.USER_NAME] = userName
                    userHashMap[constant.USER_PHONE] = phoneNum.toString().trim().toLong()
                    userHashMap[constant.USER_GENDER] = gender
                    userHashMap[constant.USER_LOCATION] = location
                    Firestore().updateUserInfo(this@UpdateProfileFragment, userHashMap)
                    loadingDialog.hideLoading()
                }
            }
        }
    }
    private fun validateName(userName: String): Pair<Boolean, String> {
        if (userName.isEmpty()) {
            return Pair(false, "Name is not empty")
        } else if (userName.length > 30) {
            return Pair(false, "Name cannot exceed 30 characters!")
        }
        return Pair(true, "")
    }

    private fun validatePhoneNum(phoneNumber: String): Pair<Boolean, String> {
        val regex = "[0-9]+?".toRegex()
        if (phoneNumber.isEmpty()) {
            return Pair(false, "Phone Number is not empty!")
        } else if (!phoneNumber.matches(regex)) {
            return Pair(false, "Please enter valid Phone Number!")
        }
        return Pair(true, "")
    }

    private fun validateGender(gender: String): Pair<Boolean, String> {
        if (gender.isEmpty()) {
            return Pair(false, "Gender is not empty!")
        } else if (gender !in listOf("Male", "Female")) {
            return Pair(false, "Please enter Male or Female!")
        }
        return Pair(true, "")
    }

    private fun validateLocation(location: String): Pair<Boolean, String> {
        if (location.isEmpty()) {
            return Pair(false, "Gender is not empty!")
        }
        return Pair(true, "")
    }
    fun updateUserSuccessConfirm() {
        Toast.makeText(this@UpdateProfileFragment.activity, "Update User Infomation successed!", Toast.LENGTH_SHORT).show()
        FragmentUtil(activity).replaceFragment(HomeFragment(), R.id.HomeFrameLayout)
    }
}