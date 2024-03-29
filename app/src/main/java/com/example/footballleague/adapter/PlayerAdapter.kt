package com.example.footballleague.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.footballleague.R
import com.example.footballleague.database.entity.Squad
import kotlinx.android.synthetic.main.details_item.view.*

class PlayerAdapter(var dataList: List<Squad>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.details_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as CardViewHolder).bindPlayer(dataList[position])
    }

    override fun getItemCount() = dataList.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindPlayer(data: Squad) {
            itemView.player_name.text = data.name
            itemView.player_country.text = data.countryOfBirth
            if (data.position.isNullOrEmpty()) {
                itemView.player_position.text = data.role
            } else {
                itemView.player_position.text = data.position

            }

        }

    }
}