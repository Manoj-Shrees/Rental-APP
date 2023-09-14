package com.trendsetter.deck_out.Extra;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Debug;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trendsetter.deck_out.R;

import java.io.IOException;

public class CheckConnectivity  {

    Dialog dataconnectionstatuspannel;
    Button tryagainbtn , opensettingbtn;
    Context context;

    public CheckConnectivity(Context context)
    {
        dataconnectionstatuspannel = new Dialog(context);
        dataconnectionstatuspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dataconnectionstatuspannel.getWindow().setWindowAnimations(R.style.animateddialog);
        dataconnectionstatuspannel.setCanceledOnTouchOutside(false);
        dataconnectionstatuspannel.setCancelable(false);
        dataconnectionstatuspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dataconnectionstatuspannel.setContentView(R.layout.dataconnectivitystatslayout);

        opensettingbtn = dataconnectionstatuspannel.findViewById(R.id.dataconnectionopensettingbtn);
        tryagainbtn = dataconnectionstatuspannel.findViewById(R.id.tryagainbtn);

        this.context = context;
    }

    public  boolean isInternetAvailable() {
        boolean connection = false;

        ConnectivityManager ConnectionManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connection=true;
        }
        else
        {
            genratedialog();
            connection=false;
        }
        return connection;
    }



    public View gettrybutton()
    {
        return tryagainbtn;
    }

    public void closedialog()
    {
        dataconnectionstatuspannel.dismiss();
    }


   private void genratedialog()
   {


       opensettingbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
           }
       });



       dataconnectionstatuspannel.show();
       Window window = dataconnectionstatuspannel.getWindow();
       window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
               | View.SYSTEM_UI_FLAG_FULLSCREEN
               | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
               | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
       window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
   }

}
