package com.example.plant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.model.Species
import com.example.plant.util.Helpers.Companion.sectionsHelper
import kotlinx.android.synthetic.main.species_item.view.tv_alphabet
import java.util.Locale

class SpeciesRecyclerAdapter(private val speciesList: MutableList<Species>, val listener: MyClickListener) :
    RecyclerView.Adapter<SpeciesRecyclerAdapter.ViewHolder>(), SectionIndexer {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    private lateinit var mListener: MyClickListener

    interface MyClickListener {
        fun onClick(position: Int) {
        }
    }
    override fun getItemCount(): Int {
        return speciesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.species_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.mTextView.text = mDataArray!![position]
        val data = speciesList[position]
        holder.itemView.apply {
            tv_alphabet.text = speciesList[position].name
        }
    }

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getSections(): Array<String> {
        val sections: MutableList<String> = ArrayList(27)
        val alphabetFull = ArrayList<String>()
        mSectionPositions = ArrayList()
        run {
            var i = 0
            val size = speciesList.size
            while (i < size) {
                val section = speciesList[i].name[0].toString().uppercase(Locale.getDefault())
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions?.add(i)
                }
                i++
            }
        }
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
        sectionsTranslator = sectionsHelper(sections, alphabetFull)
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionsTranslator[sectionIndex]!!]
    }
}