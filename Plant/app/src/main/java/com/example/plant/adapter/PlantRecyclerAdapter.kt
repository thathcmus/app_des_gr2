package com.example.plant.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.fragment.PlantDetailFragment
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Plant
import com.example.plant.util.FragmentUtil
import kotlinx.android.synthetic.main.plant_item.view.ivPlant
import kotlinx.android.synthetic.main.plant_item.view.tvDescPlant
import kotlinx.android.synthetic.main.plant_item.view.tvFamily
import kotlinx.android.synthetic.main.plant_item.view.tvPlantKingdom
import kotlinx.android.synthetic.main.plant_item.view.tvPlantName

class PlantRecyclerAdapter(val mFragment: Fragment, val plantList: ArrayList<Plant>) : RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val plantDetailFragment = PlantDetailFragment()
                val bundle = Bundle()
                bundle.putParcelable(constant.PLANT, plantList[position])
                plantDetailFragment.arguments = bundle
                FragmentUtil(mFragment.activity).replaceFragment(plantDetailFragment,
                    R.id.HomeFrameLayout,true)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = plantList[position]
        holder.itemView.apply {
            GlideLoader(context).loadUserPictureFromUrl(data.image,holder.itemView.ivPlant, R.drawable.placeholderloading)
            tvPlantName.text = data.name
            tvPlantKingdom.text = data.kingdom
            tvFamily.text = data.family
            tvDescPlant.text = data.description
        }
    }
    override fun getItemCount(): Int {
        return plantList.size
    }

}