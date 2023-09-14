package com.trendsetter.deck_out.Login_Signup;

import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDex;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.trendsetter.deck_out.R;

public class MainActivity extends AppCompatActivity {

    Dialog otppannel;
    TextView phnotxt , otptxtinput;
    Button resendotpbtn;
    CircleProgressBar signupprogressbar;
    String phonenotxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginpagefragmentcontainer, new Signup());
        transaction.addToBackStack(null).commit();




    }



    @Override
    public void onBackPressed() {

    }
}
