package com.trendsetter.deck_out.Extra;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.trendsetter.deck_out.R;


public class Drawablesdata {

    public Drawable getdrawablewhite(Context context)
    {
        Drawable customErrorDrawable = context.getResources().getDrawable(R.drawable.ic_error_white);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        return customErrorDrawable;
    }

    public Drawable getdrawableblack(Context context)
    {
        Drawable customErrorDrawable = context.getResources().getDrawable(R.drawable.ic_error_black);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        return customErrorDrawable;
    }
}
