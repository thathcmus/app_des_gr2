package com.example.plant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.databinding.PhotographyItemBinding
import com.example.plant.databinding.PlantTypeItemBinding
import com.example.plant.model.Photography
import com.example.plant.model.PlantType
import com.tanphandev.recyclerview.`interface`.ItemClickListener
import kotlinx.android.synthetic.main.photography_item.view.tvPhotographyItem
import kotlinx.android.synthetic.main.photography_item.view.tvThumbnail
import kotlinx.android.synthetic.main.plant_type_item.view.ivPlantType
import kotlinx.android.synthetic.main.plant_type_item.view.tvTypeName

class PlantTypeAdapter (var plantTypeList: List<PlantType>, val context: Context) : RecyclerView.Adapter<PlantTypeAdapter.PlantTypeViewHolder>() {
    private lateinit var binding: PlantTypeItemBinding
    class PlantTypeViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        private var itemClickListener: ItemClickListener? = null

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener?) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View) {
            itemClickListener?.onClick(v, adapterPosition, false)
        }

        override fun onLongClick(v: View): Boolean {
            itemClickListener?.onClick(v, adapterPosition, true)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantTypeViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.plant_type_item, parent, false)
        return PlantTypeViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PlantTypeViewHolder, position: Int) {
        holder.itemView.apply {
            ivPlantType.setImageResource(plantTypeList[position].image)
            tvTypeName.text = plantTypeList[position].name
        }
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                if (isLongClick)
                    Toast.makeText(
                        context,
                        "Long Click: ${plantTypeList[position].name}",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    Toast.makeText(
                        context,
                        "CLICK ITEM ${plantTypeList[position].name}",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return plantTypeList.size
    }
}