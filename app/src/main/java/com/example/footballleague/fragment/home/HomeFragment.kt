package com.example.footballleague.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import arrow.core.Eval
import com.example.footballleague.R
import com.example.footballleague.adapter.HomeViewAdapter
import com.example.footballleague.fragment.team.TeamInfoFragment
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject


class HomeFragment: DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


    private lateinit var adapter: HomeViewAdapter
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

        observeHomeView()
        prepareView()
        viewModel.value().getFootballTeams()
        homeFrame = view.findViewById<FrameLayout>(R.id.home_frame)

    }


    fun observeHomeView(){
        viewModel.value().getTeamsData().observe(this , Observer { teamData ->
            if(teamData != null)
            if (!teamData.teams.isEmpty()) {
                adapter.teamList.addAll(teamData.teams)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.value().getLoading().observe(this , Observer { isloading ->
        })
    }


    private fun prepareView() {

        val recyclerManeger = GridLayoutManager(activity, 2)
        home_recyclerview.layoutManager = recyclerManeger
        home_recyclerview.hasFixedSize()
        adapter = HomeViewAdapter(this.context!!,
            mutableListOf(),
            { teamId: Int -> teamItemClicked(teamId) },
            {r:Int -> addTofavorites(r)} )

        home_recyclerview.adapter = adapter
    }


    private fun teamItemClicked(teamId: Int) {

        val teamFragment = TeamInfoFragment.newInstance(teamId)
        childFragmentManager.beginTransaction().add(R.id.home_frame, teamFragment , TeamInfoFragment.TAG)
            .addToBackStack(TeamInfoFragment.TAG).commitAllowingStateLoss()
    }

    private fun addTofavorites(teamId: Int) {


    }


}