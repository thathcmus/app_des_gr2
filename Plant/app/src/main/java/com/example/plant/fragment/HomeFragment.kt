package com.example.plant.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.activities.Login.LoginActivity
import com.example.plant.adapter.PhotographyAdaper
import com.example.plant.databinding.FragmentHomeBinding
import com.example.plant.model.Photography

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private var photographyList: MutableList<Photography> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        //get data
        getPhotographyList()
        showUI()
        listenEvent()
        return binding.root
    }
    fun showUI() {
        binding.rcPhotography.adapter = this.activity?.let {
            PhotographyAdaper(photographyList,
                it
            )
        }
        binding.rcPhotography.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcPhotography.setHasFixedSize(true)
    }
    private fun getPhotographyList() {
        photographyList.add(Photography(R.drawable.photography_item, "#mini"))
        photographyList.add(Photography(R.drawable.photography_item2, "#cute"))
        photographyList.add(Photography(R.drawable.photography_item3, "#small"))
        photographyList.add(Photography(R.drawable.photography_item4, "#danger"))
    }
    private fun listenEvent() {

    }
}