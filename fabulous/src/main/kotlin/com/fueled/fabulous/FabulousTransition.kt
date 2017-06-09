package com.fueled.fabulous

import android.animation.AnimatorSet
import android.view.View

/**
 * Created by julienFueled on 6/7/17.
 */
open class FabulousTransition(val openingAnimation: (element: View, destX: Float, destY: Float) -> AnimatorSet, val closingAnimation: (element: View, destX: Float, destY: Float) -> AnimatorSet)