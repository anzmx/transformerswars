package com.agzz.transformerswars.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.agzz.transformerswars.R
import com.agzz.transformerswars.data.local.prefs.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHost!!.navController

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.nav_graph)

        if (sharedPreferencesManager.authToken.isEmpty()) {
            graph.startDestination = R.id.welcomeFragment
        } else {
            graph.startDestination = R.id.ListFragment
        }

        navController.graph = graph

    }
}