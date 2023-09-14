package com.trendsetter.deck_out.Login_Signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.trendsetter.deck_out.Extra.Drawablesdata;
import com.trendsetter.deck_out.R;

import java.util.HashMap;
import java.util.Map;

public class Signup extends Fragment {

    private Button  switchloginbtn , signupbtn;
    EditText signupusername , signupemailid , signupmobno , signuppassword;
    CheckBox showhidepass;
    Drawable erroricon;
    private int signupcounter=0 ;
    private int usercount=0;
    private int checkcount=0;
    String uid;
    CircleProgressBar signupprogressbar;
    private String userphno , useremailid;
    Handler handler = new Handler();
    Runnable runnable;
    int finalI = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginpagesignuplayout, container, false);
        switchloginbtn = view.findViewById(R.id.switchlogin);
        erroricon = new Drawablesdata().getdrawablewhite(getActivity());

        signupusername = view.findViewById(R.id.loginpagesignupuser_name);
        signupemailid = view.findViewById(R.id.loginpagesignupemailid);
        signupmobno = view.findViewById(R.id.loginpagesignupmobno);
        signuppassword = view.findViewById(R.id.loginpagesignuppass);

        showhidepass = view.findViewById(R.id.loginpagesignupshowhidepass);

        signupbtn   = view.findViewById(R.id.loginpagesignupbtn);

        signupprogressbar = view.findViewById(R.id.signupprogressBar);

        instlistener();

        getusercount();

        return view;
    }



    private void getusercount()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userdataref = database.getReference("datarecords/deckoutusers/usercout");
        userdataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usercount = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setusercount()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userdataref = database.getReference("datarecords/deckoutusers/usercout");
        userdataref.setValue(usercount+1);

        setuserindex(usercount);
    }




    private void instlistener()
    {
        showhidepass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    signuppassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

                else
                {
                    signuppassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signupusername.getText().toString().trim().length() == 0)
                {
                    signupusername.setError("is Empty",erroricon);
                    signupcounter+=1;
                }

                if(signupemailid.getText().toString().trim().length() == 0)
                {
                    signupemailid.setError("is Empty",erroricon);
                    signupcounter+=1;
                }

                else if(!signupemailid.getText().toString().contains("@")&&!signupemailid.getText().toString().contains(".")) {
                    signupemailid.setError("Invalid Email format",erroricon);
                    signupcounter+=1;
                }


                if(signupmobno.getText().toString().trim().length() == 0)
                {
                    signupmobno.setError("is Empty",erroricon);
                    signupcounter+=1;
                }

                if(signupmobno.getText().toString().trim().length() < 10)
                {
                    signupmobno.setError("Less than 10",erroricon);
                    signupcounter+=1;
                }

                if(signuppassword.getText().toString().trim().length() == 0)

                {
                    signuppassword.setError("is Empty",erroricon);
                    signupcounter+=1;
                }

                if(signuppassword.getText().toString().trim().length() > 0)

                {

                    if (signuppassword.getText().toString().trim().length() < 8)

                    {
                        signuppassword.setError("less than 8 character", erroricon);
                        signupcounter += 1;
                    }

                }



                if(signupcounter==0)
                {
                    useremailid = signupemailid.getText().toString();
                    userphno = signupmobno.getText().toString();
                    new checkuserdata().execute();

                }

                signupcounter = 0;


            }
        });

        switchloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.dialog_in,R.anim.dialog_out);
                transaction.replace(R.id.loginpagefragmentcontainer, new Login());
                transaction.addToBackStack(null).commit();
            }
        });



    }


    private void registeruser(String name , String emailid , String mobno , String password)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userdataref = database.getReference("datarecords/deckoutusers/Client/DOUID"+usercount);

        Map<String, String> userData = new HashMap<String, String>();

        userData.put("Name", name);
        userData.put("Emailid", emailid);
        userData.put("Mobno", "+91 "+mobno);
        userData.put("Password", password);
        userData.put("Profilepic","N/A");
        userData.put("addresscount" , "0");
        userData.put("mycartitemcount" ,"0");
        userData.put("myordertotalcount" , "0");
        userData.put("mywishlistcount" , "0");
        userData.put("Phonenoverifictaion" , "#N");
        userData.put("Mailidverifictaion" , "#N");
        userData.put("mycarttotalprice","0");
        userData.put("myorderpendingcount" ,"0");
        userdataref.setValue(userData);

        setusercount();

        signupemailid.setText("");
        signupmobno.setText("");
        signupusername.setText("");
        signuppassword.setText("");
    }


    @SuppressLint("ResourceAsColor")
    private void setprogress()
    {
        signupprogressbar.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_blue_dark,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        runnable = new Runnable() {

            @Override
            public void run() {
                if(finalI *10>=90){

                }
                else {
                    signupprogressbar.setProgress(finalI * 10);
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            finalI = i;
            handler.postDelayed(runnable,1000*(i+1));
        }


    }


    private  void setuserindex(int usercount)
    {

         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference setnameidref = database.getReference("dataindex/userindex/client/Name");
         DatabaseReference setemailididref = database.getReference("dataindex/userindex/client/Emailid");
         DatabaseReference setphoneidref = database.getReference("dataindex/userindex/client/Phoneno");

        setnameidref.child(signupusername.getText().toString().trim()+"_ID"+usercount).setValue("DOUID"+usercount);
        setemailididref.child(signupemailid.getText().toString().trim().replace("@" , "~").replace("." , "`")).setValue("DOUID"+usercount);
        uid = "DOUID"+usercount;
        setphoneidref.child(signupmobno.getText().toString().trim()).setValue(uid);


    }

    private  void  checkuserdetails()
    {
        if(checkcount == 0) {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference checkphoneidref = database.getReference("dataindex/userindex/client/Phoneno");

            checkphoneidref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(signupmobno.getText().toString()).exists()) {
                        checkcount+=2;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            final DatabaseReference checkemailidref = database.getReference("dataindex/userindex/client/Emailid");

            checkemailidref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(signupemailid.getText().toString().replace("@", "~").replace(".", "`")).exists()) {
                        checkcount+=1;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



    }


    private class checkuserdata extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            checkuserdetails();
            setprogress();
            signupprogressbar.setVisibility(View.VISIBLE);
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

            if(checkcount == 0)
            {
                registeruser(signupusername.getText().toString(), signupemailid.getText().toString(), signupmobno.getText().toString(), signuppassword.getText().toString());
                proceedtoreg();
            }


            if (checkcount == 1)
            {
                setdialog(0);
            }

            if (checkcount == 2)
            {
                setdialog(1);
            }

            if (checkcount == 3)
            {
                setdialog(2);
            }


            checkcount = 0;

            handler.removeCallbacks(runnable);
            signupprogressbar.setVisibility(View.GONE);
        }

    }



    private void setdialog(int pos)
    {

        AlertDialog.Builder message = new AlertDialog.Builder(getContext());
        message.setTitle("Message");
        AlertDialog alert;
        switch (pos)
        {
            case 0 :
                message.setMessage("This Email id already Registered please use Another Email id.");
                message.setCancelable(true);

                message.setNegativeButton("Ok",null);

                alert = message.create();
                alert.show();
                break;

            case 1 :
                message.setMessage("This Phone No. already Registered please use Another Phone No.");
                message.setCancelable(true);

                message.setNegativeButton("Ok",null);

                alert = message.create();
                alert.show();
                break;

            case 2 :
                message.setMessage("This Email id and Phone No. already Registered please use Another Email id.");
                message.setCancelable(true);

                message.setNegativeButton("Ok",null);

                alert = message.create();
                alert.show();
                break;
        }
    }


    private void proceedtoreg()
    {
        startActivity(new Intent(getContext() , Regverificationph.class).putExtra("useremail",useremailid).putExtra("userphoneno" , userphno).putExtra("uid",uid));
    }





}
