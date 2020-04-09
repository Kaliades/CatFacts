package com.simon_kulinski.catfacts.ui.cat_facts_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.simon_kulinski.catfacts.R
import com.simon_kulinski.catfacts.domain.models.CatFact
import kotlinx.android.synthetic.main.item_list.view.*
import kotlin.random.Random

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var dataSet: List<CatFact> = emptyList()

    data class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val id = view.catFactsListItem_id
        private val text = view.catFactsListItem_text
        private val icon = view.catFactsListItem_icon
        private val card = view.catFactsListItem_card
        fun bind(catFact: CatFact) {
            id.text = catFact.id
            text.text = catFact.text
            icon.setImageResource(listOfIcons[Random.nextInt(0, 7)])
            card.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.action_catFactsListFragment_to_catFactDetailsFragment)
            }
        }

        companion object {
            private var listOfIcons = listOf(
                R.drawable.ic_cat_1,
                R.drawable.ic_cat_2,
                R.drawable.ic_cat_3,
                R.drawable.ic_cat_4,
                R.drawable.ic_cat_5,
                R.drawable.ic_cat_6,
                R.drawable.ic_cat_7,
                R.drawable.ic_cat_8
            )
        }
    }

    fun setUpDataSet(dataSet: List<CatFact>) {
        this.dataSet = dataSet
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val catFact = dataSet[position]
        holder.bind(catFact)
    }
}