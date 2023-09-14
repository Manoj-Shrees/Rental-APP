package com.trendsetter.deck_out.Extra;

import android.app.Activity;
import android.util.Log;

import com.trendsetter.deck_out.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendphauthcode {


    public   void  sendsms(final Activity context , final String cust_phno , String otp) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    String apiKey = "apikey=" + context.getResources().getString(R.string.smsserverapikey);
                    String message = "&message=" + otp+" is your verification code for Deck Out - The Dress Rental APP.";
                    String sender = "&sender=" + context.getResources().getString(R.string.senderid);
                    String numbers = "&numbers=" +cust_phno;

                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    rd.close();

                }

                catch (Exception e) {
                    System.out.println("Error SMS "+e);

                }

            }
        });

        thread.start();

    }

}
