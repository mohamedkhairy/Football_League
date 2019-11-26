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
import com.example.footballleague.database.entity.Team
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.home_taem_item.view.*

class HomeViewAdapter(
    val context: Context,
    var teamList: MutableList<Team>,
    val clickListener: (Int) -> Unit,
    val homefavoritesListener: (Team, Boolean) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.home_taem_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as CardViewHolder).bindHome(
            context,
            teamList[position],
            position,
            clickListener,
            homefavoritesListener
        )
    }

    override fun getItemCount() = teamList.size

//    inline fun <reified T> checkType(value: List<comeList>): Boolean{
//        return  value is T
//    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            this.setIsRecyclable(false)
        }


        fun bindHome(
            context: Context,
            team: Team,
            position: Int,
            clickListener: (Int) -> Unit,
            homefavoritesListener: (Team, Boolean) -> Unit
        ) {
            val imageUrl = team.crestUrl
            itemView.team_name.text = team.shortName
            itemView.web_site.text = team.website
            itemView.team_color.text = team.clubColors
            itemView.venue.text = team.venue
            setSvgTeamImage(context, imageUrl)
            setCheckBox(team)

            itemView.setOnClickListener { clickListener(team.id) }
            itemView.save_CheckBox.tag = position
            itemView.save_CheckBox.setOnClickListener {
                if (itemView.save_CheckBox.isChecked)
                    homefavoritesListener(team, true)
                else
                    homefavoritesListener(team, false)
            }
            itemView.web_site.setOnClickListener { openTeamWebSite(team.website!!, context) }
        }

        private fun setCheckBox(team: Team) {
            itemView.save_CheckBox.isChecked = team.isFavorites
        }

        private fun openTeamWebSite(link: String, context: Context) {
            val customTabsIntentBuilder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            customTabsIntentBuilder.setToolbarColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            ).build().launchUrl(context, Uri.parse(link))

        }

        private fun setSvgTeamImage(context: Context, teamImage: String?) {
            val requestBuilder: RequestBuilder<PictureDrawable> = GlideToVectorYou
                .init()
                .with(context)
                .requestBuilder

            requestBuilder
                .load(teamImage)
                .error(R.drawable.soccer_ball)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().centerCrop())
                .into(itemView.team_image)
        }
    }
}