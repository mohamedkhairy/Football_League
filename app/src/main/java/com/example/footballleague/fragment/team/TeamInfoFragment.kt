package com.example.footballleague.fragment.team

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.Eval
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.footballleague.R
import com.example.footballleague.adapter.CompetitionAdapter
import com.example.footballleague.adapter.PlayerAdapter
import com.example.footballleague.database.entity.ActiveCompetition
import com.example.footballleague.database.entity.Squad
import com.example.footballleague.database.entity.TeamInfoModel
import com.example.footballleague.fragment.FavoritesFragment
import com.example.footballleague.fragment.home.HomeFragment
import com.example.footballleague.util.hideView
import com.example.footballleague.util.isNotNull
import com.example.footballleague.util.setAction
import com.example.footballleague.util.showView
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.teaminfo_layout.*
import javax.inject.Inject


class TeamInfoFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private var teamId: Int? = null
    private var backTag: String? = null
    lateinit var loadingView: View
    lateinit var errorView: View
    lateinit var viewContainer: View


    private val viewModel: Eval<TeamInfoViewModel> = Eval.later {
        ViewModelProviders.of(this, providerFactory).get(TeamInfoViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        teamId = this.arguments?.getInt("Team_ID")
        backTag = this.arguments?.getString("Back_Tag")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.teaminfo_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar.setAction("Team Details", true)
        viewModel.value().getTeamInfo(teamId!!)
        observeTeamView()
        initView(view)
        watchBackButton(view)

    }

    private fun initView(view: View) {
        loadingView = view.findViewById(R.id.teamloading)
        errorView = view.findViewById(R.id.empty_details)
        viewContainer = view.findViewById(R.id.data_container)

    }

    fun observeTeamView() {

        viewModel.value().getLiveTeamInfo().observe(this, Observer { teamInfo ->

            if (teamInfo.isNotNull()) {
                setView(teamInfo)
            }
            initSquadview(teamInfo.squad)
            initCompetitionview(teamInfo.activeCompetitions)

        })

        viewModel.value().getLoading().observe(this, Observer { isLoading ->
            if (isLoading) {
                loadingView.showView()
                viewContainer.hideView()
            } else {
                loadingView.hideView()
                viewContainer.showView()
            }
        })

        viewModel.value().getError().observe(this, Observer { isError ->
            if (isError) {
                errorView.showView()
                viewContainer.hideView()
            } else {
                errorView.hideView()
                viewContainer.showView()
            }
        })

    }

    private fun initSquadview(dataList: List<Squad>) {
        val recyclerManeger = LinearLayoutManager(activity)
        player_recyclerview.layoutManager = recyclerManeger
        player_recyclerview.isNestedScrollingEnabled = false
        player_recyclerview.startLayoutAnimation()
        var adapter = PlayerAdapter(dataList)
        player_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initCompetitionview(dataList: List<ActiveCompetition>) {
        val recyclerManeger = LinearLayoutManager(activity)
        Competitions_recyclerview.layoutManager = recyclerManeger
        player_recyclerview.isNestedScrollingEnabled = false
        var adapter = CompetitionAdapter(dataList)
        Competitions_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setView(teamInfo: TeamInfoModel) {

        setSvgTeamImage(teamInfo.crestUrl)
        team_title.text = teamInfo.name
        address.text = teamInfo.address
        phone.text = teamInfo.phone
        mail.text = teamInfo.email
        founded.text = teamInfo.founded.toString()

        com_linear.setOnClickListener {
            expandable_layout.toggle()
            arrow.animate().rotationBy(180F).setDuration(500L).start()
        }
    }


    private fun setSvgTeamImage(teamImage: String?) {
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

        setBar()
        when (item.getItemId()) {
            android.R.id.home -> {
                fragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun watchBackButton(view: View) {
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            return@setOnKeyListener setBar()
        }
    }


    private fun setBar(): Boolean {

        if (backTag.equals(HomeFragment.TAG)) {
            (activity as AppCompatActivity).supportActionBar.setAction(
                getString(R.string.app_name),
                false
            )
        } else if (backTag.equals(FavoritesFragment.TAG)) {
            (activity as AppCompatActivity).supportActionBar.setAction("Favorites", true)
        }
        return false

    }

    companion object {

        const val TAG = "TeamInfoFragment"

        fun newInstance(id: Int, backTag: String) = TeamInfoFragment().apply {
            arguments = Bundle().apply {
                putInt("Team_ID", id)
                putString("Back_Tag", backTag)
            }
        }
    }

}