package com.trendsetter.deck_out.Extra;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

import java.util.Random;

public class Otpgenerator {


    public String generateotp() {
         String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
         String intChar = "0123456789";

        Random r = new Random();
        String pass = "";
        while (pass.length() != 6) {
            int rPick = r.nextInt(2);

            if (rPick == 0) {
                int spot = r.nextInt(25);
                pass += uCase.charAt(spot);

            } else if (rPick == 1) {
                int spot = r.nextInt(9);
                pass += intChar.charAt(spot);
            }
        }
        return pass;
    }

    private void setemailotp()
    {



    }
}
