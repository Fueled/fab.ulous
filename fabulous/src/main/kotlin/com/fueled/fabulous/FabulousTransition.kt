package com.fueled.fabulous

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

/**
 * Created by julienFueled on 6/7/17.
 */
interface FabulousTransition {
    fun getOpeningAnimation(element: View, destX: Float, destY: Float): AnimatorSet {
        val anim = AnimatorSet()
        val rotateMainFAB = ObjectAnimator.ofFloat<View>(element, View.ROTATION, 0f)
        rotateMainFAB.duration = MAIN_FAB_ANIMATION_DURATION.toLong()
        anim.play(rotateMainFAB)
        return anim
    }

    fun getClosingAnimation(element: View, destX: Float, destY: Float): AnimatorSet {
        val anim = AnimatorSet()
        val rotateMainFAB = ObjectAnimator.ofFloat<View>(element, View.ROTATION, 0f)
        rotateMainFAB.duration = MAIN_FAB_ANIMATION_DURATION.toLong()
        anim.play(rotateMainFAB)
        return anim
    }
}