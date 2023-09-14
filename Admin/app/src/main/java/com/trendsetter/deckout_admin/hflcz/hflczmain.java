package com.trendsetter.deckout_admin.hflcz;

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
import java.util.HashMap;
import java.util.Map;

public class hflczmain extends AppCompatActivity {

Spinner dressmanagelistspiner ;

ScrollView helpqueslayout  , faqqueslayout  , legalqueslayout , contactlayout , zipcodelayout , hflquesandanslistlayout  ;

EditText helpquestxt , helpanstxt  , faqquestxt , faqanstxt  , legalquestxt , legalanstxt , contactnotxt  , zipcodetxt , regiontxt , landmarktxt ;
Button helpsubmitbtn  , faqsubmitbtn , legalsubmitbtn ,  contactsubmitbtn  , zipcodesubmitbtn;
ImageView helpimgview;
int imageWidth = 1000 , imageHeight = 1000;
    String channelId;
    RecyclerView quesandanslist , zipcodelist;
    Spinner quesandanstypeselector;
    private NotificationCompat.Builder uploadNotification;
    private NotificationManager notificationManager;
    Uri helpimgurl = null;
    int chkcounter =0  ;
    AlertDialog processalert;;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hflczlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.hflcztoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Help , FAQ , Legal , Contact , Zip code");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        helpqueslayout = findViewById(R.id.helpqueslayout);
        faqqueslayout = findViewById(R.id.faqqueslayout);
        legalqueslayout = findViewById(R.id.legalqueslayout);
        contactlayout = findViewById(R.id.contactlayout);
        zipcodelayout = findViewById(R.id.zipcodelayout);

        zipcodelist = findViewById(R.id.zipcodelist);

        helpqueslayout.setVisibility(View.VISIBLE);

        hflquesandanslistlayout = findViewById(R.id.hflquesandanslistlayout);

        helpquestxt = findViewById(R.id.helpques);
        helpanstxt = findViewById(R.id.helpans);
        helpimgview = findViewById(R.id.helpimg);
        helpsubmitbtn = findViewById(R.id.helpsubmitbtn);


        faqquestxt = findViewById(R.id.faqques);
        faqanstxt = findViewById(R.id.faqans);
        faqsubmitbtn = findViewById(R.id.faqbtn);

        legalquestxt = findViewById(R.id.legalques);
        legalanstxt = findViewById(R.id.legalans);
        legalsubmitbtn = findViewById(R.id.legalbtn);

        contactnotxt = findViewById(R.id.contactno);
        contactsubmitbtn = findViewById(R.id.cotactbtn);

        zipcodetxt = findViewById(R.id.zipcode);
        regiontxt = findViewById(R.id.region);
        landmarktxt = findViewById(R.id.landmark);
        zipcodesubmitbtn = findViewById(R.id.zipcodebtn);


        helpimgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
            }
        });



        helpsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpquestxt.getText().toString().trim().length() == 0)
                {
                    helpquestxt.setError("is Empty");
                    chkcounter+=1;
                }

                if(helpanstxt.getText().toString().trim().length() == 0)
                {
                    helpanstxt.setError("is Empty");
                    chkcounter+=1;
                }

                if(helpimgurl == null)
                {
                   Toast.makeText(hflczmain.this , "Please select Help Image" , Toast.LENGTH_LONG).show();
                    chkcounter+=1;
                }

                if (chkcounter == 0)
                {
                    processdialog();
                    uploadhelp(helpimgurl);
                }

                chkcounter = 0;
            }
        });



        faqsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(faqquestxt.getText().toString().trim().length() == 0)
                {
                    faqquestxt.setError("is Empty");
                    chkcounter+=1;
                }

                if(faqanstxt.getText().toString().trim().length() == 0)
                {
                    faqanstxt.setError("is Empty");
                    chkcounter+=1;
                }
                
                
                if (chkcounter == 0)
                {
                    processdialog();
                    sefaqdata();

                }

                chkcounter = 0;
            }
        });


        legalsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(legalquestxt.getText().toString().trim().length() == 0)
                {
                    legalquestxt.setError("is Empty");
                    chkcounter+=1;
                }

                if(legalanstxt.getText().toString().trim().length() == 0)
                {
                    legalanstxt.setError("is Empty");
                    chkcounter+=1;
                }


                if (chkcounter == 0)
                {
                    processdialog();
                    setlegaldata();
                }

                chkcounter = 0;
            }
        });


        zipcodesubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zipcodetxt.getText().toString().trim().length() == 0)
                {
                    zipcodetxt.setError("is Empty");
                    chkcounter+=1;
                }

                if(landmarktxt.getText().toString().trim().length() == 0)
                {
                    landmarktxt.setError("is Empty");
                    chkcounter+=1;
                }


                if(regiontxt.getText().toString().trim().length() == 0)
                {
                    regiontxt.setError("is Empty");
                    chkcounter+=1;
                }


                if (chkcounter == 0)
                {
                    processdialog();
                    setzipcode();
                }

                chkcounter = 0;
            }
        });



        contactsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactnotxt.getText().toString().trim().length() == 0)
                {
                    contactnotxt.setError("is Empty");
                    chkcounter+=1;
                }

                if (chkcounter == 0)
                {
                    processdialog();
                    setcontact();
                }

                chkcounter = 0;
            }
        });


        try {
            final Bitmap notificicon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            final Intent emptyIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(hflczmain.this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            channelId = "1";
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(channelId, "Upload Profile pic", importance);
                    notificationManager.createNotificationChannel(mChannel);
                }
                uploadNotification = new NotificationCompat.Builder(hflczmain.this, channelId);
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


                uploadNotification = new NotificationCompat.Builder(hflczmain.this)
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



        dressmanagelistspiner = findViewById(R.id.dressmanagelistselector);
        ArrayAdapter<CharSequence> dressmanagelistadapter = new ArrayAdapter<CharSequence>(hflczmain.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.hflcztype));
        dressmanagelistspiner.setAdapter(dressmanagelistadapter);


        dressmanagelistspiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0 :
                        helpqueslayout.setVisibility(View.VISIBLE);
                        faqqueslayout.setVisibility(View.GONE);
                        legalqueslayout.setVisibility(View.GONE);
                        contactlayout.setVisibility(View.GONE);
                        zipcodelayout.setVisibility(View.GONE);
                        hflquesandanslistlayout.setVisibility(View.GONE);
                        zipcodelist.setVisibility(View.GONE);

                        break;


                    case 1 :
                        helpqueslayout.setVisibility(View.GONE);
                        faqqueslayout.setVisibility(View.VISIBLE);
                        legalqueslayout.setVisibility(View.GONE);
                        contactlayout.setVisibility(View.GONE);
                        zipcodelayout.setVisibility(View.GONE);
                        hflquesandanslistlayout.setVisibility(View.GONE);
                        zipcodelist.setVisibility(View.GONE);

                        break;


                    case 2 :
                        helpqueslayout.setVisibility(View.GONE);
                        faqqueslayout.setVisibility(View.GONE);
                        legalqueslayout.setVisibility(View.VISIBLE);
                        contactlayout.setVisibility(View.GONE);
                        zipcodelayout.setVisibility(View.GONE);
                        hflquesandanslistlayout.setVisibility(View.GONE);
                        zipcodelist.setVisibility(View.GONE);

                        break;


                    case 3 :
                        helpqueslayout.setVisibility(View.GONE);
                        faqqueslayout.setVisibility(View.GONE);
                        legalqueslayout.setVisibility(View.GONE);
                        contactlayout.setVisibility(View.VISIBLE);
                        zipcodelayout.setVisibility(View.GONE);
                        hflquesandanslistlayout.setVisibility(View.GONE);
                        zipcodelist.setVisibility(View.GONE);

                        break;

                    case 4 :
                        helpqueslayout.setVisibility(View.GONE);
                        faqqueslayout.setVisibility(View.GONE);
                        legalqueslayout.setVisibility(View.GONE);
                        contactlayout.setVisibility(View.GONE);
                        zipcodelayout.setVisibility(View.VISIBLE);
                        hflquesandanslistlayout.setVisibility(View.GONE);
                        zipcodelist.setVisibility(View.GONE);

                        break;

                    case 5 :
                        helpqueslayout.setVisibility(View.GONE);
                        faqqueslayout.setVisibility(View.GONE);
                        legalqueslayout.setVisibility(View.GONE);
                        contactlayout.setVisibility(View.GONE);
                        zipcodelayout.setVisibility(View.GONE);
                        hflquesandanslistlayout.setVisibility(View.VISIBLE);
                        zipcodelist.setVisibility(View.GONE);
                        quesandanstypeselector = findViewById(R.id.queslistselector);
                        ArrayAdapter<CharSequence> qussandanstypeadapter = new ArrayAdapter<CharSequence>(hflczmain.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.hflqnatype));
                        quesandanstypeselector.setAdapter(qussandanstypeadapter);
                        quesandanslist = findViewById(R.id.quesandanslist);
                        quesandanslist.setNestedScrollingEnabled(false);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(hflczmain.this);
                        quesandanslist.setLayoutManager(layoutManager);
                        sethfllist(quesandanstypeselector.getSelectedItem().toString());

                        quesandanstypeselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                sethfllist(quesandanstypeselector.getSelectedItem().toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;

                    case 6 :
                        helpqueslayout.setVisibility(View.GONE);
                        faqqueslayout.setVisibility(View.GONE);
                        legalqueslayout.setVisibility(View.GONE);
                        contactlayout.setVisibility(View.GONE);
                        zipcodelayout.setVisibility(View.GONE);
                        hflquesandanslistlayout.setVisibility(View.GONE);
                        zipcodelist.setVisibility(View.VISIBLE);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                        zipcodelist.setLayoutManager(staggeredGridLayoutManager);
                        DatabaseReference zipcoderef = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist");
                        Query zipcodequery = zipcoderef;
                        FirebaseRecyclerOptions<zipcodegetdata> zipcodeoptions = new FirebaseRecyclerOptions.Builder<zipcodegetdata>()
                                .setQuery(zipcodequery, zipcodegetdata.class)
                                .build();
                        zipcodeadapter zipcodesadap = new zipcodeadapter(zipcodeoptions , hflczmain.this);
                        zipcodelist.setAdapter(zipcodesadap);
                        zipcodesadap.startListening();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }


    private void sethfllist(String type)
    {
        DatabaseReference hflref = FirebaseDatabase.getInstance().getReference("dataextra/"+type);
        Query hflquery = hflref;
        FirebaseRecyclerOptions<hflgetdata> hfloptions = new FirebaseRecyclerOptions.Builder<hflgetdata>()
                .setQuery(hflquery, hflgetdata.class)
                .build();
        hfladapter hfladap = new hfladapter( hfloptions , quesandanstypeselector.getSelectedItem().toString()  ,hflczmain.this);
        quesandanslist.setAdapter(hfladap);
        hfladap.startListening();
    }
    
    
    private  void processdialog()
    {
        AlertDialog.Builder message = new AlertDialog.Builder(hflczmain.this);
        message.setTitle("Uploading data on process");
        message.setMessage("\nPlease wait for a while");
        message.setCancelable(false);
        processalert = message.create();
        processalert.show();
    }


    private void sethelpdata(String helppicurl)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference helpdataref = database.getReference("dataextra/Help");

        Map<String, String> helpdata = new HashMap<String, String>();

        helpdata.put("Answer",helpanstxt.getText().toString());
        helpdata.put("Imgurl",helppicurl);
        helpdata.put("id" ,helpquestxt.getText().toString() );


        helpdataref.child(helpquestxt.getText().toString()).setValue(helpdata);

        processalert.dismiss();
        AlertDialog.Builder message = new AlertDialog.Builder(hflczmain.this);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nHelp data uploaded Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = message.create();
        alert.show();

        new taskupdater().settaskdata("Cat - Help  " , "#hflc" , "data uploaed Sucessfully.");
    }


    private void sefaqdata()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference faqdataref = database.getReference("dataextra/FAQ");

        Map<String, String> faqdata = new HashMap<String, String>();

        faqdata.put("Answer",faqanstxt.getText().toString());
        faqdata.put("id" , faqquestxt.getText().toString());


        faqdataref.child(faqquestxt.getText().toString()).setValue(faqdata);
        processalert.dismiss();

        AlertDialog.Builder message = new AlertDialog.Builder(hflczmain.this);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nFAQ data uploaded Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = message.create();
        alert.show();


        new taskupdater().settaskdata("Cat - FAQ  " , "#hflc" , "data uploaed Sucessfully.");
    }


    private void setlegaldata()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference legaldataref = database.getReference("dataextra/Legal");

        Map<String, String> legaldata = new HashMap<String, String>();

        legaldata.put("Answer",legalanstxt.getText().toString());
        legaldata.put("id",legalquestxt.getText().toString());

        legaldataref.child(legalquestxt.getText().toString()).setValue(legaldata);

        processalert.dismiss();
        AlertDialog.Builder message = new AlertDialog.Builder(hflczmain.this);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nLegal data uploaded Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = message.create();
        alert.show();

        new taskupdater().settaskdata("Cat - Legal  " , "#hflc" , "data uploaed Sucessfully.");
    }

    private void setcontact()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference helpdataref = database.getReference("deckoutadmin/adminphno");

        helpdataref.setValue(contactnotxt.getText().toString());

        processalert.dismiss();
        AlertDialog.Builder message = new AlertDialog.Builder(hflczmain.this);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nCotact data uploaded Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = message.create();
        alert.show();

        new taskupdater().settaskdata("Cat - Contact " , "#hflc" , "data uploaed Sucessfully.");
    }


    private void setzipcode()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference zipcodedataref = database.getReference("datarecords/Avialablezipcodelist");

        Map<String, String> zipcodedata = new HashMap<String, String>();

        zipcodedata.put("landmark",landmarktxt.getText().toString());
        zipcodedata.put("region",regiontxt.getText().toString());
        zipcodedata.put("zipcode" ,zipcodetxt.getText().toString());


        zipcodedataref.child(zipcodetxt.getText().toString()).setValue(zipcodedata);

        processalert.dismiss();
        AlertDialog.Builder message = new AlertDialog.Builder(hflczmain.this);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nZipcode data uploaded Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert = message.create();
        alert.show();

        new taskupdater().settaskdata("Cat - Zipcode  " , "#hflc" , "data uploaed Sucessfully.");
    }



    private void uploadhelp( Uri picfileuri) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child("help/"+new File(picfileuri.toString()).getName());
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                sethelpdata(imgurl.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                Toast.makeText(hflczmain.this,"Error ! to upload Dress 1 Img.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Dress img1 : "+filepath.getName());
                notificationManager.notify(1, uploadNotification.build());
            }
        });

    }


    private void pickphoto()
    {
        Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, 1);
    }

    private boolean opencropper(final Uri uri, final Bitmap bitmap) {

        Intent intent = new Intent(getApplicationContext(), Imagecropper.class);
        intent.setData(uri);
        startActivityForResult(intent, 2);
        return true;
    }



    private void loadAsync(final Uri uri) {
        DownloadAsync task = new DownloadAsync();
        task.execute(uri);
    }

    class DownloadAsync extends AsyncTask<Uri, Void, Bitmap> implements DialogInterface.OnCancelListener {

        ProgressDialog mProgress;
        private Uri mUri;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgress = new ProgressDialog(hflczmain.this);
            mProgress.setIndeterminate(true);
            mProgress.setCancelable(true);
            mProgress.setMessage("Loading image...");
            mProgress.setOnCancelListener(this);
            mProgress.show();
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            mUri = params[0];

            Bitmap bitmap;

//            while (mImageContainer.getWidth() < 1) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            final int w = mImageContainer.getWidth();
//            Log.d(TAG, "width: " + w);
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
                Toast.makeText(hflczmain.this, "Failed to load image " + mUri, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            Log.i("Cancelled", "onProgressCancel");
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String filePath;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    filePath = getRealPathFromURI_API19(hflczmain.this, data.getData());
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    filePath = getRealPathFromURI_API11to18(hflczmain.this, data.getData());
                } else {
                    filePath = getRealPathFromURI_BelowAPI11(hflczmain.this, data.getData());
                }

                Uri filePathUri = Uri.parse(filePath);
                loadAsync(filePathUri);
            }

            if (requestCode == 2 && resultCode == RESULT_OK) {


                        helpimgview.setImageURI(data.getData());

                        helpimgurl = Uri.fromFile(new File(data.getData().toString()));


            }


        }

        catch (Exception e)
        {

        }
    }


}
