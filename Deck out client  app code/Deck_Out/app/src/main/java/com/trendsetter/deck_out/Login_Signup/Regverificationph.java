package com.trendsetter.deck_out.Login_Signup;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.Otpgenerator;
import com.trendsetter.deck_out.Extra.sendphauthcode;
import com.trendsetter.deck_out.R;

import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;


public class Regverificationph extends AppCompatActivity {

    Button resendotpbtn;
    FirebaseAuth auth;
    Pinview phonepin ;
    String Phoneno , emailid , otptxt ;
    ProgressBar phonepbar;
    Button phoneverifybtn ;
    String mVerificationId , uid , otpemailid , otpemailidpass;
    ImageView infobtn;
    CountdownView mCvCountdownView;
    int funccounter = 0 , resendcounter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.regverificationlayoutph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.regverifytoolbar);
        setSupportActionBar(toolbar);
        uid = getIntent().getExtras().getString("uid");
        emailid =  getIntent().getExtras().getString("useremail");
        Phoneno =  getIntent().getExtras().getString("userphoneno");

        auth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Phone Number Verification");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexitmessage();
            }
        });

        resendotpbtn = findViewById(R.id.resendotpbtn);

        phonepbar = findViewById(R.id.phoneprogressbar);
        phoneverifybtn = findViewById(R.id.phoneveriftbtn);
        phoneverifybtn.setEnabled(false);

        infobtn = findViewById(R.id.regverifyinfobtn);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog versiondetailpannel = new Dialog(Regverificationph.this);
                versiondetailpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                versiondetailpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                versiondetailpannel.setCanceledOnTouchOutside(false);
                versiondetailpannel.setCancelable(true);
                versiondetailpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                versiondetailpannel.setContentView(R.layout.versiondetaillayout);

                TextView versiondetailtxt = versiondetailpannel.findViewById(R.id.versiondetail);
                versiondetailtxt.setText(Html.fromHtml("<html> <body> <p> <h2>  Email ID and Phone No. verification   </h2> </p> <br>  <p> As this verification is necessary for us to register you as genuine user and also to avoid login from bot user. <br><br>Thank You!!</p> </body> </html>"));

                ImageView changepannelclosebtn = versiondetailpannel.findViewById(R.id.softwareversiondialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        versiondetailpannel.dismiss();
                    }
                });
                versiondetailpannel.show();
                Window window = versiondetailpannel.getWindow();
                window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });

        mCvCountdownView = (CountdownView) findViewById(R.id.seccounter);
        startcounter();


        phoneverifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonepin.getValue().length() < 6)
                {
                    Toast.makeText(Regverificationph.this , "Otp Code is less than 6" , Toast.LENGTH_LONG).show();
                }

                else
                {
                    phonepbar.setVisibility(View.GONE);
                    checkotp(phonepin.getValue());
                }

            }
        });


        resendotpbtn = findViewById(R.id.resendotpbtn);
        resendotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (resendcounter < 1)
                {
                    resendotpbtn.setBackgroundResource(R.drawable.resendbtngrey);
                    resendotpbtn.setEnabled(false);

                    startcounter();
                    resendsms(Phoneno);
                }

                resendcounter+=1;

            }
        });

        phonepin = (Pinview) findViewById(R.id.phonepinview);
        phonepin.setEnabled(false);
        phonepin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                phonepbar.setVisibility(View.GONE);
                checkotp(phonepin.getValue());
            }
        });

    }


    private void checkotp(String otptxt)
    {
        if(this.otptxt.equals(otptxt))
        {
            hideKeyboard();
            Toast.makeText(Regverificationph.this,"Phone number Verified sucessfully",Toast.LENGTH_SHORT).show();
            phonepbar.setVisibility(View.GONE);
            phoneverifybtn.setEnabled(false);
            phoneverifybtn.setText("Verified");
            resendotpbtn.setEnabled(false);
            phoneverifybtn.setBackgroundResource(R.drawable.resendbtngrey);
            setphconfirmation();
            openregemailveri();
        }

        else
        {
            Toast.makeText(Regverificationph.this,"Phone number Verification failed",Toast.LENGTH_SHORT).show();
            phonepbar.setVisibility(View.VISIBLE);
            phoneverifybtn.setEnabled(false);
            phoneverifybtn.setText("Verify");
            phoneverifybtn.setBackgroundResource(R.drawable.resendbtnred);
        }

    }


     @Override
    protected void onStart() {
        super.onStart();
        if(funccounter == 0)
        {
            new sendotp().execute();
        }

        funccounter+=1;
    }

    private void startcounter()
    {
        mCvCountdownView.start(30000);
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                resendotpbtn.setBackgroundResource(R.drawable.resendbtnred);
                resendotpbtn.setEnabled(true);
            }
        });

    }



    public void  setsms(String signupmobno)
    {

        this.otptxt =  new Otpgenerator().generateotp();

        new sendphauthcode().sendsms(Regverificationph.this , signupmobno ,otptxt);
        Toast.makeText(Regverificationph.this,"Requesting sms for +91"+signupmobno,Toast.LENGTH_SHORT).show();
    }

    public void resendsms(String signupmobno)
    {
       setsms(signupmobno);
    }


    private void openregemailveri()
    {
        Intent opentonextreg = new Intent(Regverificationph.this , Regverificationemailid.class);

        opentonextreg.putExtra("useremail",emailid).putExtra("uid",uid);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {

            e.printStackTrace();

        }

        startActivity(opentonextreg);
        finish();

    }

    private void setphconfirmation()
    {
        DatabaseReference checkmailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/Phonenoverifictaion");
        checkmailidref.setValue("#Y");
    }



    public class sendotp extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           phonepbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            checkphverification();
        }
    }


    private void checkphverification()
    {
        DatabaseReference checkphref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        checkphref.child("Phonenoverifictaion").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("#N"))
                {
                    setsms(Phoneno);
                    phoneverifybtn.setBackgroundResource(R.drawable.buttonroundcornerblue);
                    phoneverifybtn.setEnabled(true);
                }

                else
                {
                    phonepin.setEnabled(false);
                    phonepbar.setVisibility(View.GONE);
                    phoneverifybtn.setBackgroundResource(R.drawable.buttonbluefade);
                    phoneverifybtn.setEnabled(false);
                    phoneverifybtn.setText("Verified");
                    openregemailveri();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onBackPressed() {
        openexitmessage();
    }


    private void openexitmessage()
    {
        AlertDialog.Builder message = new AlertDialog.Builder(Regverificationph.this);
        message.setTitle("Verification is on process");
        AlertDialog alert;
        message.setMessage("\n\nDo you wish you cancel verification ?");
        message.setCancelable(false);
        message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        message.setNegativeButton("No", null );
        alert = message.create();
        alert.show();
    }


}
