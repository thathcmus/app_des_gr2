package com.example.plant.activities.UpdateProfile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plant.R
import com.example.plant.activities.Profile.ProfileActivity
import com.example.plant.constant.constant
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.User
import com.example.plant.util.ResolveImage
import kotlinx.android.synthetic.main.activity_update_profile.btnUpdateInfo
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateEmail
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateGender
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateLocation
import kotlinx.android.synthetic.main.activity_update_profile.etUpdateName
import kotlinx.android.synthetic.main.activity_update_profile.etUpdatePhone
import kotlinx.android.synthetic.main.activity_update_profile.ivUpdateUserAvatar
import java.io.IOException
import kotlin.reflect.KClass

class UpdateProfileActivity : AppCompatActivity(), View.OnClickListener {
    private var currentUser = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        supportActionBar?.hide()
        updateFields()
        ivUpdateUserAvatar.setOnClickListener(this@UpdateProfileActivity)
        listenerEvent()
    }
    private fun updateFields() {
        val bundle = intent.extras
        currentUser = bundle?.let { User::class.getParcelable(it,constant.USER_DETAIL ) }!!

        etUpdateName.setText(currentUser?.fullName)
        etUpdateEmail.setText(currentUser?.email)
        etUpdateEmail.isEnabled = false
        if(currentUser.phone != 0L)
        {
            etUpdatePhone.setText(currentUser?.phone.toString())
        }
        etUpdateGender.setText(currentUser?.gender)
        etUpdateLocation.setText(currentUser?.location)
    }

    inline fun <reified T : Any> KClass<T>.getParcelable(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, T::class.java)
        else
            bundle.getParcelable(key)

    override fun onClick(v: View?) {
        if(v != null) {
            when (v.id){
                R.id.ivUpdateUserAvatar -> {
                    // check if the permission is already allowed or need to request for it
                    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        //choose Image from extenal storage
                        ResolveImage().showImageChooser(this)
                    }else {
                        ActivityCompat.requestPermissions(
                            this,
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
        if(requestCode == constant.READ_STORAGE_PERMISSION_CODE){
            //if permisstion is granted
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"The storage permission is granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"You just denied the permission for storage. You can also allow it from setting! ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == constant.PICK_IMAGE_REQUEST_CODE) {
                if(data != null){
                    try {
                        val selectedImageFileUri = data.data!!
                        GlideLoader(this).LoadUserPicture(selectedImageFileUri, ivUpdateUserAvatar)
                    }catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Image selection failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun listenerEvent() {
        btnUpdateInfo.setOnClickListener() {
            val userName = etUpdateName.text.trim().toString()
            val phoneNum = etUpdatePhone.text.trim().toString()
            val gender = etUpdateGender.text.trim().toString()
            val location = etUpdateLocation.text.trim().toString()
            val (isValidateName,NameMessError) = validateName(userName)
            val (isValidatePhoneNum,PhoneNumMessError) = validatePhoneNum(phoneNum)
            val (isValidateGender,GenderMessError) = validateGender(gender)
            val (isValidateLocation,LocationMessError) = validateLocation(location)

            if(!isValidateName) {
                etUpdateName.error = NameMessError
            }
            if(!isValidatePhoneNum){
                etUpdatePhone.error = PhoneNumMessError
            }
            if(!isValidateGender){
                etUpdateGender.error = GenderMessError
            }
            if(!isValidateLocation){
                etUpdateLocation.error = LocationMessError
            }

            if(isValidateName && isValidatePhoneNum && isValidateGender && isValidateLocation){
                val userHashMap = HashMap<String, Any>()
                userHashMap[constant.USER_NAME] = userName
                userHashMap[constant.USER_PHONE] = phoneNum.toString().trim().toLong()
                userHashMap[constant.USER_GENDER] = gender
                userHashMap[constant.USER_LOCATION] = location
                Firestore().updateUserInfo(this, userHashMap)
            }
        }
    }
    fun validateName(userName: String): Pair<Boolean, String> {
        if(userName.isEmpty()){
            return Pair(false,"Name is not empty")
        }else if(userName.length > 30){
            return Pair(false,"Name cannot exceed 30 characters!")
        }
        return Pair(true, "")
    }

    fun validatePhoneNum(phoneNumber: String): Pair<Boolean,String> {
        val regex = "[0-9]+?".toRegex()
        if(phoneNumber.isEmpty()){
            return Pair(false,"Phone Number is not empty!")
        }else if(!phoneNumber.matches(regex)){
            return Pair(false, "Please enter valid Phone Number!")
        }
        return Pair(true, "")
    }
    fun validateGender(gender: String): Pair<Boolean,String> {
        if(gender.isEmpty()){
            return Pair(false,"Gender is not empty!")
        }else if(gender !in listOf("Male","Female")){
            return Pair(false, "Please enter Male or Female!")
        }
        return Pair(true, "")
    }
    fun validateLocation(location: String): Pair<Boolean,String> {
        if(location.isEmpty()){
            return Pair(false,"Gender is not empty!")
        }
        return Pair(true, "")
    }
    fun updateUserSuccessConfirm() {
        Toast.makeText(this, "Update User Infomation successed!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}