package com.example.footballleague.fragment.team

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import arrow.core.Eval
import com.example.footballleague.R
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TeamInfoFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private var teamId: Int? = null

    private val viewModel: Eval<TeamInfoViewModel> = Eval.later {
        ViewModelProviders.of(this, providerFactory).get(TeamInfoViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        teamId = this.arguments?.getInt("Team_ID")

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.team_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.value().getTeamInfo(teamId!!)
        observeTeamView()
    }


    fun observeTeamView(){

        viewModel.value().getLiveTeamInfo().observe(this , Observer { teamInfo ->

            Log.d("xxxxinfo" , teamInfo.name)
        })

    }

    companion object {

        const val TAG = "TeamInfoFragment"

        fun newInstance(id: Int) = TeamInfoFragment().apply {
            arguments = Bundle().apply {
                putInt("Team_ID",id)
            }
        }
    }

}