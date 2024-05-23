package com.stacrux.paceclock.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RotateDrawable
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.stacrux.paceclock.R
import com.stacrux.paceclock.ServiceProvider
import com.stacrux.paceclock.model.ClockFace

class MainViewDrawer(private val mainContext: AppCompatActivity) {

    fun setupMainView() {
        resizePanelsToScreenWidth()
        setClockFace()
        val clockHandles = mainContext.findViewById<ImageView>(R.id.clockHandles)
        RotationHelper.setupQuarterRotations(clockHandles, mainContext)
        setAndroidSystemBarsColor(mainContext)
    }

    fun setClockFace() {
        val clockFace = ServiceProvider.settingsService.getClockFace()
        val findViewById = mainContext.findViewById<ImageView>(R.id.clockFace)
        findViewById.setImageResource(
            when (clockFace) {
                ClockFace.WHITE -> R.drawable.white_clock_template
                ClockFace.BLACK -> R.drawable.black_clock_template
                else -> R.drawable.white_clock_template
            }
        )

    }

    private fun resizePanelsToScreenWidth() {
        val windowMetrics = mainContext.windowManager.currentWindowMetrics
        val screenWidth = windowMetrics.bounds.width()
        // Find the two main LinearLayouts
        val paceClockLayout = mainContext.findViewById<LinearLayout>(R.id.paceClockLayout)
        val settingsLayout = mainContext.findViewById<LinearLayout>(R.id.settingsLayout)

        // Set width of LinearLayouts to screen width
        val layoutParamsDailyView = paceClockLayout.layoutParams as FrameLayout.LayoutParams
        layoutParamsDailyView.width = screenWidth
        paceClockLayout.layoutParams = layoutParamsDailyView

        val layoutParamsCalendarView = settingsLayout.layoutParams as FrameLayout.LayoutParams
        layoutParamsCalendarView.width = screenWidth
        layoutParamsCalendarView.leftMargin = screenWidth
        settingsLayout.layoutParams = layoutParamsCalendarView


        val mainHorizontalView =
            mainContext.findViewById<HorizontalScrollView>(R.id.mainHorizontalScroll)
        mainHorizontalView.post {
            mainHorizontalView.smoothScrollTo(0, 0)
        }
    }


    /**
     * Color status and nav bar according to the App background color
     */
    private fun setAndroidSystemBarsColor(mainContext: AppCompatActivity) {
        val backgroundColor =
            (mainContext.findViewById<HorizontalScrollView>(R.id.mainHorizontalScroll).background as? ColorDrawable)?.color
        mainContext.window.navigationBarColor = backgroundColor ?: Color.BLACK
        mainContext.window.statusBarColor = backgroundColor ?: Color.BLACK
    }


}