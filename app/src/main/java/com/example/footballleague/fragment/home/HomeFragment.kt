package com.example.footballleague.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Eval
import com.example.footballleague.R
import com.example.footballleague.adapter.FavoriteAdapter
import com.example.footballleague.adapter.HomeViewAdapter
import com.example.footballleague.database.entity.Favorites
import com.example.footballleague.database.entity.Team
import com.example.footballleague.fragment.FavoritesFragment
import com.example.footballleague.fragment.team.TeamInfoFragment
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_taem_item.*
import kotlinx.android.synthetic.main.home_taem_item.view.*
import javax.inject.Inject


class HomeFragment: DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory



    lateinit var adapter: HomeViewAdapter

    private lateinit var homeFrame: FrameLayout


    private val viewModel: Eval<HomeViewModel> = Eval.later {
        ViewModelProviders.of(this, providerFactory).get(HomeViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setAcrionBar()
        observeHomeView()
        prepareView()
        viewModel.value().getFootballTeams()
        homeFrame = view.findViewById<FrameLayout>(R.id.home_frame)

    }

    private fun setAcrionBar(){

        setHasOptionsMenu(true)
        activity?.let {
            val  appBar = (it as AppCompatActivity).supportActionBar
            appBar?.title = getString(R.string.app_name)
            appBar?.setDisplayHomeAsUpEnabled(false)
            appBar?.setHomeButtonEnabled(false)
        }
    }

    fun observeHomeView(){
        viewModel.value().getTeamsData().observe(this , Observer { teamData ->
            if(teamData != null)
            if (!teamData.isEmpty()) {
                adapter.teamList.clear()
                adapter.teamList.addAll(teamData)
                adapter.notifyDataSetChanged()

            }
        })

        viewModel.value().getLoading().observe(this , Observer { isloading ->
            if (isloading)
                showLoading()
            else
                hideLoading()
        })

    }


    private fun prepareView() {

        val recyclerManeger = GridLayoutManager(activity, 2)
        home_recyclerview.layoutManager = recyclerManeger
        home_recyclerview.hasFixedSize()
        adapter = HomeViewAdapter(this.context!!,
            mutableListOf(),
            { teamId: Int -> teamItemClicked(teamId) },
            { team: Team, ischecked: Boolean -> addTofavorites(team , ischecked)} )

        home_recyclerview.adapter = adapter
    }


    private fun teamItemClicked(teamId: Int) {

        val teamFragment = TeamInfoFragment.newInstance(teamId)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.home_frame, teamFragment)?.addToBackStack(TeamInfoFragment.TAG)?.commit()

    }

    private fun addTofavorites(team: Team , ischecked: Boolean) {


            if (ischecked) {
                val favoritesTeams = Favorites(team.id, team.crestUrl, team.name, team.clubColors, team.venue, team.website)
                viewModel.value().insertFavorites(favoritesTeams)
            } else {
                viewModel.value().deleteFavorite(team.id)
            }

    }

    private fun hideLoading(){
        homeloading.visibility = View.GONE
    }

    private fun showLoading(){
        homeloading.visibility = View.VISIBLE
    }


//    private fun deleteFavorites(id: Int , ischecked: Boolean) {
//
//
//        if (ischecked) {
////            val favoritesTeams = Favorites(team.id, team.crestUrl, team.name, team.clubColors, team.venue, team.website)
////            viewModel.value().insertFavorites(favoritesTeams)
//            Log.d("xxxx" , "check $id")
//        } else {
//
//            viewModel.value().deleteFavorite(id)
//        }
//
//    }
//
//    private fun getFavoritesList(){
//        lateinit var favAdapter: FavoriteAdapter
//        val recyclerManeger = GridLayoutManager(activity, 2)
//        home_recyclerview.layoutManager = recyclerManeger
//        home_recyclerview.hasFixedSize()
//        favAdapter = FavoriteAdapter(this.context!!,
//            mutableListOf(),
//            { teamId: Int -> teamItemClicked(teamId) },
//            { teamID: Int, ischecked: Boolean -> deleteFavorites(teamID , ischecked)} )
//
//        home_recyclerview.adapter = favAdapter
//
//        viewModel.value().getLiveFavorites().observe(this , Observer {favData ->
//
//            Log.d("xxxx" , "size ${favData?.size}")
//
////            if (favData?.isEmpty()) {
//
//
//                favData?.let {
//                    favAdapter.favList.addAll(it)
//                    favAdapter.notifyDataSetChanged()
//                }
////                }
//        })
//    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_fav -> {

                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.home_frame, FavoritesFragment())?.addToBackStack(TeamInfoFragment.TAG)?.commit()

            }
        }

        return super.onOptionsItemSelected(item)

    }
}