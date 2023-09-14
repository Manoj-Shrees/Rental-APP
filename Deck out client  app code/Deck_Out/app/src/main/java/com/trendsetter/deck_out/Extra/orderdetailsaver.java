package com.trendsetter.deck_out.Extra;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class orderdetailsaver {


    private void setorderdata(Context context , HashMap map)
    {
        SharedPreferences.Editor prefsEditor = context.getSharedPreferences("" , MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        prefsEditor.putString("clientOrderdetails", json);
        prefsEditor.commit();
    }
}
