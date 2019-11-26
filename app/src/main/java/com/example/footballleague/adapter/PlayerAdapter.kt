package com.example.footballleague.adapter

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.footballleague.R
import com.example.footballleague.database.entity.ActiveCompetition
import com.example.footballleague.database.entity.Favorites
import com.example.footballleague.database.entity.Squad
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.details_item.view.*
import kotlinx.android.synthetic.main.home_taem_item.view.*

class PlayerAdapter <T> (var dataList: List<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.details_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (checkType<List<Squad>>(dataList) && !checkType<List<ActiveCompetition>>(dataList)) {
            val squad = (dataList as List<Squad>)
            (holder as CardViewHolder).bindPlayer( squad[position])
        }
        else if (!checkType<List<Squad>>(dataList) && checkType<List<ActiveCompetition>>(dataList)){
            val activeCompetition = (dataList as List<ActiveCompetition>)
            (holder as CardViewHolder).bindCompetition( activeCompetition[position])
        }
    }

    override fun getItemCount() = dataList.size



    inline fun <reified Check> checkType(value: List<T>): Boolean{
        return  value is Check
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindPlayer(data: Squad) {
            itemView.player_name.text = data.name
            itemView.player_country.text = data.countryOfBirth
                if (data.position.isNullOrEmpty()){
                    itemView.player_position.text = data.role
                }else{
                    itemView.player_position.text = data.position

                }

        }

        fun bindCompetition(data: ActiveCompetition) {

            itemView.player_name.text = data.name
            itemView.player_country.text = data.plan
            itemView.player_position.text = data.lastUpdated
        }


    }
}