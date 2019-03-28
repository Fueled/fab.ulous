package com.fueled.fabulous

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.app.Activity
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.internal.NavigationMenu
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup


class TestActivity : FragmentActivity() {
    lateinit var fab: FloatingActionButton
    lateinit var overlay: View
    var clickedId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        fab = findViewById(R.id.fab_test) as FloatingActionButton
        overlay = findViewById(R.id.fab_overlay)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        clickedId = item!!.itemId
        return super.onOptionsItemSelected(item)
    }

}

val MAIN_FAB_ANIMATION_DURATION = 200
val TAG = "Fab.ulous"

/**
 * Class that contain the sub-menu view and the opening and closing animation.
 */
data class SubFABMenu(val subFAB: View, var openingAnimation: AnimatorSet? = null, var closingAnimation: AnimatorSet? = null)

/**
 * A Fab.ulous menu.
 */
class Fabulous(val activity: Activity) {

    internal val menuViews: MutableList<SubFABMenu> = mutableListOf()
    internal var menuId: Int = 0
    internal var fab: FloatingActionButton? = null
    internal var fabOverlay: View? = null
    internal var fragment: Fragment? = null
    internal var container: ViewGroup? = null
    var isOpen: Boolean = false
    internal var backgroundTint: Int = 0
    internal var mainFABClickListener: View.OnClickListener? = null
    internal var mainFABTransition: FabulousTransition = object : FabulousTransition {

    }
    private var subMenuPattern: FabulousPattern? = null

    val isValid: Boolean
        get() = when {
            menuId < 1 -> false
            fab == null -> false
            container == null -> false
            subMenuPattern == null -> false
            else -> true
        }

    /**
     * generic onClick listener for the FAB that control the opening/closing of the menu
     */
    private val openMenuListener = View.OnClickListener {
        if (isOpen) {
            closeMenu()
        } else {
            openMenu()
        }
        mainFABClickListener?.onClick(fab)
    }
    /**
     * FAB generic onClick listener if the manager is not properly setup
     */
    private val wrongSetupMenuListener = View.OnClickListener { Log.w(TAG, "error in configuration") }

    /**
     * Hide fab overlay with animation
     */
    fun hideFabOverlay() {
        if (fabOverlay != null) {
            fabOverlay!!.animate().alpha(0.0f).withLayer().setDuration(300).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    fabOverlay?.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    fabOverlay?.visibility = View.GONE
                }
            }).start()
        }
    }

    /**
     * Show fab overlay with animation
     */
    fun showFabOverlay() {
        if (fabOverlay != null) {
            fabOverlay!!.animate().alpha(1.0f).withLayer().setDuration(300).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    fabOverlay!!.visibility = View.VISIBLE
                }
            }).start()
        }
    }


    /**
     * remove the sub menu from the screen
     */
    fun closeMenu() {
        if (menuViews.isNotEmpty()) {
            //Do the animation before removing the view
            val anim = AnimatorSet()
            val animBuilder = anim.play(mainFABTransition.getClosingAnimation(fab!!, fab!!.x, fab!!.y))
            for (subMenu in menuViews) {
                animBuilder.with(subMenu.closingAnimation)
            }
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    menuViews.forEach { container?.removeView(it.subFAB) }
                }
            })
            anim.start()
            isOpen = false
        }
        hideFabOverlay()
    }

    /**
     * base on the menu xml, and the chosen pattern, this method build the menu
     */
    fun openMenu() {
        val fabMenu = NavigationMenu(fab?.context)
        activity.menuInflater.inflate(menuId, fabMenu)
        val menuSize = fabMenu.size()
        //setup animation set
        //Do the animation after adding the view
        val anim = AnimatorSet()
        val animBuilder = anim.play(mainFABTransition.getOpeningAnimation(fab!!, fab!!.x, fab!!.y))
        for (i in 0..menuSize - 1) {
            val item = fabMenu.getItem(i)
            val subItem: SubFABMenu
            val newButton = FloatingActionButton(fab?.context)
            newButton.setImageDrawable(item.icon)
            newButton.x = fab!!.x
            newButton.y = fab!!.y
            if (backgroundTint < 0) {
                newButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(activity, backgroundTint))
            }
            newButton.setOnClickListener({
                if (fragment == null) {
                    activity.onOptionsItemSelected(item)
                } else {
                    fragment?.onOptionsItemSelected(item)
                }
            })
            subItem = positionButton(newButton, i, menuSize)
            animBuilder.with(subItem.openingAnimation)
            menuViews.add(subItem)

        }
        anim.start()
        showFabOverlay()
        isOpen = true
    }

    /**
     * Compute the position of the sub button on the screen and setup opening and closing animation.
     */
    private fun positionButton(element: View, position: Int, numOfElem: Int): SubFABMenu {
        val posX = fab!!.x
        val posY = fab!!.y
        val item = SubFABMenu(subFAB = element, closingAnimation = subMenuPattern?.getClosingAnimation(element, posX, posY))
        val newPosition = subMenuPattern?.getFinalPosition(element, posX, posY, position, numOfElem)
        // 0.8 is the mini size of FAB
        element.scaleX = 0.8f
        element.scaleY = 0.8f
        item.openingAnimation = subMenuPattern?.getOpeningAnimation(element, newPosition!!.x, newPosition.y)
        container?.addView(element)
        return item
    }

    /**
     * Builder class that help you setup a Fab.ulous menu.
     */
    class Builder(activity: Activity) {
        var fabulous = Fabulous(activity)

        constructor(fragment: Fragment) : this(fragment.activity) {
            setFragment(fragment)
        }

        fun setFABOnClickListener(mainFABClickListener: View.OnClickListener): Fabulous.Builder {
            fabulous.mainFABClickListener = mainFABClickListener
            return this
        }

        fun setBackgroundTint(backgroundTint: Int): Fabulous.Builder {
            fabulous.backgroundTint = backgroundTint
            return this
        }

        private fun setFragment(fragment: android.support.v4.app.Fragment): Fabulous.Builder {
            fabulous.fragment = fragment
            return this
        }

        fun setFab(fab: FloatingActionButton): Fabulous.Builder {
            fabulous.fab = fab
            fabulous.container = fab.parent as ViewGroup
            return this
        }


        fun setMenuId(menuId: Int): Fabulous.Builder {
            fabulous.menuId = menuId
            return this
        }

        fun setFabOverlay(fabOverlay: View): Fabulous.Builder {
            fabulous.fabOverlay = fabOverlay
            return this
        }

        fun setMenuPattern(pattern: FabulousPattern): Fabulous.Builder {
            fabulous.subMenuPattern = pattern
            return this
        }

        fun setFabTransition(transition: FabulousTransition): Fabulous.Builder {
            fabulous.mainFABTransition = transition
            return this
        }

        fun build(): Fabulous {
            finalize()
            return fabulous
        }

        fun finalize() {
            //check that all the needed value are provided
            if (fabulous.isValid) {
                fabulous.fab!!.setOnClickListener(fabulous.openMenuListener)
                if (fabulous.fabOverlay != null) {
                    fabulous.fabOverlay!!.setOnClickListener { fabulous.closeMenu() }
                }
            } else if (fabulous.fab != null) {
                fabulous.fab!!.setOnClickListener(fabulous.wrongSetupMenuListener)
                if (fabulous.fabOverlay != null) {
                    fabulous.fabOverlay!!.setOnClickListener(null)
                }
            }
        }


    }
}
