package com.trendsetter.deck_out.Extra;

import android.view.View;
import android.view.animation.RotateAnimation;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class Arrowanimation {

    public RotateAnimation animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
       return  rotate;
    }

    public RotateAnimation animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        return rotate;
    }
}
