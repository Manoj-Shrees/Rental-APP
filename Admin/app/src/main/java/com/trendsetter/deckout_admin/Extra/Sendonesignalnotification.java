package com.trendsetter.deckout_admin.Extra;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONArray;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;

public class Sendonesignalnotification extends FirebaseMessagingService {


    OkHttpClient mClient;
    JSONArray jsonArray;


    public Sendonesignalnotification()
    {

    }

    public Sendonesignalnotification(Context context)
    {
        mClient = new OkHttpClient();

        String refreshedToken =  FirebaseInstanceId.getInstance().getToken();
        jsonArray = new JSONArray();
        jsonArray.put(refreshedToken);

        Toast.makeText(context , "Sending Notidication" ,Toast.LENGTH_LONG).show();
    }



    public void sendNotification(String messagetxt)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String senderid = "Client";

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MTA3MDgzOGUtYTFhNy00YmNiLTg1YTktZWNlODIyNzQzMjgz");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"34712adf-b254-4146-b4e6-2164907c7b67\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"app_type\", \"relation\": \"=\", \"value\": \"" + senderid + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \""+messagetxt+"\"}"
                                + "}";



                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }



                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

}
