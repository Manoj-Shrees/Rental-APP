package com.trendsetter.deck_out.Profile;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.Drawablesdata;
import com.trendsetter.deck_out.R;


public class Profiledetail extends AppCompatActivity {

    SharedPreferences sp;
    String userid;
    RelativeLayout addnewaddressbtn;
    TextView usernametxt , emailidtxt , phnotxt;
    private  EditText oldpass , newpass , confirmpass , pincode ;
    private  RecyclerView addresslist;
    private  FirebaseRecyclerOptions <profileaddressgetdata> options;
    private  ImageView changepasswordbtn , profileeditbtn;
    private  int addresscount = 0 , passerrorcount=0 , signuperrorcount = 0;
    protected String pass;
    private  DatabaseReference passref;
    private  Dialog changepasspannel , addnewaddresspannel;
    private int addadresscount = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        changepasswordbtn = findViewById(R.id.changepassbtn);
        profileeditbtn = findViewById(R.id.profileeditbtn);

        usernametxt = findViewById(R.id.profileuser_name);
        emailidtxt = findViewById(R.id.profileemailid);
        phnotxt = findViewById(R.id.profilephnoinput);

        addnewaddressbtn = findViewById(R.id.profileaddnewaddresslayout);


        addresslist = findViewById(R.id.profileaddresslist);
        addresslist.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Profiledetail.this);
        addresslist.setLayoutManager(layoutManager);
        if(!userid.equals("#guest")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/Addresses");
            Query query = ref;
            options = new FirebaseRecyclerOptions.Builder<profileaddressgetdata>()
                    .setQuery(query, profileaddressgetdata.class)
                    .build();
        }

        profileaddressitemadapter addressitemadapter = new profileaddressitemadapter(options , Profiledetail.this);
        addresslist.setAdapter(addressitemadapter);
        addressitemadapter.startListening();

        getaddresscount();
        getprofiledetails();
        if(!userid.equals("#guest")) {
            passref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/Password");
            passref.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pass = dataSnapshot.getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }


        addnewaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addnewaddressbtn.setEnabled(false);

                addnewaddresspannel = new Dialog(Profiledetail.this);
                addnewaddresspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addnewaddresspannel.getWindow().setWindowAnimations(R.style.animateddialog);
                addnewaddresspannel.setCanceledOnTouchOutside(false);
                addnewaddresspannel.setCancelable(true);
                addnewaddresspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addnewaddresspannel.setContentView(R.layout.updatedeliveryaddress);
                final EditText username = addnewaddresspannel.findViewById(R.id.deckoutusername);
                final EditText deliverymobno = addnewaddresspannel.findViewById(R.id.deckoutmobno);
                pincode = addnewaddresspannel.findViewById(R.id.deckoutpincode);
                final EditText houseno = addnewaddresspannel.findViewById(R.id.deckouthouseno);
                final EditText loc = addnewaddresspannel.findViewById(R.id.deckoutloc);
                final EditText landmark = addnewaddresspannel.findViewById(R.id.deckoutlandmark);
                final EditText town = addnewaddresspannel.findViewById(R.id.deckouttown);
                final EditText state = addnewaddresspannel.findViewById(R.id.deckoutstate);

                Button addnewaddress = addnewaddresspannel.findViewById(R.id.deckoutsetaddressbtn);

                addnewaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (username.getText().toString().length() == 0 )
                        {
                            username.setError("is empty");
                            addadresscount+=1;
                        }

                        if (loc.getText().toString().length() == 0 )
                        {
                            loc.setError("is empty");
                            addadresscount+=1;
                        }

                        if (houseno.getText().toString().length() == 0)
                        {
                            houseno.setError("is empty");
                            addadresscount+=1;
                        }

                        if (landmark.getText().toString().length() == 0 )
                        {
                            landmark.setError("is empty");
                            addadresscount+=1;
                        }

                        if (town.getText().toString().length() == 0 )
                        {
                            town.setError("is empty");
                            addadresscount+=1;
                        }

                        if (state.getText().toString().length() == 0 )
                        {
                            state.setError("is empty");
                            addadresscount+=1;
                        }

                        if (pincode.getText().toString().length() == 0 )
                        {
                            pincode.setError("is empty");
                            addadresscount+=1;
                        }

                        if (deliverymobno.getText().toString().length() == 0 )
                        {
                            deliverymobno.setError("is empty");
                            addadresscount+=1;
                        }

                        if (addadresscount == 0)
                        {
                            checkzipcode(username.getText().toString() , houseno.getText().toString() , loc.getText().toString() , landmark.getText().toString() , town.getText().toString() ,
                                    state.getText().toString() , pincode.getText().toString(), deliverymobno.getText().toString());
                        }

                        addadresscount = 0;

                    }
                });


                ImageView changepannelclosebtn = addnewaddresspannel.findViewById(R.id.newaddressdialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addnewaddresspannel.dismiss();
                        addnewaddressbtn.setEnabled(true);
                    }
                });
                addnewaddresspannel.show();

                Window window = addnewaddresspannel.getWindow();
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                window.setGravity(Gravity.CENTER);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }
        });


        changepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepasspannel = new Dialog(Profiledetail.this);
                changepasspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                changepasspannel.getWindow().setWindowAnimations(R.style.animateddialog);
                changepasspannel.setCanceledOnTouchOutside(false);
                changepasspannel.setCancelable(true);
                changepasspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                changepasspannel.setContentView(R.layout.userpasswordsetlayout);
                oldpass = changepasspannel.findViewById(R.id.profileoldpass);
                newpass = changepasspannel.findViewById(R.id.profilenewpass);
                confirmpass = changepasspannel.findViewById(R.id.profileconfirmpass);
                ImageView changepannelclosebtn = changepasspannel.findViewById(R.id.profilechangepassdialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changepasspannel.dismiss();
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
        });


        profileeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog profileeditpannel = new Dialog(Profiledetail.this);
                profileeditpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                profileeditpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                profileeditpannel.setCanceledOnTouchOutside(false);
                profileeditpannel.setCancelable(true);
                profileeditpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                profileeditpannel.setContentView(R.layout.userproupdatelayout);
                final EditText editusername = profileeditpannel.findViewById(R.id.profileeditusername);
                final EditText editemailid = profileeditpannel.findViewById(R.id.profileeditemailid);
                final EditText editmobno = profileeditpannel.findViewById(R.id.profileeditphoneno);

                editusername.setText(usernametxt.getText().toString());
                editemailid.setText(emailidtxt.getText().toString());
                editmobno.setText(phnotxt.getText().toString().replace("+91 ",""));

                Button editsavebtn = profileeditpannel.findViewById(R.id.profileeditdatebutton);
                editsavebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(!userid.equals("#guest")) {
                                setprofiledata(editusername.getText().toString().trim(), editemailid.getText().toString().trim(), editmobno.getText().toString().trim());
                                profileeditpannel.dismiss();
                        }
                    }
                });

                ImageView changepannelclosebtn = profileeditpannel.findViewById(R.id.changeprodialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        profileeditpannel.dismiss();
                    }
                });
                profileeditpannel.show();
                Window window = profileeditpannel.getWindow();
                window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });


    }


    private void  checkzipcode(String usernametxt , String housenotxt , String loctxt , String landmarktxt, String towntxt ,
                               String statetxt ,String pincodetxt , String deliverymobnotxt )
    {

        String zipcodetxt = pincodetxt;

        DatabaseReference deliverynameref = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist");
        deliverynameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(zipcodetxt))
                {
                    String addresstxt =  housenotxt+" , ;"+loctxt+" , ;"+landmarktxt+", ;"+towntxt+"<br><br>;"+statetxt+" - ;"+pincodetxt+" <br><br>Delivery Phone No. - ;"+deliverymobnotxt;
                    setnewaddress(addresstxt);
                    setdeliveryname(usernametxt);
                    setaddresscounter(addresscount+1);
                    addnewaddresspannel.dismiss();
                    addnewaddressbtn.setEnabled(true);
                }

                else
                {
                    pincode.setError("Not Avialable");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = new Bundle();
        String useranme = usernametxt.getText().toString();
        bundle.putString("userid", useranme );
        Profilepic profilefrag =  new Profilepic();
        profilefrag.setArguments(bundle);

        FragmentTransaction profilepictransaction = getSupportFragmentManager().beginTransaction();
        profilepictransaction.add(R.id.profilepiclayout,profilefrag ).commit();

        FragmentTransaction profilepicbgtransaction = getSupportFragmentManager().beginTransaction();
        profilepicbgtransaction.add(R.id.profilebackgroundimageviewlayout, new Profilepicbackground()).commit();


    }



    private void getaddresscount()
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/addresscount");
        addresscount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setAddresscount(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setAddresscount(String count)
    {
        addresscount = Integer.parseInt(count);
    }


    private void setnewaddress(String address)
    {
        final DatabaseReference deliveryaddressref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(addresscount+1));
        deliveryaddressref.child("addresstxt").setValue(address);
    }

    private void setdeliveryname(String name)
    { final DatabaseReference deliverynameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(addresscount+1));
        deliverynameref.child("deliveryname").setValue(name);

    }

    private void setaddresscounter(int count)
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/addresscount");
        addresscount.setValue(count);
    }


    private void getprofiledetails()
    {
        final DatabaseReference usernameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Name");
        usernameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usernametxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference emailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Emailid");
        emailidref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emailidtxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference phnoref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Mobno");
        phnoref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phnotxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void updatepass()
    {
        Drawable erroricon = new Drawablesdata().getdrawablewhite(Profiledetail.this);
        if(oldpass.getText().toString().equals(""))
        {
            oldpass.setError("is Empty" , erroricon);
            passerrorcount+=1;

        }

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

        if(passerrorcount == 0) {

            if (pass.equals(oldpass.getText().toString().trim())) {
                if (newpass.getText().toString().trim().equals(confirmpass.getText().toString().trim())) {
                    passref.setValue(newpass.getText().toString());
                    changepasspannel.dismiss();
                }

                else {
                    confirmpass.setError("not matched" , erroricon);
                }

            }

            else {
                oldpass.setError("Password is Invalid" , erroricon);
            }

        }

        passerrorcount=0;
    }



    private void setprofiledata(String name , String emailid , String phoneno)
    {

        DatabaseReference setusernameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Name");
        setusernameref.setValue(name);


        DatabaseReference setemailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Emailid");
        setemailidref.setValue(emailid);

        DatabaseReference setphonenoref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Mobno");
        setphonenoref.setValue("+91 "+phoneno);


    }


}
