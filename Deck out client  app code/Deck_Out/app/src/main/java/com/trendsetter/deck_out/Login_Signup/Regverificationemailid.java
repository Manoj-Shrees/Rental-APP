package com.trendsetter.deck_out.Login_Signup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.goodiebag.pinview.Pinview;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.Emaildata;
import com.trendsetter.deck_out.Extra.Emailverification;
import com.trendsetter.deck_out.R;

import java.util.Arrays;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class Regverificationemailid extends AppCompatActivity {

    Button resendotpbtn;
    Pinview emailpin;
    String  emailid ;
    ProgressBar  emailpbar;
    Button  emailverifybtn ;
    String uid , otpemailid , otpemailidpass ;
    ImageView infobtn;
    CountdownView mCvCountdownView;
    Emaildata emaildata = new Emaildata();
    int funccounter = 0;
    RelativeLayout parentrootlayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regverificationemaililayout);

        otpemailid = getResources().getString(R.string.emailidforotp);
        otpemailidpass = getResources().getString(R.string.emailidpassforotp);

        uid = getIntent().getExtras().getString("uid");
        emailid =  getIntent().getExtras().getString("useremail");

        Toolbar toolbar = (Toolbar) findViewById(R.id.regverifytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Email Id Verification");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexitmessage();
            }
        });

        resendotpbtn = findViewById(R.id.resendotpbtn);

        emailpbar = findViewById(R.id.emailidprogressbar);

        emailverifybtn = findViewById(R.id.emailverifybtn);

        emailverifybtn.setEnabled(false);
        emailverifybtn.setBackgroundResource(R.drawable.buttonbluefade);

        emailpin = findViewById(R.id.emailidpinview);
        emailpin.setEnabled(false);
        emailpin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                checkemailidotp(emailpin.getValue());
                hideKeyboard();
            }
        });



        emailverifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailpin.getValue().trim().length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(parentrootlayout, "OTP is empty", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(Color.RED);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }

                else
                {
                    checkemailidotp(emailpin.getValue());
                }

            }
        });


        resendotpbtn = findViewById(R.id.resendotpbtn);
        resendotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendotpbtn.setBackgroundResource(R.drawable.resendbtngrey);
                resendotpbtn.setEnabled(false);
                startcounter();
                sendemail();
            }
        });


        emailpin = findViewById(R.id.emailidpinview);
        emailpin.setEnabled(false);
        emailpin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                checkemailidotp(emailpin.getValue());
            }
        });



        infobtn = findViewById(R.id.regverifyinfobtn);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog versiondetailpannel = new Dialog(Regverificationemailid.this);
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
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });

        mCvCountdownView = (CountdownView) findViewById(R.id.seccounter);
        startcounter();
    }




    private void checkmnailverification()
    {
        DatabaseReference checkmailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        checkmailidref.child("Mailidverifictaion").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("#N"))
                {
                    emailpbar.setVisibility(View.GONE);
                    emailpin.setEnabled(true);
                    emailverifybtn.setText("Verify");
                    emailverifybtn.setEnabled(true);
                    emailverifybtn.setBackgroundResource(R.drawable.buttonroundcornerblue);
                    sendemail();
                }

                else
                {
                    emailpbar.setVisibility(View.GONE);
                    emailpin.setEnabled(false);
                    emailverifybtn.setText("Verified");
                    emailverifybtn.setEnabled(false);
                    emailverifybtn.setBackgroundResource(R.drawable.buttonbluefade);
                    showverfidymsg();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setemailconfirmation()
    {
        DatabaseReference checkmailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/Mailidverifictaion");
        checkmailidref.setValue("#Y");
    }

    private void showverfidymsg()
    {
        AlertDialog.Builder message = new AlertDialog.Builder(Regverificationemailid.this);
        message.setTitle("Verified Sucessfully");
        AlertDialog alert;
        message.setMessage("\nNow Login with your Phone No. / Emaild to use our service\n\nThank You!!!");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = message.create();
        alert.show();
    }


    private void sendemail()
    {
        String email = emailid;
        emailpbar.setVisibility(View.VISIBLE);

        final List<String> toEmail = Arrays.asList(email.split("\\s*,\\s*"));

        Toast.makeText(Regverificationemailid.this,"Requesting otp for email id ",Toast.LENGTH_SHORT).show();


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Emailverification sendemailtask = new Emailverification(Regverificationemailid.this , otpemailid,
                            otpemailidpass,toEmail,"Email Verification Code",emaildata.getdata());
                    sendemailtask.createEmailMessage();
                    sendemailtask.sendEmail();
                } catch (Exception e) {
                    System.out.print(e);
                }

            }

        }).start();
        setemailotp();
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


    private void setemailotp()
    {
        DatabaseReference otpref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        otpref.child("/emailotp").setValue(emaildata.getOtp());
    }

    private void checkemailidotp(String otp)
    {

        DatabaseReference otpref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        otpref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("emailotp").getValue().toString().equals(otp))
                {
                    showverfidymsg();
                    setemailconfirmation();
                }

                else
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(Regverificationemailid.this);
                    message.setTitle("Otp not matched");
                    AlertDialog alert;
                    message.setMessage("\nPlease Enter Correct OTP recieved by your email id.");
                    message.setCancelable(true);
                    message.setNegativeButton("Ok",null);
                    alert = message.create();
                    alert.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public class sendotp extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            emailpbar.setVisibility(View.VISIBLE);
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
            checkmnailverification();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(funccounter == 0)
            new sendotp().execute();
            funccounter+=1;
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
        AlertDialog.Builder message = new AlertDialog.Builder(Regverificationemailid.this);
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
