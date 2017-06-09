package com.fueled.fabulous.sample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.PathInterpolator;

import com.fueled.fabulous.FabulousPattern;
import com.fueled.fabulous.FabulousPosition;

import org.jetbrains.annotations.NotNull;

/**
 * Created by julienFueled on 6/9/17.
 */

public class CirclePattern implements FabulousPattern {
    private static final int MAIN_FAB_ANIMATION_DURATION = 200;

    @NotNull
    @Override
    public AnimatorSet getClosingAnimation(@NotNull View element, float destX, float destY) {
        AnimatorSet anim = new AnimatorSet();
        ObjectAnimator fabX = ObjectAnimator.ofFloat(element, View.X, destX);
        fabX.setDuration(MAIN_FAB_ANIMATION_DURATION);
        ObjectAnimator fabY = ObjectAnimator.ofFloat(element, View.Y, destY);
        fabY.setDuration(MAIN_FAB_ANIMATION_DURATION);
        anim.play(fabX).with(fabY);
        return anim;
    }

    @NotNull
    @Override
    public AnimatorSet getOpeningAnimation(@NotNull View element, float destX, float destY) {
        AnimatorSet anim = new AnimatorSet();
        ObjectAnimator fabX = ObjectAnimator.ofFloat(element, View.X, destX);
        fabX.setDuration(MAIN_FAB_ANIMATION_DURATION);
        ObjectAnimator fabY = ObjectAnimator.ofFloat(element, View.Y, destY);
        fabY.setDuration(MAIN_FAB_ANIMATION_DURATION);
        anim.play(fabX).with(fabY);
        return anim;
    }


    @NotNull
    @Override
    public FabulousPosition getFinalPosition(@NotNull View subMenu, float fabX, float fabY, int position, int menuSize) {
        double spaceAlpha = (2 * Math.PI) / menuSize;
        double posX = fabX + (Math.sin((position - (3 * menuSize - 1) / 2f) * spaceAlpha) * 400);
        double posY = fabY + (Math.cos((position - (3 * menuSize - 1) / 2f) * spaceAlpha) * 400);
        return new FabulousPosition((float) posX, (float) posY);
    }
}
