package com.example.plant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotographyHomeAdapter(val context: Context, private val photography: List<PhotographyHomeModel>):RecyclerView.Adapter<PhotographyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotographyViewHolder {
        return PhotographyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_home_photography, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return photography.size
    }

    override fun onBindViewHolder(holder: PhotographyViewHolder, position: Int) {
        val photo = photography[position]
        holder.thumbnail.text = "#${photo.thumbnail}"

        Glide.with(context)
            .load(photo.picture)
            .into(holder.picture)
    }
}

class PhotographyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val thumbnail: TextView = itemView.findViewById(R.id.tvThumbnail)
    val picture: ImageView = itemView.findViewById(R.id.ivPlantPhotography)
}