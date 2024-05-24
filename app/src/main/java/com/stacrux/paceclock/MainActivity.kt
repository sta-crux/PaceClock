package com.stacrux.paceclock

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.model.ChosenOrientation
import com.stacrux.paceclock.service.impl.SettingsServiceSharedPreferencesImpl
import com.stacrux.paceclock.ui.MainViewDrawer
import com.stacrux.paceclock.ui.SettingsViewDrawer

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceProvider.initializeServices(this)
        val chosenOrientation = ServiceProvider.settingsService.getChosenOrientation()
        if (chosenOrientation == ChosenOrientation.PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            setContentView(R.layout.activity_main)
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            setContentView(R.layout.activity_main_landscape)
        }
        MainViewDrawer(this).setupMainView()
        SettingsViewDrawer(this).drawSettingsPage()
    }
}