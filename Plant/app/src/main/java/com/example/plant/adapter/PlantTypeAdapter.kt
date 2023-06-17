package com.example.plant.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.constant.constant
import com.example.plant.databinding.PhotographyItemBinding
import com.example.plant.databinding.PlantTypeItemBinding
import com.example.plant.firestore.Firestore
import com.example.plant.fragment.PlantFragment
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Photography
import com.example.plant.model.Plant
import com.example.plant.model.PlantType
import com.example.plant.util.FragmentUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.tanphandev.recyclerview.`interface`.ItemClickListener
import kotlinx.android.synthetic.main.photography_item.view.tvPhotographyItem
import kotlinx.android.synthetic.main.photography_item.view.tvThumbnail
import kotlinx.android.synthetic.main.plant_type_item.view.ivPlantType
import kotlinx.android.synthetic.main.plant_type_item.view.tvTypeName
import kotlinx.android.synthetic.main.plant_type_item.view.tvTypeQuantity

class PlantTypeAdapter (val mFragement: Fragment, var plantTypeList: List<PlantType>, val context: Context) : RecyclerView.Adapter<PlantTypeAdapter.PlantTypeViewHolder>() {
    private lateinit var binding: PlantTypeItemBinding
//    private var plantList: MutableList<Plant> = mutableListOf()

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
            GlideLoader(context).loadUserPictureFromUrl(plantTypeList[position].image,holder.itemView.ivPlantType, R.drawable.placeholderloading)
            tvTypeName.text = plantTypeList[position].name
            tvTypeQuantity.text = plantTypeList[position].count.toString()
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
                {
                    Firestore().getPlantListOfPlantType(plantTypeList[position].name) { plantList ->
                        val plantFrag = PlantFragment()
                        val bundle = Bundle()
                        bundle.putParcelableArrayList(constant.PLANT, plantList as ArrayList<Plant>)
                        bundle.putString(constant.PLANT_TYPE, plantTypeList[position].name)
                        plantFrag.arguments = bundle
                        FragmentUtil(mFragement.requireActivity()).replaceFragment(plantFrag, R.id.HomeFrameLayout, true)
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return plantTypeList.size
    }
}