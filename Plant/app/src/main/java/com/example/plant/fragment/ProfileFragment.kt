package com.example.plant.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.constant.constant
import com.example.plant.databinding.FragmentProfileBinding
import com.example.plant.firestore.Firestore
import com.example.plant.glide.GlideLoader
import com.example.plant.model.User
import com.example.plant.util.FragmentUtil
import kotlinx.android.synthetic.main.fragment_profile.ivAvatarUser
import kotlinx.android.synthetic.main.fragment_profile.ivLocationIcon
import kotlinx.android.synthetic.main.fragment_profile.tvLocationUser
import kotlinx.android.synthetic.main.fragment_profile.tvNameUser

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private var currentUser = User()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        Firestore().getUserDetail( this)
        listenerEvent()
        return binding.root
    }
    fun setCurrentUser(currentUser: User){
        this.currentUser = currentUser
    }
    fun ShowUIInfo(UserInfo : User){
        this.activity?.let { GlideLoader(it).loadUserPictureFromUrl(UserInfo.avatar,ivAvatarUser) }
        tvNameUser.text = UserInfo?.fullName
        tvLocationUser.text = UserInfo?.location
        if (UserInfo?.location != "") {
            ivLocationIcon.isVisible = true
        }
    }
    private fun listenerEvent() {
        binding.editUser.setOnClickListener() {
            val updateProfileFrag = UpdateProfileFragment()
            val bundle = Bundle()
            bundle.putParcelable(constant.USER_DETAIL, currentUser)
            updateProfileFrag.arguments = bundle
            FragmentUtil(this.activity).replaceFragment(updateProfileFrag, R.id.HomeFrameLayout)
        }
        binding.tvSignOut.setOnClickListener() {
            val stillLoginPre = this.activity?.getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
            val editor = stillLoginPre?.edit()
            editor?.putBoolean("isLoggedIn", false)
            editor?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}