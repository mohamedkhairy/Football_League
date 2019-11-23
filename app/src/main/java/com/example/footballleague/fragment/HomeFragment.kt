package com.example.footballleague.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Eval
import com.example.footballleague.R
import com.example.footballleague.database.FootballDatabase
import com.example.footballleague.database.dao.Dao
import com.example.footballleague.database.entity.CompetitionTeamsData
import com.example.footballleague.resource.Resource
import com.example.footballleague.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment: DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var dao: Dao


    private val viewModel: Eval<HomeViewModel> = Eval.later {
        ViewModelProviders.of(this, providerFactory).get(HomeViewModel::class.java)
    }

    private val footballDatabase: Eval.Later<FootballDatabase?> = Eval.later {
        activity?.application?.let { FootballDatabase.getInstance(it) }
    }

    private val results = MediatorLiveData<Resource<CompetitionTeamsData?>>()

    private val results2 = MediatorLiveData<String>()





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.home_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val dao = footballDatabase.value?.footballDao()
//       val dta =  dao.getSavedTeamsData()
val name = "khairy"

//        dta?.let {
//            results.addSource(it) { data ->
//                if (dta != null){
//                    Log.d("xxxx" ,"gooooood")
//
//                }
//                else{
//                    Log.d("xxxx" ,"nulllllll")
//
//                }
//            }
//        }
//

//        GlobalScope.launch(Dispatchers.Main) {
//            dao.getSavedTeamsData().let {
//                Log.d("xxxx" ,"0000")
//                results.addSource(it) { cacheObject ->
//
//                    if (cacheObject != null){
//                        Log.d("xxxx" ,"gooooood")
//
//                    }
//                    else{
//                        Log.d("xxxx" ,"nulllllll")
//
//                    }
//                }
//            }
//        }

//        results2.addSource(name) { data ->
//            if (data != null){
//                Log.d("xxxx" ,"gooooood")
//
//            }
//            else{
//                Log.d("xxxx" ,"nulllllll")
//
//            }
//        }


        observeHomeView()


        view.ok_button.setOnClickListener {
            viewModel.value().getFootballTeams()
        }
    }


    fun observeHomeView(){
        viewModel.value().getTeamsData().observe(this , Observer { teamData ->
            if(teamData != null)
            Log.d("xxxt" , teamData.count.toString())
        })

        viewModel.value().getLoading().observe(this , Observer { isloading ->
            Log.d("xxxt" , isloading.toString())
        })
    }


}