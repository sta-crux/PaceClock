package com.stacrux.paceclock.ui

import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.stacrux.paceclock.R
import com.stacrux.paceclock.ServiceProvider
import com.stacrux.paceclock.model.ChosenDisplayMode
import com.stacrux.paceclock.model.ClockFace
import com.stacrux.paceclock.model.SoundSet

class SettingsViewDrawer(private val mainContext: AppCompatActivity) {

    fun drawSettingsPage() {
        setClockFaceSettingToggles()
        setSoundSetToggles()
        setSetsCounterVisibilityToggles()
        setOrientationToggles()
        val exitAppButton = mainContext.findViewById<FloatingActionButton>(R.id.exitAppButton)
        exitAppButton.setOnClickListener {
            mainContext.finish()
        }
    }

    private fun setOrientationToggles() {
        val portraitToggle = mainContext.findViewById<FrameLayout>(R.id.portraitOriToggle)
        val landscapeToggle = mainContext.findViewById<FrameLayout>(R.id.landscapeOriToggle)
        val chosenOrientation = ServiceProvider.settingsService.getChosenOrientation()
        if (chosenOrientation == ChosenDisplayMode.DEFAULT_PORTRAIT) {
            portraitToggle.alpha = 1f
            landscapeToggle.alpha = .2f
        } else {
            portraitToggle.alpha = .2f
            landscapeToggle.alpha = 1f
        }
        portraitToggle.setOnClickListener {
            ServiceProvider.settingsService.changeChosenOrientation(ChosenDisplayMode.DEFAULT_PORTRAIT)
            portraitToggle.alpha = 1f
            landscapeToggle.alpha = .2f
            Toast.makeText(
                mainContext,
                "The default view will be applied at the next app restart.",
                Toast.LENGTH_SHORT
            ).show()
        }
        landscapeToggle.setOnClickListener {
            ServiceProvider.settingsService.changeChosenOrientation(ChosenDisplayMode.IMMERSIVE)
            portraitToggle.alpha = .2f
            landscapeToggle.alpha = 1f
            val settingsLayout = mainContext.findViewById<LinearLayout>(R.id.settingsLayout)
            Toast.makeText(mainContext, "Immersive mode will be applied at the next app restart.", Toast.LENGTH_SHORT)
                .show()
            Snackbar.make(settingsLayout, "INFO: Only the PACE CLOCK will be displayed.", Snackbar.LENGTH_LONG).show()
        }
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
        val modernToggle = mainContext.findViewById<FrameLayout>(R.id.settingsClockFaceModernToggle)
        val clockFace = ServiceProvider.settingsService.getClockFace()
        highlightClockFace(clockFace)

        whiteToggle.setOnClickListener {
            ServiceProvider.settingsService.changeClockFace(ClockFace.WHITE)
            highlightClockFace(ClockFace.WHITE)
            MainViewDrawer(mainContext).setClockFace()
        }
        blackToggle.setOnClickListener {
            ServiceProvider.settingsService.changeClockFace(ClockFace.BLACK)
            highlightClockFace(ClockFace.BLACK)
            MainViewDrawer(mainContext).setClockFace()
        }
        modernToggle.setOnClickListener {
            ServiceProvider.settingsService.changeClockFace(ClockFace.MODERN)
            highlightClockFace(ClockFace.MODERN)
            MainViewDrawer(mainContext).setClockFace()
        }
    }

    private fun highlightClockFace(clockFace: ClockFace) {
        val whiteToggle = mainContext.findViewById<FrameLayout>(R.id.settingsClockFaceWhiteToggle)
        val blackToggle = mainContext.findViewById<FrameLayout>(R.id.settingsClockFaceBlackToggle)
        val modernToggle = mainContext.findViewById<FrameLayout>(R.id.settingsClockFaceModernToggle)
        when (clockFace) {
            ClockFace.WHITE -> {
                whiteToggle.alpha = 1f
                blackToggle.alpha = .2f
                modernToggle.alpha = .2f
            }

            ClockFace.BLACK -> {
                whiteToggle.alpha = .2f
                blackToggle.alpha = 1f
                modernToggle.alpha = .2f
            }

            ClockFace.MODERN -> {
                whiteToggle.alpha = .2f
                blackToggle.alpha = .2f
                modernToggle.alpha = 1f
            }
        }
    }
}