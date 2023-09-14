package com.trendsetter.deck_out.Extra;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

public class Datechecker {

    private RequestQueue mqueue;
    private Context context ;

    public Datechecker(Context context)
    {
        mqueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public String getdate()
    {
        String todaydate = checkdate();

        return todaydate;
    }

    private String checkdate()
    {
        String url =  "http://worldclockapi.com/api/json/utc/now";
        final String[] currentdate = new String[1];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            currentdate[0] = response.getString("currentDateTime");
                            currentdate[0] = currentdate[0].substring(0 , currentdate[0].indexOf("T")).trim();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            currentdate[0] = sdf.format(sdf2.parse(currentdate[0]));
                            setcurrdate(currentdate[0]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mqueue.add(request);

        return currentdate[0];

    }


    private void setcurrdate(String datetxt)
    {

        SharedPreferences sp;
        SharedPreferences dateref = context.getSharedPreferences("currdateonl9", Context.MODE_PRIVATE);
        SharedPreferences.Editor daterefeditor = dateref.edit();
        daterefeditor.putString("nowdate",datetxt);
        daterefeditor.commit();

    }


}
