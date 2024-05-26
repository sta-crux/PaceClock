package com.stacrux.paceclock

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.stacrux.paceclock.model.ChosenDisplayMode
import com.stacrux.paceclock.ui.MainViewDrawer
import com.stacrux.paceclock.ui.SettingsViewDrawer

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceProvider.initializeServices(this)
        val chosenOrientation = ServiceProvider.settingsService.getChosenOrientation()
        if (chosenOrientation == ChosenDisplayMode.DEFAULT_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            setContentView(R.layout.activity_main)
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            setContentView(R.layout.activity_main_immersive)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = ViewCompat.getWindowInsetsController(window.decorView)
            controller?.let {
                it.hide(WindowInsetsCompat.Type.systemBars())
                it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        MainViewDrawer(this).setupMainView()
        SettingsViewDrawer(this).drawSettingsPage()
    }
}