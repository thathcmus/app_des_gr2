package com.example.plant.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        listenEvent()
        return binding.root
    }

    private fun listenEvent() {
            binding.btnLogout.setOnClickListener() {
            val stillLoginPre = this.activity?.getSharedPreferences("stillLogin", Context.MODE_PRIVATE)
            val editor = stillLoginPre?.edit()
            editor?.putBoolean("isLoggedIn", false)
            editor?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}