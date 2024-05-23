package com.stacrux.paceclock.service

import com.stacrux.paceclock.model.ClockFace
import com.stacrux.paceclock.model.SoundSet

interface SettingsService {

    fun getClockFace(): ClockFace

    fun changeClockFace(clockFace: ClockFace)

    fun getSoundSet(): SoundSet

    fun changeSoundSet(soundSet: SoundSet)

    fun isSoundEnabled(): Boolean

    fun isSetsCounterEnabled(): Boolean

    fun changeSetsCounterVisibility(isVisible: Boolean)

}