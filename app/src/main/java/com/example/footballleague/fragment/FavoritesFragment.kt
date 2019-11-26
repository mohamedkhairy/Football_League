package com.example.footballleague.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import arrow.core.Eval
import arrow.core.toOption
import com.example.footballleague.R
import com.example.footballleague.adapter.FavoriteAdapter
import com.example.footballleague.fragment.home.HomeViewModel
import com.example.footballleague.fragment.team.TeamInfoFragment
import com.example.footballleague.util.hideView
import com.example.footballleague.util.setAction
import com.example.footballleague.util.showView
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.favorites_layout.*
import javax.inject.Inject

class FavoritesFragment : DaggerFragment() {


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var errorText: View
    private val viewModel: Eval<HomeViewModel> = Eval.later {
        ViewModelProviders.of(this, providerFactory).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.favorites_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar.setAction("Favorites", true)
        initView(view)
        getFavoritesList()
        watchBackButton(view)


    }


    private fun initView(view: View) {
        errorText = view.findViewById(R.id.empty_fav)
        val recyclerManeger = GridLayoutManager(activity, 2)
        fav_recyclerview.layoutManager = recyclerManeger
        fav_recyclerview.hasFixedSize()
        favAdapter = FavoriteAdapter(this.context!!,
            mutableListOf(),
            { teamId: Int -> teamItemClicked(teamId) },
            { teamID: Int, ischecked: Boolean -> deleteFavorites(teamID, ischecked) })

        fav_recyclerview.adapter = favAdapter
    }

    private fun deleteFavorites(id: Int, ischecked: Boolean) {


        if (!ischecked) {
            viewModel.value().deleteFavorite(id)
        }

    }

    private fun getFavoritesList() {

        viewModel.value().getLiveFavorites().observe(this, Observer { favData ->
            favData.toOption().fold({
                errorText.showView()
            }, {
                if (it.isNullOrEmpty()) {
                    errorText.showView()
                    favAdapter.favList.clear()
                    favAdapter.notifyDataSetChanged()
                } else {
                    errorText.hideView()
                    favAdapter.favList.clear()
                    favAdapter.favList.addAll(it)
                    favAdapter.notifyDataSetChanged()
                }
            })

        })
    }


    private fun teamItemClicked(teamId: Int) {

        val teamFragment = TeamInfoFragment.newInstance(teamId, FavoritesFragment.TAG)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fav_frame, teamFragment)
            ?.addToBackStack(TeamInfoFragment.TAG)?.commit()

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
        (activity as AppCompatActivity).supportActionBar.setAction(
            getString(R.string.app_name),
            false
        )
        return false
    }


    companion object {

        const val TAG = "FavoriteFragment"

    }
}