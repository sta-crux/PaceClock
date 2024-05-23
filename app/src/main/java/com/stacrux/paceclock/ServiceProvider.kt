package com.stacrux.paceclock

import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.service.SettingsService
import com.stacrux.paceclock.service.impl.SettingsServiceSharedPreferencesImpl

object ServiceProvider {

    lateinit var settingsService: SettingsService

    fun initializeServices(
        mainContext: AppCompatActivity
    ) {
        this.settingsService = SettingsServiceSharedPreferencesImpl(mainContext)
    }

}