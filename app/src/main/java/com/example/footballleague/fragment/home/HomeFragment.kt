package com.example.footballleague.fragment.home

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import arrow.core.Eval
import com.example.footballleague.R
import com.example.footballleague.adapter.HomeViewAdapter
import com.example.footballleague.database.entity.Favorites
import com.example.footballleague.database.entity.Team
import com.example.footballleague.fragment.FavoritesFragment
import com.example.footballleague.fragment.team.TeamInfoFragment
import com.example.footballleague.util.hideView
import com.example.footballleague.util.showView
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var adapter: HomeViewAdapter
    lateinit var loadingView: View
    lateinit var errorView: View
    private lateinit var homeFrame: FrameLayout


    private val viewModel: Eval<HomeViewModel> = Eval.later {
        ViewModelProviders.of(this, providerFactory).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        refresh()
        observeHomeView()
        prepareView(view)
        viewModel.value().getFootballTeams()
        homeFrame = view.findViewById<FrameLayout>(R.id.home_frame)

    }


    fun observeHomeView() {
        viewModel.value().getTeamsData().observe(this, Observer { teamData ->
            if (teamData != null)
                if (!teamData.isEmpty()) {
                    adapter.teamList.clear()
                    adapter.teamList.addAll(teamData)
                    adapter.notifyDataSetChanged()
                }
        })

        viewModel.value().getLoading().observe(this, Observer { isloading ->
            if (isloading)
                loadingView.showView()
            else
                loadingView.hideView()
        })

        viewModel.value().getConnection().observe(this, Observer { isConnected ->
            if (isConnected)
                errorView.hideView()
            else
                errorView.showView()
        })

    }


    private fun prepareView(view: View) {

        loadingView = view.findViewById(R.id.homeloading)
        errorView = view.findViewById(R.id.empty)

        val recyclerManeger = GridLayoutManager(activity, 2)
        home_recyclerview.layoutManager = recyclerManeger
        home_recyclerview.startLayoutAnimation()
        adapter = HomeViewAdapter(this.context!!,
            mutableListOf(),
            { teamId: Int -> teamItemClicked(teamId) },
            { team: Team, ischecked: Boolean -> addTofavorites(team, ischecked) })

        home_recyclerview.adapter = adapter
    }


    private fun teamItemClicked(teamId: Int) {

        val teamFragment = TeamInfoFragment.newInstance(teamId, HomeFragment.TAG)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.home_frame, teamFragment)
            ?.addToBackStack(TeamInfoFragment.TAG)?.commit()

    }

    private fun addTofavorites(team: Team, ischecked: Boolean) {


        if (ischecked) {
            val favoritesTeams = Favorites(
                team.id,
                team.crestUrl,
                team.name,
                team.clubColors,
                team.venue,
                team.website
            )
            viewModel.value().insertFavorites(favoritesTeams)
        } else {
            viewModel.value().deleteFavorite(team.id)
        }

    }

    private fun refresh() {
        swipe_refresh_layout.setOnRefreshListener {

            viewModel.value().getFootballTeams()

            swipe_refresh_layout.setRefreshing(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> {

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.home_frame, FavoritesFragment())
                    ?.addToBackStack(TeamInfoFragment.TAG)?.commit()

            }
        }

        return super.onOptionsItemSelected(item)

    }

    companion object {

        const val TAG = "HomeFragment"

    }
}