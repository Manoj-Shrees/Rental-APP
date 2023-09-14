package com.trendsetter.deckout_Delivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login  extends AppCompatActivity {

    EditText phnotxt , passtxt ;
    CheckBox showpasschk ;
    Button loginbtn ;
    int logincounter = 0;
    private SharedPreferences sp;
    SharedPreferences.Editor Ed;
    AlertDialog.Builder  logindialog;
    AlertDialog logindialogalert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loginlayout);

        sp = getSharedPreferences("Login", MODE_PRIVATE);
        Ed=sp.edit();

        phnotxt = findViewById(R.id.uid);
        passtxt = findViewById(R.id.pass);


        showpasschk = findViewById(R.id.hideshowpassbtn);

        showpasschk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    passtxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

                else
                {
                    passtxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phnotxt.getText().toString().trim().length() == 0)
                {
                    phnotxt.setError("is Empty");
                    logincounter+=1;
                }

                if(passtxt.getText().toString().trim().length() == 0)
                {
                    passtxt.setError("is Empty");
                    logincounter+=1;
                }

                if(logincounter == 0)
                {
                    logindialog = new AlertDialog.Builder(Login.this);
                    logindialog.setTitle("Processing");
                    logindialog.setMessage("\nPlease Wait for a while");
                    logindialog.setCancelable(false);
                    logindialogalert = logindialog.create();
                    logindialogalert.show();

                    checkuid(phnotxt.getText().toString() , passtxt.getText().toString());
                }

                logincounter = 0;
            }
        });
    }




    private void loginuserwithphone(final String uid , final String id , final String password) {


        final DatabaseReference checkuid = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Deliveryboy/" + uid);
        checkuid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Mobno").getValue().equals(id.trim())) {
                    DatabaseReference checkpass = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Deliveryboy/" + uid);
                    checkpass.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Password").getValue().equals(password)) {
                                startActivity(new Intent(Login.this, MainActivity.class).putExtra("userid", uid));
                                Ed.putString("userid", uid);
                                Ed.commit();
                                finish();
                            }

                            else {
                                logindialogalert.dismiss();
                                AlertDialog.Builder message = new AlertDialog.Builder(Login.this);
                                message.setTitle("Password not matched");
                                AlertDialog alert;
                                message.setMessage("\nContact Deck Out Admin to Reset or get your Password.");
                                message.setCancelable(true);
                                message.setNegativeButton("Ok", null);
                                alert = message.create();
                                alert.show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


         private void checkuid(final String id , final String pass)
        {
            final DatabaseReference checkdata = FirebaseDatabase.getInstance().getReference("dataindex/userindex/Deliveryboy");
            checkdata.child("Phoneno").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(id).exists()) {

                        loginuserwithphone(dataSnapshot.child(id).getValue().toString() ,"+91 "+id , pass);
                    }

                    else
                    {
                        logindialogalert.dismiss();
                        AlertDialog.Builder message = new AlertDialog.Builder(Login.this);
                        message.setTitle("Message");
                        AlertDialog alert;
                        message.setMessage("Contact Deck Out Admin to Register your Account.");
                        message.setCancelable(true);
                        message.setNegativeButton("Ok", null);
                        alert = message.create();
                        alert.show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
}
