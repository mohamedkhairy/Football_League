package com.example.footballleague.fragment.team

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.Eval
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.footballleague.R
import com.example.footballleague.adapter.HomeViewAdapter
import com.example.footballleague.adapter.PlayerAdapter
import com.example.footballleague.database.entity.ActiveCompetition
import com.example.footballleague.database.entity.Squad
import com.example.footballleague.database.entity.Team
import com.example.footballleague.database.entity.TeamInfoModel
import com.example.footballleague.util.isNotNull
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_taem_item.view.*
import kotlinx.android.synthetic.main.teaminfo_layout.*
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

        val view = inflater.inflate(R.layout.teaminfo_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAcrionBar()
        viewModel.value().getTeamInfo(teamId!!)
        observeTeamView()

    }

    private fun setAcrionBar(){

        setHasOptionsMenu(true)
        activity?.let {
            val  appBar = (it as AppCompatActivity).supportActionBar
            appBar?.title = "Team Details"
            appBar?.setDisplayHomeAsUpEnabled(true)
            appBar?.setHomeButtonEnabled(true)
        }
    }

    fun observeTeamView(){

        viewModel.value().getLiveTeamInfo().observe(this , Observer { teamInfo ->

            if (teamInfo.isNotNull()){
                setView(teamInfo)
            }
            initSquadview(teamInfo.squad)
            initCompetitionview(teamInfo.activeCompetitions)

        })

    }

    private fun  initSquadview(dataList: List<Squad>){
        val recyclerManeger = LinearLayoutManager(activity)
        player_recyclerview.layoutManager = recyclerManeger
        player_recyclerview.hasFixedSize()
        var adapter = PlayerAdapter(dataList)
        player_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun  initCompetitionview(dataList: List<ActiveCompetition>){
        val recyclerManeger = LinearLayoutManager(activity)
        Competitions_recyclerview.layoutManager = recyclerManeger
        Competitions_recyclerview.hasFixedSize()
        var adapter = PlayerAdapter(dataList)
        Competitions_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setView(teamInfo: TeamInfoModel){

        setSvgTeamImage(teamInfo.crestUrl)
        team_title.text = teamInfo.name
        address.text = teamInfo.address
        phone.text = teamInfo.phone
        mail.text = teamInfo.email
        founded.text = teamInfo.founded.toString()

        expandable_layout.setOnClickListener {
            expandable_layout.toggle()
//            view.google_arrow.animate().rotationBy(180F).setDuration(500L).start()
        }
    }


    private fun setSvgTeamImage(teamImage: String?){
        val requestBuilder: RequestBuilder<PictureDrawable> = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(teamImage)
            .error(R.drawable.soccer_ball)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().centerCrop())
            .into(team_photo)
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

        const val TAG = "TeamInfoFragment"

        fun newInstance(id: Int) = TeamInfoFragment().apply {
            arguments = Bundle().apply {
                putInt("Team_ID",id)
            }
        }
    }

}