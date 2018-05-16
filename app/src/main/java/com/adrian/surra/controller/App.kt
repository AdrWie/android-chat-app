package com.adrian.surra.controller

import android.app.Application
import com.adrian.surra.utilities.SharedPrefs

// Set and instantiated before any other class
class App: Application() {

    // Singleton within a class, only one instance of user preferences
    companion object {
        lateinit var sharedPreferences: SharedPrefs
    }
    // Initiate SharedPreferences to the "global" context at startup
    override fun onCreate() {
        sharedPreferences = SharedPrefs(applicationContext)
        super.onCreate()
    }
}