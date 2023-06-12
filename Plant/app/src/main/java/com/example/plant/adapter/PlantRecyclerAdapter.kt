package com.example.plant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Plant
import kotlinx.android.synthetic.main.fragment_plant_detail.view.imageView
import kotlinx.android.synthetic.main.plant_item.view.ivPlant
import kotlinx.android.synthetic.main.plant_item.view.tvPlantDescription
import kotlinx.android.synthetic.main.plant_item.view.tvPlantFamily
import kotlinx.android.synthetic.main.plant_item.view.tvPlantKingdom
import kotlinx.android.synthetic.main.plant_item.view.tvPlantName

class PlantRecyclerAdapter(val plantList: ArrayList<Plant>, val listener: MyClickListener) : RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onClick(position)

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
            GlideLoader(context).loadUserPictureFromUrl(plantList[position].image,holder.itemView.ivPlant, R.drawable.placeholderloading)
            tvPlantName.text = plantList[position].name
            tvPlantKingdom.text = plantList[position].kingdom
            tvPlantFamily.text = plantList[position].family
            tvPlantDescription.text = plantList[position].content
        }
    }
    override fun getItemCount(): Int {
        return plantList.size
    }
    interface MyClickListener{
        fun onClick(position: Int){
        }
    }

}