package com.trendsetter.deck_out.Login_Signup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.Drawablesdata;
import com.trendsetter.deck_out.Extra.Emaildata;
import com.trendsetter.deck_out.Extra.Emailverification;
import com.trendsetter.deck_out.R;

import java.util.Arrays;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class Loginforgotpass extends AppCompatActivity {

    TextView  frgtpasshintemail  , newpass , confirmpass;
    Button genoptbtn , verifyotpbtn;
    EditText frgtpassemail;
    Pinview emailotppinview;
    private String uid , otpemailid , otpemailidpass;
    Dialog changepasspannel;
    int passerrorcount =  0 , otpsndcount = 0;
    CountdownView mCvCountdownView;
    protected String checkemail;
    Drawable erroricon;
    private Emaildata emaildata = new Emaildata();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginforgotpasslayout);

        if (Build.VERSION.SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        uid = getIntent().getExtras().getString("uid");
        otpemailid = getResources().getString(R.string.emailidforotp);
        otpemailidpass = getResources().getString(R.string.emailidpassforotp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.frgtpasstoolbar);
        setSupportActionBar(toolbar);

        erroricon = new Drawablesdata().getdrawablewhite(Loginforgotpass.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Reset Password");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        frgtpassemail = findViewById(R.id.frgtpassemail);
        frgtpasshintemail  = findViewById(R.id.frgtpasshintmail);
        emailotppinview = findViewById(R.id.frgtemailidpinview);
        genoptbtn = findViewById(R.id.frgtpassgenotp);
        verifyotpbtn = findViewById(R.id.frgtpassgenotpverify);
        verifyotpbtn.setEnabled(false);
        mCvCountdownView = (CountdownView) findViewById(R.id.frgtseccounter);

        getuseremailid();
        genoptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpsndcount < 2)
                {
                    sendemail();
                    otpsndcount+=1;
                }

                else
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Loginforgotpass.this);
                    builder1.setTitle("Message");
                    builder1.setMessage("\nplease try again you have been reached up to limit.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton("OK", null);

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });

        verifyotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkotp();

            }
        });


    }

    private void startcounter()
    {
        mCvCountdownView.start(30000);
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                genoptbtn.setBackgroundResource(R.drawable.resendbtnred);
                genoptbtn.setEnabled(true);
                genoptbtn.setText("Resend");
            }
        });

    }

    private void setemailidhint(String mailid) {
        checkemail = mailid;
        String temp;
        int loc = mailid.indexOf("@");
        temp = mailid.substring(1, loc);
        temp = mailid.replace(temp, "xxxxx");
        frgtpasshintemail.setText("   " + temp + "  ");

    }


    private void setotp()
    {
        DatabaseReference otpref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        otpref.child("emailotp").setValue(emaildata.getOtp());
    }

    private void getuseremailid()
    {
        DatabaseReference otpref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        otpref.child("Emailid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               setemailidhint(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkotp()
    {
        /*hideKeyboard();*/
        DatabaseReference otpref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        otpref.child("emailotp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().trim().equals(emailotppinview.getValue()))
                {
                    verifyotpbtn.setEnabled(false);
                    verifyotpbtn.setBackgroundResource(R.drawable.buttonbluefade);
                    openresetpass();
                }

                else
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Loginforgotpass.this);
                    builder1.setTitle("OTP verification failed");
                    builder1.setMessage("\nEntered OTP not matched Please check your Email and replace with the correct one. ");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton("OK",null);

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void openresetpass()
    {

        changepasspannel = new Dialog(Loginforgotpass.this);
        changepasspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changepasspannel.getWindow().setWindowAnimations(R.style.animateddialog);
        changepasspannel.setCanceledOnTouchOutside(false);
        changepasspannel.setCancelable(true);
        changepasspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changepasspannel.setContentView(R.layout.userpasswordsetlayout);
        TextView oldpass = changepasspannel.findViewById(R.id.profileoldpass);
        oldpass.setVisibility(View.GONE);
        newpass = changepasspannel.findViewById(R.id.profilenewpass);
        confirmpass = changepasspannel.findViewById(R.id.profileconfirmpass);
        ImageView changepannelclosebtn = changepasspannel.findViewById(R.id.profilechangepassdialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepasspannel.dismiss();
                finish();
            }
        });

        Button passupdatebrn = changepasspannel.findViewById(R.id.profilepassupbtn);
        passupdatebrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepass();
            }
        });
        changepasspannel.show();

        Window window = changepasspannel.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }



    private void sendemail()
    {
        String email = frgtpassemail.getText().toString();

        if(email.equals(checkemail))
        {
            hideKeyboard();
            startcounter();
            frgtpassemail.setEnabled(false);
            genoptbtn.setBackgroundResource(R.drawable.buttonbluefade);
            genoptbtn.setEnabled(false);
            emailotppinview.setEnabled(true);
            verifyotpbtn.setEnabled(true);
            verifyotpbtn.setBackgroundResource(R.drawable.buttonroundcornerblue);
            List<String> toEmail = Arrays.asList(email.split("\\s*,\\s*"));

            Toast.makeText(Loginforgotpass.this, "sending email", Toast.LENGTH_SHORT).show();


            new Thread(new Runnable() {

                @Override
                public void run() {
                    try
                    {
                        Emailverification sendemailtask = new Emailverification(Loginforgotpass.this , otpemailid,
                                otpemailidpass,toEmail,"Email Verification Code",emaildata.getdata());
                        sendemailtask.createEmailMessage();
                        sendemailtask.sendEmail();
                    }

                    catch (Exception e) {
                        System.out.print(e);
                    }

                }

            }).start();
            setotp();
        }

        else
        {
            frgtpassemail.setError("Not match" , erroricon);
        }

    }



    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private void updatepass()
    {
        hideKeyboard();
        Drawable erroricon = new Drawablesdata().getdrawablewhite(Loginforgotpass.this);

        if (newpass.getText().toString().equals(""))
        {
            newpass.setError("is Empty" , erroricon);
            passerrorcount+=1;

        }

        if (confirmpass.getText().toString().equals(""))
        {
            confirmpass.setError("is Empty" , erroricon);
            passerrorcount+=1;
        }

        if (!confirmpass.getText().toString().equals(newpass.getText().toString()))
        {
            confirmpass.setError("Not matched" , erroricon);
            passerrorcount+=1;
        }


        if(passerrorcount == 0) {

            DatabaseReference passref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
            passref.child("Password").setValue(newpass.getText().toString().trim());
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Loginforgotpass.this);
            builder1.setTitle("Message");
            builder1.setMessage("\nNew password has been sucessfully Updated . ");
            builder1.setCancelable(true);

            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

        passerrorcount=0;
    }
}
