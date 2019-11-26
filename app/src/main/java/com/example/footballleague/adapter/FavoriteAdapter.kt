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
import com.example.footballleague.database.entity.Favorites
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.home_taem_item.view.*

class FavoriteAdapter(
    val context: Context,
    var favList: MutableList<Favorites>,
    val clickListener: (Int) -> Unit,
    val favoritesListener: (Int, Boolean) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.home_taem_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as CardViewHolder).bindFav(
            context,
            favList[position],
            clickListener,
            favoritesListener
        )
    }

    override fun getItemCount() = favList.size


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindFav(
            context: Context,
            fav: Favorites,
            clickListener: (Int) -> Unit,
            favoritesListener: (Int, Boolean) -> Unit
        ) {
            this.setIsRecyclable(false)
            val imageUrl = fav.image
            itemView.team_name.text = fav.teamName
            itemView.web_site.text = fav.webSite
            setSvgTeamImage(context, imageUrl)

            itemView.save_CheckBox.isChecked = true
            itemView.setOnClickListener { clickListener(fav.id) }
            itemView.save_CheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                favoritesListener(
                    fav.id,
                    isChecked
                )
            }
            itemView.web_site.setOnClickListener { openTeamWebSite(fav.webSite!!, context) }
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