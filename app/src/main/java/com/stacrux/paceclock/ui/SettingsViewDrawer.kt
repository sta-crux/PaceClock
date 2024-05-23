package com.stacrux.paceclock.ui

import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.R
import com.stacrux.paceclock.ServiceProvider
import com.stacrux.paceclock.model.ClockFace
import com.stacrux.paceclock.model.SoundSet

class SettingsViewDrawer(private val mainContext: AppCompatActivity) {

    fun drawSettingsPage() {
        setClockFaceSettingToggles()
        setSoundSetToggles()
        setSetsCounterVisibilityToggles()
    }

    private fun setSetsCounterVisibilityToggles() {
        val setsCounterVisible =
            mainContext.findViewById<FrameLayout>(R.id.setsCounterDisplayOnToggle)
        val setsCounterInvisible =
            mainContext.findViewById<FrameLayout>(R.id.setsCounterDisplayOffToggle)

        setsCounterVisible.alpha =
            if (ServiceProvider.settingsService.isSetsCounterEnabled()) 1f else .2f
        setsCounterInvisible.alpha =
            if (ServiceProvider.settingsService.isSetsCounterEnabled()) .2f else 1f

        setsCounterVisible.setOnClickListener {
            ServiceProvider.settingsService.changeSetsCounterVisibility(true)
            setsCounterVisible.alpha =
                if (ServiceProvider.settingsService.isSetsCounterEnabled()) 1f else .2f
            setsCounterInvisible.alpha =
                if (ServiceProvider.settingsService.isSetsCounterEnabled()) .2f else 1f
            MainViewDrawer(mainContext).setsVisibility()
            Toast.makeText(mainContext, "The sets counter is now visible.", Toast.LENGTH_SHORT)
                .show()
        }
        setsCounterInvisible.setOnClickListener {
            ServiceProvider.settingsService.changeSetsCounterVisibility(false)
            setsCounterVisible.alpha =
                if (ServiceProvider.settingsService.isSetsCounterEnabled()) 1f else .2f
            setsCounterInvisible.alpha =
                if (ServiceProvider.settingsService.isSetsCounterEnabled()) .2f else 1f
            MainViewDrawer(mainContext).setsVisibility()
            Toast.makeText(mainContext, "The sets counter is now hidden.", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun setSoundSetToggles() {
        val noSoundToggle = mainContext.findViewById<FrameLayout>(R.id.noSoundToggle)
        val pianoNotesToggle = mainContext.findViewById<FrameLayout>(R.id.pianoNoteToggle)
        val effectsToggle = mainContext.findViewById<FrameLayout>(R.id.effectsSoundToggle)

        val soundSet = ServiceProvider.settingsService.getSoundSet()
        when (soundSet) {
            SoundSet.NOTES -> {
                noSoundToggle.alpha = .2f
                pianoNotesToggle.alpha = 1f
                effectsToggle.alpha = .2f
            }

            SoundSet.EFFECTS -> {
                noSoundToggle.alpha = .2f
                pianoNotesToggle.alpha = .2f
                effectsToggle.alpha = .1f
            }

            SoundSet.NONE -> {
                noSoundToggle.alpha = 1f
                pianoNotesToggle.alpha = .2f
                effectsToggle.alpha = .2f
            }
        }

        noSoundToggle.setOnClickListener {
            noSoundToggle.alpha = 1f
            pianoNotesToggle.alpha = .2f
            effectsToggle.alpha = .2f
            ServiceProvider.settingsService.changeSoundSet(SoundSet.NONE)
            Toast.makeText(mainContext, "No sound will be played.", Toast.LENGTH_SHORT).show()
        }
        pianoNotesToggle.setOnClickListener {
            noSoundToggle.alpha = .2f
            pianoNotesToggle.alpha = 1f
            effectsToggle.alpha = .2f
            ServiceProvider.settingsService.changeSoundSet(SoundSet.NOTES)
            Toast.makeText(
                mainContext,
                "A piano note will be played each 15 seconds.",
                Toast.LENGTH_SHORT
            ).show()
        }
        effectsToggle.setOnClickListener {
            noSoundToggle.alpha = .2f
            pianoNotesToggle.alpha = .2f
            effectsToggle.alpha = 1f
            ServiceProvider.settingsService.changeSoundSet(SoundSet.EFFECTS)
            Toast.makeText(
                mainContext,
                "A sound will be played each 15 seconds.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setClockFaceSettingToggles() {
        val whiteToggle = mainContext.findViewById<FrameLayout>(R.id.settingsClockFaceWhiteToggle)
        val blackToggle = mainContext.findViewById<FrameLayout>(R.id.settingsClockFaceBlackToggle)
        val clockFace = ServiceProvider.settingsService.getClockFace()
        when (clockFace) {
            ClockFace.WHITE -> {
                whiteToggle.alpha = 1f
                blackToggle.alpha = .2f
            }

            ClockFace.BLACK -> {
                whiteToggle.alpha = 1f
                blackToggle.alpha = .2f
            }
        }
        whiteToggle.setOnClickListener {
            ServiceProvider.settingsService.changeClockFace(ClockFace.WHITE)
            whiteToggle.alpha = 1f
            blackToggle.alpha = .2f
            MainViewDrawer(mainContext).setClockFace()
        }
        blackToggle.setOnClickListener {
            ServiceProvider.settingsService.changeClockFace(ClockFace.BLACK)
            whiteToggle.alpha = .2f
            blackToggle.alpha = 1f
            MainViewDrawer(mainContext).setClockFace()
        }
    }


}