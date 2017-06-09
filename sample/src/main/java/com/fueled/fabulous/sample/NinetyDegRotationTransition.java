package com.fueled.fabulous.sample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.fueled.fabulous.FabulousTransition;

import org.jetbrains.annotations.NotNull;

/**
 * Created by julienFueled on 6/9/17.
 */

public class NinetyDegRotationTransition implements FabulousTransition {
    private static final int MAIN_FAB_ANIMATION_DURATION = 200;

    @NotNull
    @Override
    public AnimatorSet getClosingAnimation(@NotNull View element, float destX, float destY) {
        AnimatorSet anim = new AnimatorSet();
        ObjectAnimator fabX = ObjectAnimator.ofFloat(element, View.ROTATION, 0);
        fabX.setDuration(MAIN_FAB_ANIMATION_DURATION);
        anim.play(fabX);
        return anim;
    }

    @NotNull
    @Override
    public AnimatorSet getOpeningAnimation(@NotNull View element, float destX, float destY) {
        AnimatorSet anim = new AnimatorSet();
        ObjectAnimator fabX = ObjectAnimator.ofFloat(element, View.ROTATION, 90);
        fabX.setDuration(MAIN_FAB_ANIMATION_DURATION);
        anim.play(fabX);
        return anim;
    }
}
