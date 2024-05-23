package com.stacrux.paceclock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.service.impl.SettingsServiceSharedPreferencesImpl
import com.stacrux.paceclock.ui.MainViewDrawer
import com.stacrux.paceclock.ui.SettingsViewDrawer

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ServiceProvider.initializeServices(this)
        MainViewDrawer(this).setupMainView()
        SettingsViewDrawer(this).drawSettingsPage()
    }
}