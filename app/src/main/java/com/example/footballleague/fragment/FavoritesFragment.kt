package com.example.footballleague.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import arrow.core.Eval
import com.example.footballleague.R
import com.example.footballleague.adapter.FavoriteAdapter
import com.example.footballleague.fragment.home.HomeViewModel
import com.example.footballleague.fragment.team.TeamInfoFragment
import com.example.footballleague.fragment.team.TeamInfoViewModel
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.favorites_layout.*
import javax.inject.Inject

class FavoritesFragment: DaggerFragment() {

    private lateinit var favAdapter: FavoriteAdapter

    @Inject
        lateinit var providerFactory: ViewModelProviderFactory

        private val viewModel: Eval<HomeViewModel> = Eval.later {
            ViewModelProviders.of(this, providerFactory).get(HomeViewModel::class.java)
        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
//            teamId = this.arguments?.getInt("Team_ID")

        }


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val view = inflater.inflate(R.layout.favorites_layout, container, false)

            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setAcrionBar()
            initView()
            getFavoritesList()


        }

    private fun setAcrionBar(){

        setHasOptionsMenu(true)
        activity?.let {
            val  appBar = (it as AppCompatActivity).supportActionBar
            appBar?.title = "Favorites"
            appBar?.setDisplayHomeAsUpEnabled(true)
            appBar?.setHomeButtonEnabled(true)
        }
    }

    fun initView(){
        val recyclerManeger = GridLayoutManager(activity, 2)
        fav_recyclerview.layoutManager = recyclerManeger
        fav_recyclerview.hasFixedSize()
        favAdapter = FavoriteAdapter(this.context!!,
            mutableListOf(),
            { teamId: Int -> teamItemClicked(teamId) },
            { teamID: Int, ischecked: Boolean -> deleteFavorites(teamID , ischecked)} )

        fav_recyclerview.adapter = favAdapter
    }

    private fun deleteFavorites(id: Int , ischecked: Boolean) {


        if (ischecked) {
//            val favoritesTeams = Favorites(team.id, team.crestUrl, team.name, team.clubColors, team.venue, team.website)
//            viewModel.value().insertFavorites(favoritesTeams)
            Log.d("xxxx" , "check $id")
        } else {

            viewModel.value().deleteFavorite(id)
        }

    }

    private fun getFavoritesList(){

        viewModel.value().getLiveFavorites().observe(this , Observer {favData ->

            Log.d("xxxx" , "size ${favData?.size}")

//            if (favData?.isEmpty()) {


            favData?.let {
                favAdapter.favList.addAll(it)
                favAdapter.notifyDataSetChanged()
            }
//                }
        })
    }


    private fun teamItemClicked(teamId: Int) {

        val teamFragment = TeamInfoFragment.newInstance(teamId)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fav_frame, teamFragment)?.addToBackStack(TeamInfoFragment.TAG)?.commit()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val item = menu.findItem(R.id.action_fav)
        item.setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                fragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    companion object {

        const val TAG = "FavoriteFragment"

    }
}