package com.sta.crux.paceclock.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sta.crux.paceclock.R
import com.sta.crux.paceclock.ServiceProvider
import com.sta.crux.paceclock.model.ChosenDisplayMode
import com.sta.crux.paceclock.model.ClockFace

class MainViewDrawer(private val mainContext: AppCompatActivity) {

    private var setsCounterInt: Int = 0

    fun setupMainView() {
        val chosenOrientation = ServiceProvider.settingsService.getChosenOrientation()
        if (chosenOrientation == ChosenDisplayMode.DEFAULT_PORTRAIT) {
            resizePanelsToScreenWidth()
        } else {
            resizePanelsToScreenHeight()
        }
        setClockFace()
        setsVisibility()
        val clockHandles = mainContext.findViewById<ImageView>(R.id.clockHandles)
        RotationHelper(mainContext).setupQuarterRotations(clockHandles, mainContext)
        setAndroidSystemBarsColor(mainContext)
    }

    fun setsVisibility() {
        val setsLayout = mainContext.findViewById<TextView>(R.id.setsCounter)
        setsLayout.visibility =
            if (ServiceProvider.settingsService.isSetsCounterEnabled()) View.VISIBLE else View.INVISIBLE
    }

    fun setClockFace() {
        val clockFace = ServiceProvider.settingsService.getClockFace()
        val paceClock = mainContext.findViewById<ImageView>(R.id.clockFace)
        paceClock.setImageResource(
            when (clockFace) {
                ClockFace.WHITE -> R.drawable.white_clock_template
                ClockFace.BLACK -> R.drawable.black_clock_template
                ClockFace.MODERN -> R.drawable.modern_clock_template
            }
        )
        paceClock.setOnClickListener {
            setsCounterInt++
            val setsCounter = mainContext.findViewById<TextView>(R.id.setsCounter)
            setsCounter.text = "SETS: $setsCounterInt"
        }
        paceClock.setOnLongClickListener {
            setsCounterInt = 0
            val setsCounter = mainContext.findViewById<TextView>(R.id.setsCounter)
            setsCounter.text = "SETS: $setsCounterInt"
            true
        }
    }

    private fun resizePanelsToScreenHeight() {
        val windowMetrics = mainContext.windowManager.currentWindowMetrics
        val screenHeight = windowMetrics.bounds.height()
        // Find the two main LinearLayouts
        val paceClockLayout = mainContext.findViewById<LinearLayout>(R.id.paceClockLayout)
        val settingsLayout = mainContext.findViewById<LinearLayout>(R.id.settingsLayout)

        // Set width of LinearLayouts to screen width
        val layoutParamsPaceClockView = paceClockLayout.layoutParams as FrameLayout.LayoutParams
        layoutParamsPaceClockView.height = screenHeight
        paceClockLayout.layoutParams = layoutParamsPaceClockView

        val layoutParamsSettingsView = settingsLayout.layoutParams as FrameLayout.LayoutParams
        layoutParamsSettingsView.height = screenHeight
        layoutParamsSettingsView.topMargin = screenHeight
        settingsLayout.layoutParams = layoutParamsSettingsView


        val mainView =
            mainContext.findViewById<ScrollView>(R.id.mainHorizontalScroll)
        mainView.post {
            mainView.smoothScrollTo(0, 0)
        }
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
        val chosenOrientation = ServiceProvider.settingsService.getChosenOrientation()
        val backgroundColor = if (chosenOrientation == ChosenDisplayMode.DEFAULT_PORTRAIT) {
            (mainContext.findViewById<HorizontalScrollView>(R.id.mainHorizontalScroll).background as? ColorDrawable)?.color
        } else {
            (mainContext.findViewById<ScrollView>(R.id.mainHorizontalScroll).background as? ColorDrawable)?.color

        }
        mainContext.window.navigationBarColor = backgroundColor ?: Color.BLACK
        val topColor =
            (mainContext.findViewById<Toolbar>(R.id.topToolBar)?.background as? ColorDrawable)?.color
        mainContext.window.statusBarColor = topColor ?: mainContext.window.navigationBarColor
    }


}