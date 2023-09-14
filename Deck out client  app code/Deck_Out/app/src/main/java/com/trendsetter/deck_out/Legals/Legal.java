package com.trendsetter.deck_out.Legals;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.NonScrollExpandableListView;
import com.trendsetter.deck_out.Login_Signup.Login;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Legal extends AppCompatActivity {


    ArrayList<String> menu;
    HashMap<String, String> listDataansChild;
    legalexplistadapter listAdapter;
    NonScrollExpandableListView faqexplist;
    TextView ratus , contactus , feedback;
    RelativeLayout softversionbtn ;
    String contacttxt = "" , usernametxt , propictxt;
    Dialog feedbackaddresspannel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legallayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.legaltoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Legal");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getuseridfromdata() != null)
        {
            getusername();
        }

        softversionbtn = findViewById(R.id.softwareversioncode);
        softversionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog versiondetailpannel = new Dialog(Legal.this);
                versiondetailpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                versiondetailpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                versiondetailpannel.setCanceledOnTouchOutside(false);
                versiondetailpannel.setCancelable(true);
                versiondetailpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                versiondetailpannel.setContentView(R.layout.versiondetaillayout);

                TextView  versiondetailtxt = versiondetailpannel.findViewById(R.id.versiondetail);
                versiondetailtxt.setText(Html.fromHtml("<html> <body> <p> <h2>  Software version  :  2 . 0  </h2> </p> <br>  <p> Developed By \" Dream Hunterz Technology \"  <br>  <br> Mail Developer on : <br> <br> "+getResources().getString(R.string.devmailid)+"</p> </body> </html>"));

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

        ratus = findViewById(R.id.legalrateus);
        ratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Legal.this ,"Opening Play Store Please wait ...." ,Toast.LENGTH_SHORT).show();
                Intent   intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        feedback = findViewById(R.id.legalfeedbackbtn);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackaddresspannel = new Dialog(Legal.this);
                feedbackaddresspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                feedbackaddresspannel.getWindow().setWindowAnimations(R.style.animateddialog);
                feedbackaddresspannel.setCanceledOnTouchOutside(false);
                feedbackaddresspannel.setCancelable(true);
                feedbackaddresspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                feedbackaddresspannel.setContentView(R.layout.feedbacklayout);

                Spinner  feedbacktypeselector = feedbackaddresspannel.findViewById(R.id.feedbackspinner);
                String[] types = {"Report an Error", "Suggestion / Advice", "Anything else"};
                ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(Legal.this, R.layout.spinner_text, types);
                langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                feedbacktypeselector.setAdapter(langAdapter);

                Button feedbacksubmitbutton = feedbackaddresspannel.findViewById(R.id.feedbacksubmitbutton);
                EditText feedbackcommentbox = feedbackaddresspannel.findViewById(R.id.feedbackcommentbox);

                feedbacksubmitbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (getuseridfromdata() == null)
                        {
                           AlertDialog.Builder message = new androidx.appcompat.app.AlertDialog.Builder(Legal.this);
                            message.setTitle("Guest User");
                            AlertDialog alert;
                            message.setMessage("\nYou have to Login First to give feedback us .\nClick on Login to Login / Sign up to our Service .");
                            message.setCancelable(true);

                            message.setNegativeButton("Close",null);
                            message.setNegativeButton("Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Legal.this , Login.class));
                                    finish();
                                }
                            });
                            alert = message.create();
                            alert.show();
                        }

                        else
                       {
                           
                           String feedbacktype = null;
                           switch (feedbacktypeselector.getSelectedItemPosition())
                           {
                               case 0:
                                   feedbacktype = "#rae";
                                   break;

                               case 1:
                                    feedbacktype = "#sgad";
                                   break;


                               case 2:
                                   feedbacktype = "#ane";
                                   break;
                           }
                           
                            createfeedback(feedbacktype, feedbackcommentbox.getText().toString() , getuseridfromdata() , usernametxt , propictxt);
                        }
                    }
                });


                ImageView changepannelclosebtn = feedbackaddresspannel.findViewById(R.id.feedbackdialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedbackaddresspannel.dismiss();
                    }
                });

                feedbackaddresspannel.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                feedbackaddresspannel.getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                feedbackaddresspannel.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                feedbackaddresspannel.show();

            }
        });


        contactus = findViewById(R.id.legalcontactus);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog contactuspannel = new Dialog(Legal.this);
                contactuspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                contactuspannel.getWindow().setWindowAnimations(R.style.animateddialog);
                contactuspannel.setCanceledOnTouchOutside(false);
                contactuspannel.setCancelable(true);
                contactuspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                contactuspannel.setContentView(R.layout.contactuslayout);
                final TextView contactustxt = contactuspannel.findViewById(R.id.contactustxt);
                final ImageView callbtn = contactuspannel.findViewById(R.id.contactuscallbtn);

                final Intent intent = new Intent(Intent.ACTION_DIAL);
                contactustxt.setText(Html.fromHtml("Having  Quieries ?<br><br>Don't hesitate feel free to Contact us.<br><br>on - +91  "+contacttxt));
                callbtn.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Legal.this ,"Opening Dialler Please wait ...." ,Toast.LENGTH_SHORT).show();
                        String uri = "tel:" + "+91"+contacttxt;
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    }
                });
                ImageView changepannelclosebtn = contactuspannel.findViewById(R.id.contactusdialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contactuspannel.dismiss();
                    }
                });
                contactuspannel.show();
                Window window = contactuspannel.getWindow();
                window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });



        faqexplist =  findViewById(R.id.legalexplist);
        menu = new ArrayList<>();
        listDataansChild = new HashMap<>();
        menu.clear();
        listDataansChild.clear();

        new getdata().execute();

    }


    private void createfeedback(String type , String commentsdata , String id , String name , String propic)
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference feedbackdataref = database.getReference("dataextra/Feedback");
        Map<String, String> feedbackData = new HashMap<String, String>();

        feedbackData.put("id",id);
        feedbackData.put("name" , name);
        feedbackData.put("propic" , propic);
        feedbackData.put("type",type);
        feedbackData.put("feedbacktxt", commentsdata);

        String key = feedbackdataref.push().getKey();

        feedbackdataref.child(key).setValue(feedbackData);

        feedbackaddresspannel.dismiss();
        android.app.AlertDialog.Builder deleteitemmsg = new android.app.AlertDialog.Builder(Legal.this);
        deleteitemmsg.setTitle("Message");
        deleteitemmsg.setMessage("\n\nThank you for your Valuable feedback we will try to look into it.");
        deleteitemmsg.setPositiveButton("Ok", null);
        deleteitemmsg.show();



    }


    private void getdatalist()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference parentReference = database.getReference("dataextra/Legal");

        //reading data from firebase
        parentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    final String datakey= snapshot.getKey();
                    final DatabaseReference childref = parentReference;
                    childref.child(datakey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                String key= snapshot.getKey();
                                String value=snapshot.getValue().toString();


                                if (key.equals("Answer"))
                                {
                                    setListDataansChild(datakey,value);
                                }


                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }





    private void setListDataansChild(String datakey , String data)
    {

        menu.add(datakey);
        listDataansChild.put(datakey, data);

    }


    private String getuseridfromdata()
    {
         SharedPreferences sp;
        sp=getSharedPreferences("Login", MODE_PRIVATE);
       String  uid=sp.getString("userid","");

       return uid;

    }


    public class getdata extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getdatalist();
            getcontactno ();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            listAdapter = new legalexplistadapter(Legal.this,listDataansChild ,menu);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            faqexplist.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }

    private void getcontactno ()
    {
        DatabaseReference contactref = FirebaseDatabase.getInstance().getReference("deckoutadmin/adminphno");
        contactref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setcontact(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setcontact(String phnotxt)
    {
        this.contacttxt = phnotxt;
    }


    private void  getusername()
    {
        DatabaseReference usernameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+getuseridfromdata().trim()+"/Name");
        usernameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setusername(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setusername(String nametxt)
    {
        this.usernametxt = nametxt;
        getuserpropic();
    }


    private void getuserpropic()
    {
        DatabaseReference userpropicref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+getuseridfromdata().trim()+"/Profilepic");
        userpropicref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setuserpropic(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setuserpropic(String propictxt)
    {
        this.propictxt = propictxt;
    }


}
