package com.example.plant.util

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.plant.R

public class ProgressBarLoading(val mActivity: Activity?) {
    private lateinit var dialog: AlertDialog

    fun startLoading() {
        //setView
         val dialogView = mActivity?.layoutInflater?.inflate(R.layout.progress_bar,null)
        //setDialog
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }
    fun hideLoading()
    {
        dialog.dismiss()
    }
}