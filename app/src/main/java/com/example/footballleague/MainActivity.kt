package com.example.footballleague

import android.os.Bundle
import com.example.footballleague.fragment.home.HomeFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment()
    }

    private fun startFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, HomeFragment())
            .commit()
    }
}
