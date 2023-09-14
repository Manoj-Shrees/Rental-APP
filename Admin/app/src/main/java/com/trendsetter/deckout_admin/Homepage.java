package com.trendsetter.deckout_admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import com.trendsetter.deckout_admin.Extra.Sendonesignalnotification;
import com.trendsetter.deckout_admin.Feedback.feedback;
import com.trendsetter.deckout_admin.Taskhistory.Taskhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Homepage extends AppCompatActivity {

    RecyclerView homepagerecylerview ;
    Homepageadapter homepageadapter;
    ArrayList<String> itmtitletxt ;
    ArrayList<Integer> itmimg;
    List<String> permission;
    int changepasscount = 0;
    Dialog setchangepasspannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(Homepage.this);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        OneSignal.sendTag("user_id","deckout_admin");
        setContentView(R.layout.homepagelayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.homepagetoolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bottombarbg));
        }

        getadminname();
        getSupportActionBar().setTitle("Loading ...");
        getSupportActionBar().setSubtitle("Welcome");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        TextView title=(TextView )toolbar.getChildAt(0);
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        title.setSelected(true);
        title.setSingleLine(true);

        itmimg = new ArrayList<>();
        itmtitletxt = new ArrayList<>();
        setdatalist();

       /* permission = new ArrayList<>();
        permission.add(EasyPermissionList.READ_EXTERNAL_STORAGE);
        permission.add(EasyPermissionList.WRITE_EXTERNAL_STORAGE);*/

        homepagerecylerview = findViewById(R.id.homepagerecylerview);
        homepagerecylerview.setBackgroundResource(R.drawable.homepagesemicircle);
        StaggeredGridLayoutManager stagaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        homepagerecylerview.setLayoutManager(stagaggeredGridLayoutManager);
        homepageadapter = new Homepageadapter(Homepage.this , itmtitletxt , itmimg);
        homepagerecylerview.setAdapter(homepageadapter);


    }


    private void setdatalist()
    {
        itmimg.add(R.drawable.ic_dress_upload);
        itmimg.add(R.drawable.ic_dress_manage);
        itmimg.add(R.drawable.ic_helpfaqandothers);
        itmimg.add(R.drawable.ic_client_manage);
        itmimg.add(R.drawable.ic_delivery_boy);
        itmimg.add(R.drawable.ic_retailer_setting);
        itmimg.add(R.drawable.ic_order);
        itmimg.add(R.drawable.ic_orderhistory);

        itmtitletxt.add("Dress Upload");
        itmtitletxt.add("Manage Dress");
        itmtitletxt.add("Help , FAQ , Legal , Contact , Zip code");
        itmtitletxt.add("Manage Client");
        itmtitletxt.add("Manage Delivery Boy");
        itmtitletxt.add("Manage Retailer");
        itmtitletxt.add("Orders List");
        itmtitletxt.add("Order History");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homepagemenu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tasklist:

                startActivity(new Intent(Homepage.this , Taskhistory.class));

                break;

            case R.id.action_feedback:
                startActivity(new Intent(Homepage.this , feedback.class));
                break;

            case R.id.action_user:
                final Dialog setnamepannel = new Dialog(Homepage.this);
                setnamepannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                setnamepannel.getWindow().setWindowAnimations(R.style.animateddialog);
                setnamepannel.setCanceledOnTouchOutside(false);
                setnamepannel.setCancelable(true);
                setnamepannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                setnamepannel.setContentView(R.layout.setadminnamelayout);

                EditText adminnametxt = setnamepannel.findViewById(R.id.adminusername);
                Button  adminnamesubbtn = setnamepannel.findViewById(R.id.adminnamesubmitbtn);

                adminnamesubbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adminnametxt.getText().length() == 0)
                        {
                            adminnametxt.setError("is empty");
                        }

                        else
                        {
                            setadminname(adminnametxt.getText().toString());
                            setnamepannel.dismiss();
                        }
                    }
                });



                ImageView changepannelclosebtn = setnamepannel.findViewById(R.id.setadminnamedialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setnamepannel.dismiss();
                    }
                });

                setnamepannel.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                setnamepannel.getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                setnamepannel.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                setnamepannel.show();

                break;

            case R.id.action_changepass :

                setchangepasspannel = new Dialog(Homepage.this);
                setchangepasspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                setchangepasspannel.getWindow().setWindowAnimations(R.style.animateddialog);
                setchangepasspannel.setCanceledOnTouchOutside(false);
                setchangepasspannel.setCancelable(true);
                setchangepasspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                setchangepasspannel.setContentView(R.layout.setusernewpass);
                setchangepasspannel.setCancelable(false);

                TextView useridtxt = setchangepasspannel.findViewById(R.id.changepassuserid);
                TextView userpasstxt = setchangepasspannel.findViewById(R.id.changepassuserpass);
                TextView confirmpasstxt = setchangepasspannel.findViewById(R.id.changepassconfirmpass);


                Spinner userselector = setchangepasspannel.findViewById(R.id.usertypeselector);
                ArrayAdapter<CharSequence> userselectoradapter = new ArrayAdapter<CharSequence>(Homepage.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.createusertype));
                userselector.setAdapter(userselectoradapter);

                ImageView changepassclosebtn = setchangepasspannel.findViewById(R.id.changepassdialogclose);
                changepassclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setchangepasspannel.dismiss();
                    }
                });

                Button updatepass = setchangepasspannel.findViewById(R.id.changepassupdatebtn);
                updatepass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (useridtxt.getText().toString().length() == 0)
                        {
                            useridtxt.setError("is Empty");
                            changepasscount+=1;
                        }

                        if (userpasstxt.getText().toString().length() == 0)
                        {
                            userpasstxt.setError("is Empty");
                            changepasscount+=1;
                        }

                        if (userpasstxt.getText().toString().length() == 0)
                        {
                            userpasstxt.setError("is Empty");
                            changepasscount+=1;
                        }

                        if (!userpasstxt.getText().toString().equals(confirmpasstxt.getText().toString()))
                        {
                            confirmpasstxt.setError("not matched");
                            changepasscount+=1;
                        }

                        if (changepasscount == 0)

                        {
                            setnewpass(useridtxt.getText().toString() , userselector.getSelectedItem().toString() , userpasstxt.getText().toString());
                        }


                        changepasscount = 0 ;


                    }
                });

                setchangepasspannel.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                setchangepasspannel.getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                setchangepasspannel.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                setchangepasspannel.show();

                break;


            case R.id.action_sendnotific :

               Dialog notificpannel = new Dialog(Homepage.this);
                notificpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                notificpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                notificpannel.setCanceledOnTouchOutside(false);
                notificpannel.setCancelable(true);
                notificpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                notificpannel.setContentView(R.layout.sendnotificationlayout);
                notificpannel.setCancelable(false);

                TextView notifictxt = notificpannel.findViewById(R.id.notifictxt);
                ImageView sendnoticbtn = notificpannel.findViewById(R.id.sendnotifictaionbtn);

                sendnoticbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Sendonesignalnotification(Homepage.this).sendNotification(notifictxt.getText().toString());
                    }
                });

                ImageView notificclosebtn = notificpannel.findViewById(R.id.sendnotificdialogclose);
                notificclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notificpannel.dismiss();
                    }
                });

                notificpannel.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                notificpannel.getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                notificpannel.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                notificpannel.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


/*    @Override
    protected void onStart() {
        super.onStart();
        new EasyPermissionInit(Homepage.this, permission);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EasyPermissionConstants.INTENT_CODE:

                if (data != null) {
                    boolean isGotAllPermissions =
                            data.getBooleanExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION, false);

                    if(data.hasExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION)){
                        if (isGotAllPermissions) {
                            *//* Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();*//*
                        } else {
                            *//*  Toast.makeText(this, "All permission not Granted", Toast.LENGTH_SHORT).show();*//*
                        }}

                    // if you want to know which are the denied permissions.
                    if (data.getSerializableExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST) != null) {

                        ArrayList  deniedPermissions = new ArrayList<>();

                        deniedPermissions.addAll((Collection<? extends String>) data.getSerializableExtra(
                                EasyPermissionConstants.DENIED_PERMISSION_LIST));

                        if (deniedPermissions.size() > 0) {
                            for (int i = 0; i < deniedPermissions.size(); i++) {

                                if(EasyPermissionList.READ_EXTERNAL_STORAGE == deniedPermissions.get(i)) {

                                    Toast.makeText(this, "Storage Permission not granted", Toast.LENGTH_SHORT)
                                            .show();

                                }


                                if(EasyPermissionList.CAMERA == deniedPermissions.get(i)) {

                                    Toast.makeText(this, "Storage Permission not granted", Toast.LENGTH_SHORT)
                                            .show();

                                }

                            }
                        }
                    }
                }
        }
    }*/


    private  void getadminname()
    {
        DatabaseReference adminnameref = FirebaseDatabase.getInstance().getReference("deckoutadmin/adminname");
        adminnameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setadminnametotoolbar(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setadminnametotoolbar(String nametxt)
    {
        getSupportActionBar().setTitle(nametxt);
    }

    private void setadminname(String nametxt)
    {
        DatabaseReference adminnameref = FirebaseDatabase.getInstance().getReference("deckoutadmin/adminname");
        adminnameref.setValue(nametxt);
    }


    private void setnewpass(String userid , String usertype , String pass)
    {
        DatabaseReference adminnameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/"+usertype.replace(" " , ""));
        adminnameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userid))
                {
                    adminnameref.child(userid).child("Password").setValue(pass);
                    setchangepasspannel.dismiss();
                }

                else
                {
                   Toast.makeText(Homepage.this , "User ID. not found." , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}