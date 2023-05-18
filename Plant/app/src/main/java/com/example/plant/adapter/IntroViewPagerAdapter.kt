package com.example.plant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import kotlinx.android.synthetic.main.intro_item.view.contentIntroItem
import kotlinx.android.synthetic.main.intro_item.view.headerIntroItem
import kotlinx.android.synthetic.main.intro_item.view.ivIntroItem

class IntroViewPagerAdapter(private var images : List<Int>, private  var titles: List<String>, private var content : List<String>): RecyclerView.Adapter<IntroViewPagerAdapter.Pager2ViewHolder>() {
    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.intro_item,parent,false))
    }

    override fun onBindViewHolder(holder: IntroViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemView.apply {
            ivIntroItem.setImageResource(images[position])
            headerIntroItem.text = titles[position]
            contentIntroItem.text = content[position]
        }
    }
    override fun getItemCount(): Int {
        return titles.size
    }
}