package com.sta.crux.paceclock

import androidx.appcompat.app.AppCompatActivity
import com.sta.crux.paceclock.service.SettingsService
import com.sta.crux.paceclock.service.impl.SettingsServiceSharedPreferencesImpl

object ServiceProvider {

    lateinit var settingsService: SettingsService

    fun initializeServices(
        mainContext: AppCompatActivity
    ) {
        this.settingsService = SettingsServiceSharedPreferencesImpl(mainContext)
    }

}