package com.stacrux.paceclock.service.impl

import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.model.ClockFace
import com.stacrux.paceclock.model.SoundSet
import com.stacrux.paceclock.service.SettingsService

/**
 * Implementation of SettingsService based upon the shared preferences
 */
class SettingsServiceSharedPreferencesImpl(private val mainContext: AppCompatActivity) :
    SettingsService {

    private val sharedPreferences = mainContext.getSharedPreferences("MyPreferences", MODE_PRIVATE)
    private val clockFaceSettingsIdentifier = "clockFace"
    private val soundSetIdentifier = "soundSet"
    private val setsCounterVisibilityIdentifier = "setsCounterVisibile"

    override fun getClockFace(): ClockFace {
        val clockFacePref =
            sharedPreferences.getString(clockFaceSettingsIdentifier, ClockFace.WHITE.name)
        if (ClockFace.WHITE.name == clockFacePref) {
            return ClockFace.WHITE
        }
        if (ClockFace.BLACK.name == clockFacePref) {
            return ClockFace.BLACK
        }
        // default in case of not found
        return ClockFace.WHITE
    }

    override fun changeClockFace(clockFace: ClockFace) {
        sharedPreferences.edit().putString(clockFaceSettingsIdentifier, clockFace.name).apply()
    }

    override fun getSoundSet(): SoundSet {
        val soundSet = sharedPreferences.getString(soundSetIdentifier, SoundSet.NONE.name)
        if (soundSet == SoundSet.EFFECTS.name) {
            return SoundSet.EFFECTS
        }
        if (soundSet == SoundSet.NOTES.name) {
            return SoundSet.NOTES
        }
        return SoundSet.NONE
    }

    override fun changeSoundSet(soundSet: SoundSet) {
        sharedPreferences.edit().putString(soundSetIdentifier, soundSet.name).apply()
    }

    override fun isSoundEnabled(): Boolean {
        val soundSet = getSoundSet()
        return soundSet != SoundSet.NONE
    }

    override fun isSetsCounterEnabled(): Boolean {
        return sharedPreferences.getBoolean(setsCounterVisibilityIdentifier, true)
    }

    override fun changeSetsCounterVisibility(isVisible: Boolean) {
        sharedPreferences.edit().putBoolean(setsCounterVisibilityIdentifier, isVisible)
            .apply()
    }
}