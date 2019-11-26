package com.example.footballleague.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.database.entity.ActiveCompetition
import kotlinx.android.synthetic.main.details_item.view.*

class CompetitionAdapter(var dataList: List<ActiveCompetition>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.details_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardViewHolder).bindCompetition(dataList[position])
    }

    override fun getItemCount() = dataList.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCompetition(data: ActiveCompetition) {

            itemView.player_name.text = data.name
            itemView.player_country.text = data.plan
            itemView.player_position.text = data.lastUpdated
        }


    }
}