package com.trendsetter.deckout_admin.Extra;

import android.app.Activity;
import android.util.Log;

import com.trendsetter.deckout_admin.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class linktosmsserver {




    public   void  sendsms(final Activity context , final String cust_phno , final String cust_name , int pos , String deldate) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

        try {

            String apiKey = "apikey=" + context.getResources().getString(R.string.smsserverapikey);
            String message = "&message=" + getmsg(pos , deldate , cust_name);
            String sender = "&sender=" + context.getResources().getString(R.string.senderid);
            String numbers = "&numbers=" +cust_phno;

            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            Log.e(">>msg" , data);
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


    private String getmsg(int pos , String deldate , String cust_name)
    {
        String message = "";
        switch (pos)
        {
            case 0:
                message = "Dear "+cust_name+" , your order has been confirmed and will be delivered soon.";
                break;

            case 1 :
                message = "Dear "+cust_name+" , your order has been dispatched . We will be delivering by "+deldate+".";
               break;

            case 2 :
                message = "Dear "+cust_name+" , your order will be delivered today . You are requested to be available to collect the order.";
                break;
        }

        return message;
    }






}
