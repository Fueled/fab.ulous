package com.fueled.fabulous

import android.animation.AnimatorSet
import android.view.View

/**
 * Interface that custom menu pattern must implement.
 * Created by julienFueled on 6/7/17.
 */
open class FabulousPattern(val finalPosition: (subMenu: View, fabX: Float, fabY: Float, position: Int, menuSize: Int) -> FabulousPosition, openingAnimation: (element: View, destX: Float, destY: Float) -> AnimatorSet, closingAnimation: (element: View, destX: Float, destY: Float) -> AnimatorSet) : FabulousTransition(openingAnimation, closingAnimation)

data class FabulousPosition(val x: Float, val y: Float)
