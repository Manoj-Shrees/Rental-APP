package com.trendsetter.deckout_admin.Dressmanage;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.asksira.bsimagepicker.Utils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.naver.android.helloyako.imagecrop.util.BitmapLoadUtils;
import com.trendsetter.deckout_admin.Extra.Imagecropper;
import com.trendsetter.deckout_admin.Extra.taskupdater;
import com.trendsetter.deckout_admin.R;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dressmange extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener,
        BSImagePicker.ImageLoaderDelegate,
        BSImagePicker.OnSelectImageCancelledListener  {

    int imageWidth = 1000 , imageHeight = 1000 , dressimgselectorpos =0;
    RecyclerView productlist ;
    ScrollView homepagetopadslayout , homepagetopshortcutslayout ,  homepageadsbannerlayout , homepagedressgallerymanagelayout ,  dressgallerylayout, dressexploreoptadsbannerlayout , dressmanageproductlistlayout;
    ImageView homepagetopadsdress1 , homepagetopadsdress2 , homepagetopadsdress3 , homepagetopadsdress4  , homepagetopshortcutstype1 ,  dresshomepageadstype1 , dresshomepageadstype2 , dressgalleryimg , exploreadsimg1 , exploreadsimg2 , exploreadsimg3 , exploreadsimg4 , exploreadsimg5 , exploreadsimg6 , exploreadsimg7;
    Button homepagetopadssubbtn , topshortcutssubbtn ,  dresshomepageadssubbtn , dressgallerysubbtn , exploreoptionsubbtn ;
    Uri homepagetopadsdress1uri = null , homepagetopadsdress2uri = null, homepagetopadsdress3uri = null, homepagetopadsdress4uri= null
            , homepagetopshortcutstypeuri = null , dresshomepageadstype1uri = null , dresshomepageadstype2uri = null  , dressgalleryimguri = null
            , exploreadsimg1uri = null  , exploreadsimg2uri = null , exploreadsimg3uri = null , exploreadsimg4uri = null , exploreadsimg5uri = null
            , exploreadsimg6uri = null , exploreadsimg7uri = null ;
    EditText dresstitletxt ;
    Spinner topshortcutselector , homepageadsbannerselector , homepagedressselector , exploreoptadsselector ;
    String channelId ;
    BSImagePicker singleSelectionPicker;
    private NotificationCompat.Builder uploadNotification;
    private NotificationManager notificationManager;
    private OnIntentReceived dressitemsIntentListener;
    private int requestCode;
    private int resultCode;
    private Intent data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressmanagelayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.managedresstoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Dress Manage");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


         singleSelectionPicker = new BSImagePicker.Builder("com.yourdomain.yourpackage.fileprovider")
                .setMaximumDisplayingImages(24) //Default: Integer.MAX_VALUE. Don't worry about performance :)
                .setSpanCount(3) //Default: 3. This is the number of columns
                .setGridSpacing(Utils.dp2px(2)) //Default: 2dp. Remember to pass in a value in pixel.
                .setPeekHeight(Utils.dp2px(360)) //Default: 360dp. This is the initial height of the dialog.
                .hideCameraTile() //Default: show. Set this if you don't want user to take photo.
                .hideGalleryTile()
                .setTag("A request ID")
                .useFrontCamera() //Default: false. Launching camera by intent has no reliable way to open front camera so this does not always work.
                .build();

        productlist = findViewById(R.id.dressmanageproductlist);
        productlist.setNestedScrollingEnabled(false);

        homepagetopadsdress1 = findViewById(R.id.homepagetopadsdressimg1selector);
        homepagetopadsdress2 = findViewById(R.id.homepagetopadsdressimg2selector);
        homepagetopadsdress3 = findViewById(R.id.homepagetopadsdressimg3selector);
        homepagetopadsdress4 = findViewById(R.id.homepagetopadsdressimg4selector);

        homepagetopshortcutstype1 = findViewById(R.id.homepagetopshortcutstype1selector);

        homepagedressgallerymanagelayout = findViewById(R.id.homepagedressgallerymanagelayout);

        dresshomepageadstype1 = findViewById(R.id.dresshomepageadstype1selector);
        dresshomepageadstype2 = findViewById(R.id.dresshomepageadstype2selector);

        dressgalleryimg = findViewById(R.id.dresshomepagegalleryselector);

        exploreadsimg1 = findViewById(R.id.dressexploreoptionadstypelist1selector);
        exploreadsimg2 = findViewById(R.id.dressexploreoptionadstypelist2selector);
        exploreadsimg3 = findViewById(R.id.dressexploreoptionadstypelist3selector);
        exploreadsimg4 = findViewById(R.id.dressexploreoptionadstypelist4selector);
        exploreadsimg5 = findViewById(R.id.dressexploreoptionadstypelist5selector);
        exploreadsimg6 = findViewById(R.id.dressexploreoptionadstypelist6selector);
        exploreadsimg7 = findViewById(R.id.dressexploreoptionadstypelist7selector);

        dresstitletxt = findViewById(R.id.dressgallerytitletxt);

        homepagetopadsdress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=1;
            }
        });

        homepagetopadsdress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=2;
            }
        });

        homepagetopadsdress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=3;
            }
        });

        homepagetopadsdress4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=4;
            }
        });

        homepagetopshortcutstype1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=5;
            }
        });

        dresshomepageadstype1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=6;
            }
        });

        dresshomepageadstype2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=7;
            }
        });


        dressgalleryimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=8;
            }
        });


        exploreadsimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=9;
            }
        });

        exploreadsimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=10;
            }
        });

        exploreadsimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=11;
            }
        });

        exploreadsimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=12;
            }
        });

        exploreadsimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=13;
            }
        });


        exploreadsimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=14;
            }
        });


        exploreadsimg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=15;
            }
        });



        homepagetopadssubbtn = findViewById(R.id.homepagetopadssubbtn);
        dresshomepageadssubbtn = findViewById(R.id.dresshomepageadssubbtn);
        topshortcutssubbtn = findViewById(R.id.homepagetopshortcutssubbtn);
        dressgallerysubbtn = findViewById(R.id.dressgallerysubbtn);
        exploreoptionsubbtn = findViewById(R.id.dressexploreoptadsbannersubbtn);



        homepagetopadssubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(homepagetopadsdress1uri != null || homepagetopadsdress2uri != null || homepagetopadsdress3uri != null || homepagetopadsdress4uri != null)
                {
                    updatedataprocessdialog();
                    uploadhomepagetopadsdress1(homepagetopadsdress1uri , homepagetopadsdress2uri , homepagetopadsdress3uri , homepagetopadsdress4uri);
                }

                else
                {
                    Toast.makeText(Dressmange.this, "Please Select atleast  1 Image.", Toast.LENGTH_LONG).show();
                }

            }
        });

        topshortcutssubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(homepagetopshortcutstypeuri != null)
                {
                    updatedataprocessdialog();
                    uploadhomepagetopshortcutads(topshortcutselector.getSelectedItem().toString().toLowerCase() , homepagetopshortcutstypeuri);
                }

                else
                {
                    Toast.makeText(Dressmange.this, "Please Select Image.", Toast.LENGTH_LONG).show();
                }
            }
        });

        dresshomepageadssubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homepageadsbannerselector.getSelectedItem().equals("Bottom"))
                {
                    if (dresshomepageadstype1uri != null || dresshomepageadstype2uri != null) {
                        updatedataprocessdialog();
                        uploadhomepagebottomad1(homepageadsbannerselector.getSelectedItem().toString(), dresshomepageadstype1uri, dresshomepageadstype2uri);
                    }

                    else
                    {
                        Toast.makeText(Dressmange.this, "Please Select atleast 1 Image.", Toast.LENGTH_LONG).show();
                    }
                }

                else {

                    if (dresshomepageadstype1uri != null || dresshomepageadstype2uri != null) {
                        updatedataprocessdialog();
                        uploadhomepagesad1(homepageadsbannerselector.getSelectedItem().toString(), dresshomepageadstype1uri, dresshomepageadstype2uri);
                    }

                    else
                    {
                        Toast.makeText(Dressmange.this, "Please Select atleast 1 Image.", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        dressgallerysubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dressgalleryimguri != null  && dresstitletxt.getText().toString().length() != 0)
                {
                    uploadgallery(dressgalleryimguri , dresstitletxt.getText().toString());
                    updatedataprocessdialog();
                }

                 if(dresstitletxt.getText().toString().length() == 0)
                {
                    dresstitletxt.setError("is empty");
                }

                if (dressgalleryimguri == null)
                {
                    Toast.makeText(Dressmange.this , "Select image first to upload" , Toast.LENGTH_LONG).show();
                }

            }
        });

        exploreoptionsubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (exploreadsimg1uri != null || exploreadsimg2uri != null || exploreadsimg3uri != null || exploreadsimg4uri != null ||
                        exploreadsimg5uri != null || exploreadsimg6uri != null || exploreadsimg7uri != null)
                {
                   uploadexploreadsimg1(exploreoptadsselector.getSelectedItem().toString() , exploreadsimg1uri , exploreadsimg2uri
                           , exploreadsimg3uri , exploreadsimg4uri , exploreadsimg5uri , exploreadsimg6uri , exploreadsimg7uri );
                   updatedataprocessdialog();
                }


                else
                {
                    Toast.makeText(Dressmange.this, "Please Select atleast 1 Image.", Toast.LENGTH_LONG).show();
                }

            }
        });


        try {
            final Bitmap notificicon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            final Intent emptyIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(Dressmange.this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            channelId = "1";
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(channelId, "Uploading files", importance);
                    notificationManager.createNotificationChannel(mChannel);
                }
                uploadNotification = new NotificationCompat.Builder(Dressmange.this, channelId);
                uploadNotification.setContentTitle("Filename")                            // required
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(notificicon)
                        .setContentText("uploading")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setTicker("processing");
            }

            else {


                uploadNotification = new NotificationCompat.Builder(Dressmange.this)
                        .setProgress(0, 0, true)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Filename")
                        .setContentText("processing")
                        .setLargeIcon(notificicon)
                        .setContentIntent(pendingIntent)
                        .setOngoing(true)
                        .setColor(Color.parseColor("#c4ef0404"))
                        .setContentInfo("counting");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        homepagetopadslayout = findViewById(R.id.homepagetopadslayout);
        homepagetopshortcutslayout = findViewById(R.id.homepagetopshortcutslayout);
        homepageadsbannerlayout  = findViewById(R.id.homepageadsbannerlayout);
        dressgallerylayout = findViewById(R.id.dressgallerylayout);
        dressexploreoptadsbannerlayout = findViewById(R.id.dressexploreoptadsbannerlayout);
        dressmanageproductlistlayout = findViewById(R.id.dressmanageproductlistlayout);

        Spinner dressmanagelistselector = findViewById(R.id.dressmanagelistselector);
        ArrayAdapter<CharSequence> dressmanagelistadapter = new ArrayAdapter<CharSequence>(Dressmange.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dressmanagetypelist));
        dressmanagelistselector.setAdapter(dressmanagelistadapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        dressmanagelistselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        homepagetopadslayout.setVisibility(View.VISIBLE);
                        homepagetopshortcutslayout.setVisibility(View.GONE);
                        homepageadsbannerlayout.setVisibility(View.GONE);
                        dressgallerylayout.setVisibility(View.GONE);
                        homepagedressgallerymanagelayout.setVisibility(View.GONE);
                        dressexploreoptadsbannerlayout.setVisibility(View.GONE);
                        dressmanageproductlistlayout.setVisibility(View.GONE);
                        dressitemsIntentListener = null;
                        break;


                    case 1 :

                        homepagetopadslayout.setVisibility(View.GONE);
                        homepagetopshortcutslayout.setVisibility(View.VISIBLE);
                        homepageadsbannerlayout.setVisibility(View.GONE);
                        dressgallerylayout.setVisibility(View.GONE);
                        homepagedressgallerymanagelayout.setVisibility(View.GONE);
                        dressexploreoptadsbannerlayout.setVisibility(View.GONE);
                        dressmanageproductlistlayout.setVisibility(View.GONE);
                        topshortcutselector = findViewById(R.id.homepagetopshortcutstypelistselector);
                        ArrayAdapter<CharSequence> topshortcutsadapter = new ArrayAdapter<CharSequence>(Dressmange.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.exploreadsbanner));
                        topshortcutselector.setAdapter(topshortcutsadapter);
                        dressitemsIntentListener = null;
                        break;


                    case 2:
                        homepagetopadslayout.setVisibility(View.GONE);
                        homepagetopshortcutslayout.setVisibility(View.GONE);
                        homepageadsbannerlayout.setVisibility(View.VISIBLE);
                        dressgallerylayout.setVisibility(View.GONE);
                        homepagedressgallerymanagelayout.setVisibility(View.GONE);
                        dressexploreoptadsbannerlayout.setVisibility(View.GONE);
                        dressmanageproductlistlayout.setVisibility(View.GONE);
                        homepageadsbannerselector = findViewById(R.id.dresshomepageadstypelistselector);
                        ArrayAdapter<CharSequence> homepageadsbanneradapter = new ArrayAdapter<CharSequence>(Dressmange.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.homepageadsbanner));
                        homepageadsbannerselector.setAdapter(homepageadsbanneradapter);
                        dressitemsIntentListener = null;
                        break;

                    case 3:
                        homepagetopadslayout.setVisibility(View.GONE);
                        homepagetopshortcutslayout.setVisibility(View.GONE);
                        homepageadsbannerlayout.setVisibility(View.GONE);
                        dressgallerylayout.setVisibility(View.VISIBLE);
                        homepagedressgallerymanagelayout.setVisibility(View.GONE);
                        dressexploreoptadsbannerlayout.setVisibility(View.GONE);
                        dressmanageproductlistlayout.setVisibility(View.GONE);
                        dressitemsIntentListener = null;
                        break;

                    case 4:
                        homepagetopadslayout.setVisibility(View.GONE);
                        homepagetopshortcutslayout.setVisibility(View.GONE);
                        homepageadsbannerlayout.setVisibility(View.GONE);
                        dressgallerylayout.setVisibility(View.GONE);
                        homepagedressgallerymanagelayout.setVisibility(View.VISIBLE);
                        dressexploreoptadsbannerlayout.setVisibility(View.GONE);
                        dressmanageproductlistlayout.setVisibility(View.GONE);
                        RecyclerView dressgalleryview = findViewById(R.id.dressgallerylist);
                        dressgalleryview.setNestedScrollingEnabled(false);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                        dressgalleryview.setLayoutManager(staggeredGridLayoutManager);
                        DatabaseReference ref = database.getReference("datarecords/Homepagegallery");
                        Query query=ref;
                        final FirebaseRecyclerOptions<dressgallerygetdata>[] dressgalleryoptions = new FirebaseRecyclerOptions[]{new FirebaseRecyclerOptions.Builder<dressgallerygetdata>()
                                .setQuery(query, dressgallerygetdata.class)
                                .build()};
                        dressitemsIntentListener = null;
                        dressgalleryadapter dressgalleryadapter = new dressgalleryadapter(dressgalleryoptions[0] , Dressmange.this);
                        dressgalleryview.setAdapter(dressgalleryadapter);
                        dressgalleryadapter.startListening();
                        break;


                    case 5:
                        homepagetopadslayout.setVisibility(View.GONE);
                        homepagetopshortcutslayout.setVisibility(View.GONE);
                        homepageadsbannerlayout.setVisibility(View.GONE);
                        dressgallerylayout.setVisibility(View.GONE);
                        homepagedressgallerymanagelayout.setVisibility(View.GONE);
                        dressexploreoptadsbannerlayout.setVisibility(View.VISIBLE);
                        dressmanageproductlistlayout.setVisibility(View.GONE);
                        exploreoptadsselector = findViewById(R.id.dressexploreoptionadstypelistselector);
                        ArrayAdapter<CharSequence> exploreoptadsadapter = new ArrayAdapter<CharSequence>(Dressmange.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.exploreadsbanner));
                        exploreoptadsselector.setAdapter(exploreoptadsadapter);
                        dressitemsIntentListener = null;
                        break;



                    case 6:
                        homepagetopadslayout.setVisibility(View.GONE);
                        homepagetopshortcutslayout.setVisibility(View.GONE);
                        homepageadsbannerlayout.setVisibility(View.GONE);
                        dressgallerylayout.setVisibility(View.GONE);
                        homepagedressgallerymanagelayout.setVisibility(View.GONE);
                        dressexploreoptadsbannerlayout.setVisibility(View.GONE);
                        dressmanageproductlistlayout.setVisibility(View.VISIBLE);
                        homepagedressselector = findViewById(R.id.dressmanageproductlistselector);
                        ArrayAdapter<CharSequence> homepagedressadapter = new ArrayAdapter<CharSequence>(Dressmange.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.homepagedresstype));
                        homepagedressselector.setAdapter(homepagedressadapter);


                        homepagedressselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position)
                                {
                                    case 0:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                    case 1:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                    case 2:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                    case 3:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                    case 4:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                    case 5:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                    case 6:
                                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());
                                        break;

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        setProductlistdata(database , homepagedressselector.getSelectedItem().toString());


                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setProductlistdata(FirebaseDatabase database , String filtertype)
    {
        DatabaseReference homepagedressref = database.getReference("datarecords/Product/Productdetails");
        Query homepagedressquery = homepagedressref.orderByChild("Productparent_type").equalTo("#"+filtertype);

        final FirebaseRecyclerOptions<productlistitemgetdata>[] options = new FirebaseRecyclerOptions[]{new FirebaseRecyclerOptions.Builder<productlistitemgetdata>()
                .setQuery(homepagedressquery, productlistitemgetdata.class)
                .build()};
        productlistitemadapter adapter = new productlistitemadapter(options[0],Dressmange.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Dressmange.this);
        productlist.setLayoutManager(layoutManager);
        productlist.setAdapter(adapter);
        adapter.startListening();

        dressitemsIntentListener = adapter;
    }


    private void setloc(String uri)
    {
       Log.e(">>loc - " , " - > "+uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setloc("nvhjujhy"+data.getData().toString());

        if (dressitemsIntentListener != null) {
            dressitemsIntentListener.onIntent(data , resultCode , requestCode);

        }


        else {
            try {
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    String filePath;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        filePath = getRealPathFromURI_API19(Dressmange.this, data.getData());
                    } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                        filePath = getRealPathFromURI_API11to18(Dressmange.this, data.getData());
                    } else {
                        filePath = getRealPathFromURI_BelowAPI11(Dressmange.this, data.getData());
                    }

                    Uri filePathUri = Uri.parse(filePath);
                    loadAsync(filePathUri);
                }

                if (requestCode == 2 && resultCode == RESULT_OK) {
                    switch (dressimgselectorpos) {
                        case 1:
                            homepagetopadsdress1.setImageURI(data.getData());
                            homepagetopadsdress1uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 2:
                            homepagetopadsdress2.setImageURI(data.getData());
                            homepagetopadsdress2uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 3:
                            homepagetopadsdress3.setImageURI(data.getData());
                            homepagetopadsdress3uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 4:
                            homepagetopadsdress4.setImageURI(data.getData());
                            homepagetopadsdress4uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 5:
                            homepagetopshortcutstype1.setImageURI(data.getData());
                            homepagetopshortcutstypeuri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 6:
                            dresshomepageadstype1.setImageURI(data.getData());
                            dresshomepageadstype1uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 7:
                            dresshomepageadstype2.setImageURI(data.getData());
                            dresshomepageadstype2uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 8:
                            dressgalleryimg.setImageURI(data.getData());
                            dressgalleryimguri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 9:
                            exploreadsimg1.setImageURI(data.getData());
                            exploreadsimg1uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 10:
                            exploreadsimg2.setImageURI(data.getData());
                            exploreadsimg2uri = Uri.fromFile(new File(data.getData().toString()));
                            break;


                        case 11:
                            exploreadsimg3.setImageURI(data.getData());
                            exploreadsimg3uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 12:
                            exploreadsimg4.setImageURI(data.getData());
                            exploreadsimg4uri = Uri.fromFile(new File(data.getData().toString()));
                            break;


                        case 13:
                            exploreadsimg5.setImageURI(data.getData());
                            exploreadsimg5uri = Uri.fromFile(new File(data.getData().toString()));
                            break;


                        case 14:
                            exploreadsimg6.setImageURI(data.getData());
                            exploreadsimg6uri = Uri.fromFile(new File(data.getData().toString()));
                            break;

                        case 15:
                            exploreadsimg7.setImageURI(data.getData());
                            exploreadsimg7uri = Uri.fromFile(new File(data.getData().toString()));
                            break;


                    }

                    dressimgselectorpos = 0;
                }

                if (data.getData() == null) {
                    dressimgselectorpos = 0;
                }


            } catch (Exception e) {
                dressimgselectorpos = 0;
            }

        }
    }

    private void pickphoto()
    {

        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        startActivityForResult(pickIntent, 1);
    }

    private boolean opencropper(final Uri uri, final Bitmap bitmap) {

        Intent intent = new Intent(getApplicationContext(), Imagecropper.class);
        intent.setData(uri);
        startActivityForResult(intent, 2);
        return true;
    }


    private void loadAsync(final Uri uri) {
        Log.e(">>datafile" , "loc :// "+ uri.getPath().toString());
        DownloadAsync task = new DownloadAsync();
        task.execute(uri);
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {

    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {

    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {

    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {

    }

    class DownloadAsync extends AsyncTask<Uri, Void, Bitmap> implements DialogInterface.OnCancelListener {

        ProgressDialog mProgress;
        private Uri mUri;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgress = new ProgressDialog(Dressmange.this);
            mProgress.setIndeterminate(true);
            mProgress.setCancelable(true);
            mProgress.setMessage("Loading image...");
            mProgress.setOnCancelListener(this);
            mProgress.show();
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            mUri = params[0];

            Log.e(">>" , ">>"+mUri);

            Bitmap bitmap;
            bitmap = BitmapLoadUtils.decode(mUri.toString(), imageWidth, imageHeight, true);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if (mProgress.getWindow() != null) {
                mProgress.dismiss();
            }

            if (result != null) {
                opencropper(mUri, result);
            }
            else {
                Toast.makeText(Dressmange.this, "Failed to load image " + mUri, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null) {
            int columnIndex =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(columnIndex);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int columnIndex
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }



    private void setgallery(String imgurl , String topic)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dressdataref = database.getReference("datarecords/Homepagegallery");

        String id = dressdataref.push().getKey();
        Map<String, String> gallerydata = new HashMap<String, String>();
        gallerydata.put("imgurl" , imgurl);
        gallerydata.put("id" , id);
        gallerydata.put("name" , topic);

        dressdataref.child(id).setValue(gallerydata);

        setprocessedmessage("\n Dress Gallery Uploaded Sucessfully." , "Dress Gallery");
    }



    private void setadsbanner(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Homepageshortcutlist/"+type);
        topshortcutstref.setValue(imgurl);
    }


    private void setExploreadsimg1(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbanner1imgurl");
        topshortcutstref.setValue(imgurl);

    }

    private void setExploreadsimg2(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbanner2imgurl");
        topshortcutstref.setValue(imgurl);

    }


    private void setExploreadsimg3(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbanner3imgurl");
        topshortcutstref.setValue(imgurl);

    }


    private void setExploreadsimg4(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbanner4imgurl");
        topshortcutstref.setValue(imgurl);

    }


    private void setExploresquareadsimg1(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbannersquare1imgurl");
        topshortcutstref.setValue(imgurl);

    }


    private void setExploresquareadsimg2(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbannersquare2imgurl");
        topshortcutstref.setValue(imgurl);
    }

    private void setExploresquareadsimg3(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Exploreitems/"+type+"/adsbannersquare3imgurl");
        topshortcutstref.setValue(imgurl);
    }



     private  void sethomepagesad1(String  type , String url)
     {
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference topshortcutstref = database.getReference("datarecords/Homepageshortcutproductlist/"+type+"/ad1");
         topshortcutstref.setValue(url);
     }

    private  void sethomepagesad2(String  type , String url)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Homepageshortcutproductlist/"+type+"/ad2");
        topshortcutstref.setValue(url);
        setprocessedmessage("\nSucessfully updates Homepage ADS banner" , "Homepage ADS banner");
    }


    private  void sethomepagebottomad1(String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Homepagebottomadslist/bottomads1url");
        topshortcutstref.setValue(imgurl);
    }

    private  void sethomepagebottomad2(String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference topshortcutstref = database.getReference("datarecords/Homepagebottomadslist/bottomads2url");
        topshortcutstref.setValue(imgurl);
    }


    private void sethomepagetopads(String type , String imgurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference homepagetopadsdressref = database.getReference("datarecords/Hompageslideradslist/"+type);
        homepagetopadsdressref.setValue(imgurl);
    }


    private void sethomepagetopshortcutads(String type , String url)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Hompageslideradslistref = database.getReference("datarecords/Homepageshortcutlist/shortcut"+type+"/shortcuturl");
        Hompageslideradslistref.setValue(url);
    }



    private  String getfiledate()
    {
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        return dateFormat.format(date);
    }

    private void uploadgallery(Uri picfileuri , String dresstitle) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child("Homepage/Gallery/IMG_"+getfiledate()+".png");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(0, uploadNotification.build());
                setgallery(imgurl.toString() , dresstitle );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(0, uploadNotification.build());
                Toast.makeText(Dressmange.this,"Error ! to upload Image to Gallery.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Dress Gallery Image : "+filepath.getName());
                notificationManager.notify(0, uploadNotification.build());
            }
        });

    }


    private void uploadexploreadsimg1(String type , Uri picfile1uri , Uri picfile2uri , Uri picfile3uri
            , Uri picfile4uri , Uri picfile5uri ,  Uri picfile6uri , Uri picfile7uri )
    {

        if (picfile1uri == null)
        {
            uploadexploreadsimg2(type , picfile2uri , picfile3uri
                    , picfile4uri , picfile5uri , picfile6uri , picfile7uri );
        }
        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child(" Exploreitemlist/"+type+"/IMG_"+getfiledate()+".png");
        filepath.putFile(picfile1uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(2, uploadNotification.build());
                setExploreadsimg1(type , imgurl.toString());
                uploadexploreadsimg2(type , picfile2uri , picfile3uri, picfile4uri , picfile5uri , picfile6uri , picfile7uri );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(2, uploadNotification.build());
                Toast.makeText(Dressmange.this,"Error ! to upload Image to Explore Ads.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Explore Ads Image : "+filepath.getName());
                notificationManager.notify(2, uploadNotification.build());
            }
        });
    }




    private void uploadexploreadsimg2(String type  , Uri picfile2uri , Uri picfile3uri
            , Uri picfile4uri , Uri picfile5uri ,  Uri picfile6uri , Uri picfile7uri )
    {

        if (picfile2uri == null)
        {
            uploadexploreadsimg3(type , picfile3uri, picfile4uri , picfile5uri , picfile6uri , picfile7uri );
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/" + type + "/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile2uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(3, uploadNotification.build());
                    setExploreadsimg2(type, imgurl.toString());
                    uploadexploreadsimg3(type , picfile3uri, picfile4uri , picfile5uri , picfile6uri , picfile7uri );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(3, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Explore Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("DExplore Ads Image : " + filepath.getName());
                    notificationManager.notify(3, uploadNotification.build());
                }
            });

        }
    }


    private void uploadexploreadsimg3(String type , Uri picfile3uri, Uri picfile4uri , Uri picfile5uri ,  Uri picfile6uri , Uri picfile7uri)
    {

        if (picfile3uri == null)
        {
            uploadexploreadsimg4(type , picfile4uri , picfile5uri , picfile6uri , picfile7uri );
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/" + type + "/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile3uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(4, uploadNotification.build());
                    setExploreadsimg3(type, imgurl.toString());
                    uploadexploreadsimg4(type , picfile4uri , picfile5uri , picfile6uri , picfile7uri );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(4, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Explore Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Ads Image : " + filepath.getName());
                    notificationManager.notify(4, uploadNotification.build());
                }
            });
        }
    }


    private void uploadexploreadsimg4(String type ,Uri picfile4uri , Uri picfile5uri ,  Uri picfile6uri , Uri picfile7uri)
    {
        if (picfile4uri == null)
        {
            uploadExploresquareadsimg1(type  , picfile5uri , picfile6uri , picfile7uri );
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/" + type + "/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile4uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(5, uploadNotification.build());
                    setExploreadsimg4(type, imgurl.toString());
                    uploadExploresquareadsimg1(type, picfile5uri, picfile6uri, picfile7uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(5, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Explore Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Ads Image : " + filepath.getName());
                    notificationManager.notify(5, uploadNotification.build());
                }
            });

        }
    }


    private void uploadExploresquareadsimg1(String type , Uri picfile5uri ,  Uri picfile6uri , Uri picfile7uri)
    {

        if (picfile5uri == null)
        {
            uploadExploresquareadsimg2(type   , picfile6uri , picfile7uri );
        }

        else {

            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/" + type + "/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile5uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(6, uploadNotification.build());
                    setExploresquareadsimg1(type, imgurl.toString());
                    uploadExploresquareadsimg2(type, picfile6uri, picfile7uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(6, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Explore Square Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Square Ads Image : " + filepath.getName());
                    notificationManager.notify(6, uploadNotification.build());
                }
            });

        }
    }



    private void uploadExploresquareadsimg2(String type , Uri picfile6uri , Uri picfile7uri)
    {

        if (picfile6uri == null)
        {
            uploadExploresquareadsimg3(type ,picfile7uri );
        }

        else {


            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/" + type + "/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile6uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(7, uploadNotification.build());
                    setExploresquareadsimg2(type, imgurl.toString());
                    uploadExploresquareadsimg3(type, picfile7uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(7, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Explore Square Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Square Ads Image : " + filepath.getName());
                    notificationManager.notify(7, uploadNotification.build());
                }
            });


        }
    }



    private void uploadExploresquareadsimg3(String type , Uri picfile7uri)
    {
        if (picfile7uri == null)
        {
            setprocessedmessage("\n Sucessfully updated Explore Ads" , "Explore Ads");
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/" + type + "/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile7uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(8, uploadNotification.build());
                    setExploresquareadsimg3(type, imgurl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(8, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Explore Square Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Square Ads Image : " + filepath.getName());
                    notificationManager.notify(8, uploadNotification.build());
                }
            });

        }
    }


    private void uploadhomepagetopshortcutads(String type , Uri picfileuri)
    {
        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child("  Homepage/Topshortcut/ic_"+type.toLowerCase()+".png");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(9, uploadNotification.build());
                sethomepagetopshortcutads(type , imgurl.toString());
                setprocessedmessage("\n Top Shortcuts Updated Sucessfully" , "Top Shortcuts");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(9, uploadNotification.build());
                Toast.makeText(Dressmange.this,"Error ! to upload Image to Explore Square Ads.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Homepage Top shortcuts Image : "+filepath.getName());
                notificationManager.notify(9, uploadNotification.build());
            }
        });
    }


    private void uploadhomepagesad1(String type , Uri picfile1uri , Uri picfile2uri) {

        if (picfile1uri == null)
        {
            uploadhomepagesad2(type , picfile2uri);
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/ic_" + type.toLowerCase() + ".png");
            filepath.putFile(picfile1uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(10, uploadNotification.build());
                    sethomepagesad1(type, imgurl.toString());
                    uploadhomepagesad2(type , picfile2uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(10, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Homepage Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Homepage Ads Image : " + filepath.getName());
                    notificationManager.notify(10, uploadNotification.build());
                }
            });

        }
    }


    private void uploadhomepagesad2(String type , Uri picfileuri)
    {

        if (picfileuri == null) {
              setprocessedmessage("\nSucessfully updates Homepage ADS banner" , "Homepage ADS banner");
        }

        else
        {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/ic_" + type.toLowerCase() + ".png");
            filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(11, uploadNotification.build());
                    sethomepagesad2(type, imgurl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(11, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Homepage Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Homepage Ads Image : " + filepath.getName());
                    notificationManager.notify(11, uploadNotification.build());
                }
            });

        }
    }


    private  void uploadhomepagebottomad1(String type , Uri picfile1uri , Uri picfile2uri)
    {

        if (picfile1uri == null)
        {
            uploadhomepagebottomad2(type , picfile2uri);
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/ic_" + type.toLowerCase() + ".png");
            filepath.putFile(picfile1uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(12, uploadNotification.build());
                    sethomepagebottomad1(imgurl.toString());
                    uploadhomepagebottomad2(type , picfile2uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(12, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Homepage Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Homepage Ads Image : " + filepath.getName());
                    notificationManager.notify(12, uploadNotification.build());
                }
            });
        }
    }

    private  void uploadhomepagebottomad2(String type , Uri picfileuri)
    {
        if (picfileuri == null)
        {
            setprocessedmessage("\n Sucessfully uploaded Bottom Ads" , "Bottom Ads");
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" Exploreitemlist/ic_" + type.toLowerCase() + ".png");
            filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(13,   uploadNotification.build());
                    sethomepagebottomad2(imgurl.toString());
                    setprocessedmessage("/n Sucessfully uploaded Bottom Ads" ,  "Bottom Ads");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(13, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Homepage Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Homepage Ads Image : " + filepath.getName());
                    notificationManager.notify(13, uploadNotification.build());
                }
            });
        }
    }


    private void uploadhomepagetopadsdress1(Uri picfile1uri , Uri picfile2uri , Uri picfile3uri , Uri picfile4uri)
    {

        if (picfile1uri == null)
        {
            uploadhomepagetopadsdress2(picfile2uri , picfile3uri , picfile4uri);
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child("Homepage/Sliderads/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile1uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(14, uploadNotification.build());
                    uploadhomepagetopadsdress2(picfile2uri , picfile3uri , picfile4uri);
                    sethomepagetopads("slideimage1url" , imgurl.toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(14, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Top Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Ads Image : " + filepath.getName());
                    notificationManager.notify(14, uploadNotification.build());
                }
            });

        }

    }


    private void uploadhomepagetopadsdress2(Uri picfile2uri , Uri picfile3uri , Uri picfile4uri)
    {

        if (picfile2uri == null)
        {
            uploadhomepagetopadsdress3(picfile3uri , picfile4uri);
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child("Homepage/Sliderads/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile2uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(15, uploadNotification.build());
                    uploadhomepagetopadsdress3(picfile3uri , picfile4uri);
                    sethomepagetopads("slideimage2url" , imgurl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(15, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Top Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Ads Image : " + filepath.getName());
                    notificationManager.notify(15, uploadNotification.build());
                }
            });

        }

    }


    private void uploadhomepagetopadsdress3(Uri picfile3uri , Uri picfile4uri)
    {

        if (picfile3uri == null)
        {
            uploadhomepagetopadsdress4(picfile4uri);
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child("Homepage/Sliderads/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile3uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(16, uploadNotification.build());
                    uploadhomepagetopadsdress4(picfile4uri);
                    sethomepagetopads("slideimage3url" , imgurl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(16, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Top Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Ads Image : " + filepath.getName());
                    notificationManager.notify(16, uploadNotification.build());
                }
            });

        }

    }

    private void uploadhomepagetopadsdress4(Uri picfile4uri)
    {

        if (picfile4uri == null)
        {
            setprocessedmessage("\n Sucessfully updated top ads sliders." , "top ads sliders");
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child("Homepage/Sliderads/IMG_" + getfiledate() + ".png");
            filepath.putFile(picfile4uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    sethomepagetopads("slideimage4url", imgurl.toString());
                    setprocessedmessage("\n Sucessfully updated top ads sliders.", "top ads sliders.");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(17, uploadNotification.build());
                    Toast.makeText(Dressmange.this, "Error ! to upload Image to Top Ads.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Explore Ads Image : " + filepath.getName());
                    notificationManager.notify(17, uploadNotification.build());
                }
            });

        }

    }



    private void updatedataprocessdialog()
    {
        AlertDialog.Builder processingmessage = new AlertDialog.Builder(Dressmange.this);
        processingmessage.setTitle("Uploading data on process");
        processingmessage.setMessage("\nPlease wait for a while");
        processingmessage.setCancelable(false);
        AlertDialog  processdialog = processingmessage.create();
        processdialog.show();
    }


    private void setprocessedmessage(String message , String tasktype)
    {
        AlertDialog.Builder processedmessage = new AlertDialog.Builder(Dressmange.this);
        processedmessage.setTitle("Message");
        AlertDialog alert;
        processedmessage.setMessage(message);
        processedmessage.setCancelable(false);
        processedmessage.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = processedmessage.create();
        alert.show();

        new taskupdater().settaskdata("Cat - Dress Manage - "+tasktype , "#mdrs" , "data updated Sucessfully.");
    }


}
