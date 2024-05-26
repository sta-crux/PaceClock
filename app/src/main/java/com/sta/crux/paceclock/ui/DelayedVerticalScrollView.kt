package com.sta.crux.paceclock.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import kotlin.math.abs

/**
 * Bounce back to the original position if the user did not scroll enough,
 * to avoid accidental scrolling
 */
class DelayedVerticalScrollView : ScrollView {

    private var startX = 0f
    private var startY = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {

            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
            }

            MotionEvent.ACTION_MOVE -> {
                if (startX == 0F) {
                    startX = ev.x
                }
                if (startY == 0F) {
                    startY = ev.y
                }
                return super.onTouchEvent(ev)
            }

            MotionEvent.ACTION_UP -> {
                if (abs(ev.x - startX) > abs(ev.y - startY)) {
                    if (ev.y - startY > 0) {
                        scrollToPage(1)
                        startY = 0F
                    } else {
                        scrollToPage(0)
                        startY = 0F
                    }
                    startX = 0F
                    startY = 0F
                    return true
                }
                if (ev.y - startY > 0) {
                    scrollToPage(0)
                    startY = 0F
                } else {
                    scrollToPage(1)
                    startY = 0F
                }
                return true
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun scrollToPage(pageNumber: Int) {
        if (pageNumber == 0) {
            smoothScrollTo(0, 0)
            return
        }
        if (pageNumber == 1) {
            smoothScrollTo(0, height)
            return
        }
        smoothScrollTo(0, height * 3)
    }

}




