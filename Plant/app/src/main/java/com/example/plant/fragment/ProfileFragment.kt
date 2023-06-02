package com.example.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.plant.R
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
        this.activity?.let { Firestore().getUserDetail(it, this) }
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

//            activity?.let{
//                val intent = Intent (it, UpdateProfileActivity::class.java)
//                val bundle = Bundle()
//                bundle.putParcelable(constant.USER_DETAIL, currentUser)
//                intent.putExtras(bundle)
//                startActivity(intent)
//                it.startActivity(intent)
//            }
        }
    }
}