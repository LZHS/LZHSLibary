package com.lzhs.library.wedgit.banner.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateDownPageTransformer implements ViewPager.PageTransformer {

    private static final float ROT_MAX = 20.0f;
    private float mRot;


    public void transformPage(View view, float position) {
        if (position < -1) view.setRotation(0);
        else if (position <= 1) {
            if (position < 0) {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation(mRot);
            } else {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation(mRot);
            }
        } else view.setRotation( 0);
    }
}

