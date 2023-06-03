package com.example.plant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PlantTypeAdapter(val context: Context, private val plantType: List<PlantTypeModel>):RecyclerView.Adapter<PlantTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantTypeViewHolder {
        return PlantTypeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return plantType.size
    }

    override fun onBindViewHolder(holder: PlantTypeViewHolder, position: Int) {
        val plant = plantType[position]
        holder.plantName.text = plant.name
        holder.numberOfPlant.text = plant.count.toString()

        Glide.with(context)
            .load(plant.image)
            .into(holder.pic)
    }

}

class PlantTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val plantName: TextView = itemView.findViewById(R.id.tvPlantTypeName)
    val pic: ImageView = itemView.findViewById(R.id.ivPlant)
    val numberOfPlant: TextView = itemView.findViewById(R.id.tvNumberOfType)
}