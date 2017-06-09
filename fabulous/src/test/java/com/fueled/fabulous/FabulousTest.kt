package com.fueled.fabulous

import android.animation.AnimatorSet
import android.support.v4.app.Fragment
import android.view.View
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment


/**
 * Created by julienFueled on 6/7/17.
 */

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "AndroidManifest.xml", constants = BuildConfig::class)
class FabulousTest {
    val activityMock = Robolectric.setupActivity(TestActivity::class.java)
    val fragmentMock = Fragment()

    val onClickMock = View.OnClickListener {

    }

    val subMenuPatternMock = object : FabulousPattern {
        override fun getFinalPosition(subMenu: View, fabX: Float, fabY: Float, position: Int, menuSize: Int): FabulousPosition {
            return FabulousPosition(2f, 2f)
        }

    }

    @Before
    fun setup() {
        startFragment(fragmentMock)
    }

    @Test
    fun testFABMenuSetupIsValid() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .setFABOnClickListener(onClickMock)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(true))
    }

    @Test
    fun testFABMenuIsNotOpen() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .setFABOnClickListener(onClickMock)
                .build()
        assertThat(fab.isOpen, `is`<Boolean>(false))
    }

    @Test
    fun testFABMenuSetupIsInvalidNoMenu() {
        val fab = Fabulous.Builder(activityMock)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .setFABOnClickListener(onClickMock)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(equalTo<Boolean>(false)))
    }

    @Test
    fun testFABMenuSetupIsInvalidNoFAB() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setMenuPattern(subMenuPatternMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .setFABOnClickListener(onClickMock)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(false))
    }

    @Test
    fun testFABMenuSetupIsInvalidNoPattern() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(false))
    }

    @Test
    fun testFABMenuSetupIsValidNoTint() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(true))
    }

    @Test
    fun testFABMenuSetupIsValidNoSpacer() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(true))
    }

    @Test
    fun testFABMenuSetupISValidFragment() {
        val fab = Fabulous.Builder(fragmentMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        assertThat(fab.isValid, `is`<Boolean>(true))
    }

    @Test
    fun testSubFABMenu() {
        val aset = AnimatorSet()
        val testObj = SubFABMenu(activityMock.fab, aset, aset)

        assertThat(testObj.closingAnimation, `is`<AnimatorSet>(equalTo<AnimatorSet>(aset)))
        assertThat(testObj.openingAnimation, `is`<AnimatorSet>(equalTo<AnimatorSet>(aset)))
        assertThat(testObj.subFAB, `is`<View>(activityMock.fab))
    }

    @Test
    fun testClickMainFABOpenCloseMenu() {
        val fab = Fabulous.Builder(fragmentMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        activityMock.fab.performClick()
        assertThat(fab.isOpen, `is`(true))
        activityMock.fab.performClick()
        assertThat(fab.isOpen, `is`(false))
    }

    @Test
    fun testFABOverlayVisibility() {
        val fab = Fabulous.Builder(fragmentMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setFabOverlay(activityMock.overlay)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        activityMock.fab.performClick()
        assertThat(activityMock.overlay.visibility, `is`<Int>(View.VISIBLE))
        activityMock.fab.performClick()
        assertThat(fab.isOpen, `is`(false))
    }

    @Test
    fun testFABOverlayClick() {
        val fab = Fabulous.Builder(fragmentMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setFabOverlay(activityMock.overlay)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        activityMock.fab.performClick()
        assertThat(activityMock.overlay.visibility, `is`<Int>(View.VISIBLE))
        activityMock.overlay.performClick()
        assertThat(fab.isOpen, `is`(false))
    }

    @Test
    fun testSubFABClick() {
        val fab = Fabulous.Builder(activityMock)
                .setMenuId(R.menu.menu_test)
                .setFab(activityMock.fab)
                .setFabOverlay(activityMock.overlay)
                .setMenuPattern(subMenuPatternMock)
                .setFABOnClickListener(onClickMock)
                .setBackgroundTint(android.R.color.holo_blue_bright)
                .build()
        activityMock.fab.performClick()
        fab.menuViews[0].subFAB.performClick()
        assertThat(activityMock.clickedId, `is`<Int>(R.id.menu_test_one))
    }
}