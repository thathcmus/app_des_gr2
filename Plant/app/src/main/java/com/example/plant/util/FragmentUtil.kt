package com.example.plant.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class FragmentUtil(val activity: FragmentActivity?) {
    private val fragmentManager: FragmentManager = activity!!.supportFragmentManager
    fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = false) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}