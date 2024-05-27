package com.sta.crux.paceclock.ui

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.sta.crux.paceclock.R
import com.sta.crux.paceclock.ServiceProvider
import com.sta.crux.paceclock.model.SoundSet

class RotationHelper(private val mainContext: AppCompatActivity) {

    private val periodDuration = 15000


    val notes = listOf(
        MediaPlayer.create(mainContext, R.raw.c3),
        MediaPlayer.create(mainContext, R.raw.c4),
        MediaPlayer.create(mainContext, R.raw.c5),
        MediaPlayer.create(mainContext, R.raw.c6)
    )

    val effects = listOf(
        MediaPlayer.create(mainContext, R.raw.glass),
        MediaPlayer.create(mainContext, R.raw.ting),
        MediaPlayer.create(mainContext, R.raw.tone),
        MediaPlayer.create(mainContext, R.raw.star)
    )

    fun setupQuarterRotations(viewToRotate: View, ctx: Context?) {


        val quarterRotations: MutableList<Animation> = ArrayList()
        for (i in 0..3) {
            val fromDegrees = 90f * i
            val toDegrees = 90f * (i + 1)
            val anim = RotateAnimation(
                fromDegrees,
                toDegrees,
                Animation.RELATIVE_TO_SELF,
                .5f,
                Animation.RELATIVE_TO_SELF,
                .5f
            )
            anim.interpolator = LinearInterpolator()
            anim.setDuration(periodDuration.toLong())
            quarterRotations.add(anim)
        }
        for (i in 0..3) {
            quarterRotations[i].setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    if (ServiceProvider.settingsService.isSoundEnabled()) {
                        val soundSet = ServiceProvider.settingsService.getSoundSet()
                        val soundArray = if (soundSet == SoundSet.NOTES) notes else effects
                        soundArray[i].start()
                    }
                    startNextAnimation(viewToRotate, quarterRotations, i)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
        viewToRotate.startAnimation(quarterRotations[0])
    }

    private fun startNextAnimation(
        viewToRotate: View,
        quarterRotations: List<Animation>,
        index: Int
    ) {
        if (index + 1 == quarterRotations.size) {
            viewToRotate.startAnimation(quarterRotations[0])
        } else {
            viewToRotate.startAnimation(quarterRotations[index + 1])
        }
    }

}