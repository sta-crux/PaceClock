package com.sta.crux.paceclock.service

import com.sta.crux.paceclock.model.ChosenDisplayMode
import com.sta.crux.paceclock.model.ClockFace
import com.sta.crux.paceclock.model.SoundSet

interface SettingsService {

    fun getClockFace(): ClockFace

    fun changeClockFace(clockFace: ClockFace)

    fun getSoundSet(): SoundSet

    fun changeSoundSet(soundSet: SoundSet)

    fun isSoundEnabled(): Boolean

    fun isSetsCounterEnabled(): Boolean

    fun changeSetsCounterVisibility(isVisible: Boolean)

    fun getChosenOrientation(): ChosenDisplayMode

    fun changeChosenOrientation(chosenDisplayMode: ChosenDisplayMode)

}