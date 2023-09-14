package com.trendsetter.deckout_Delivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

public class Mainscreen extends AppCompatActivity {

    private SharedPreferences sp;
    String userref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainscreenlayout);

        sp=getSharedPreferences("Login", MODE_PRIVATE);
        userref=sp.getString("userid","");

    }


    @Override
    protected void onStart() {
        super.onStart();

        new setdatacache().execute();
    }

    private class setdatacache extends AsyncTask
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            checklogindata();
            finish();
        }
    }



    private void checklogindata()
    {

        if(userref.equals(""))
        {
                startActivity(new Intent(Mainscreen.this , Login.class));
        }

        else
        {
            startActivity(new Intent(Mainscreen.this , MainActivity.class).putExtra("userid",userref));
        }
    }


}
