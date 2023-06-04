package com.example.plant.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.databinding.PhotographyItemBinding
import com.example.plant.glide.GlideLoader
import com.example.plant.model.Photography
import com.tanphandev.recyclerview.`interface`.ItemClickListener
import kotlinx.android.synthetic.main.photography_item.view.tvPhotographyItem
import kotlinx.android.synthetic.main.photography_item.view.tvThumbnail

class PhotographyAdaper (var photographyList: List<Photography>, val context: Context) : RecyclerView.Adapter<PhotographyAdaper.PhotographyViewHolder>() {
    private lateinit var binding: PhotographyItemBinding
    class PhotographyViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotographyViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.photography_item, parent, false)
        return PhotographyViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PhotographyViewHolder, position: Int) {
        GlideLoader(context).loadUserPictureFromUrl(photographyList[position].picture,holder.itemView.tvPhotographyItem,R.drawable.placeholderloading)
        holder.itemView.apply {
            tvThumbnail.text = photographyList[position].thumbnail
        }
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                if (isLongClick)
                    Toast.makeText(
                        context,
                        "Long Click: ${photographyList[position].thumbnail}",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    Toast.makeText(
                        context,
                        "CLICK ITEM ${photographyList[position].thumbnail}",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return photographyList.size
    }
}