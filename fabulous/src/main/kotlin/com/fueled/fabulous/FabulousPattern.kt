package com.fueled.fabulous

import android.view.View

/**
 * Interface that custom menu pattern must implement.
 * Created by julienFueled on 6/7/17.
 */
interface FabulousPattern : FabulousTransition {
    fun getFinalPosition(subMenu: View, fabX: Float, fabY: Float, position: Int, menuSize: Int): FabulousPosition
}

data class FabulousPosition(val x: Float, val y: Float)
