package com.trendsetter.deck_out.Extra;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.trendsetter.deck_out.Homepage.homepage;
import com.trendsetter.deck_out.Login_Signup.MainActivity;
import com.trendsetter.deck_out.R;

public class Introapp  extends AppCompatActivity {


    TextView openloginsignup , openmain;
    private SharedPreferences sp;
    private   SharedPreferences.Editor Ed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introlayout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        openloginsignup = findViewById(R.id.loginsignupbtn);
        openmain = findViewById(R.id.mainbtn);

        openloginsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Introapp.this, MainActivity.class));
            }
        });


        openmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Introapp.this);
                builder1.setTitle("Message");
                builder1.setMessage("\nDo you wish to continue as guest ? \n\nNote : Login as a guest will not allow you to use some features of this app.");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Ed.putString("usertype", "#guest");
                                Ed.commit();
                                startActivity(new Intent(Introapp.this, homepage.class).putExtra("usertype", "#guest").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });

        sp=getSharedPreferences("Login", MODE_PRIVATE);
        Ed=sp.edit();


    }


    @Override
    public void onBackPressed() {

    }


}
