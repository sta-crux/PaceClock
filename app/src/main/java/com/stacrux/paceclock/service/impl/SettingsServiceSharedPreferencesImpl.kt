package com.stacrux.paceclock.service.impl

import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.model.ChosenDisplayMode
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
    private val chosenOrientationIdentifier = "chosenOrientation"

    override fun getClockFace(): ClockFace {
        val clockFacePref =
            sharedPreferences.getString(clockFaceSettingsIdentifier, ClockFace.WHITE.name)
        if (ClockFace.WHITE.name == clockFacePref) {
            return ClockFace.WHITE
        }
        if (ClockFace.BLACK.name == clockFacePref) {
            return ClockFace.BLACK
        }
        if (ClockFace.MODERN.name == clockFacePref) {
            return ClockFace.MODERN
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

    override fun getChosenOrientation(): ChosenDisplayMode {
        val orientation =
            sharedPreferences.getString(chosenOrientationIdentifier, ChosenDisplayMode.DEFAULT_PORTRAIT.name)
        if (orientation == ChosenDisplayMode.IMMERSIVE.name) {
            return ChosenDisplayMode.IMMERSIVE
        }
        return ChosenDisplayMode.DEFAULT_PORTRAIT
    }

    override fun changeChosenOrientation(chosenDisplayMode: ChosenDisplayMode) {
        sharedPreferences.edit()
            .putString(chosenOrientationIdentifier, chosenDisplayMode.name)
            .apply()
    }
}