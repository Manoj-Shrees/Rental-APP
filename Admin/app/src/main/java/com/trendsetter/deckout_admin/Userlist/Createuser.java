package com.trendsetter.deckout_admin.Userlist;

import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.naver.android.helloyako.imagecrop.util.BitmapLoadUtils;
import com.trendsetter.deckout_admin.Extra.Imagecropper;
import com.trendsetter.deckout_admin.Extra.taskupdater;
import com.trendsetter.deckout_admin.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Createuser extends AppCompatActivity {

    Spinner selectusertype ;
    CheckBox showpasschk;
    Button submitbtn;
    EditText usernametxt , userphnotxt , userpasstxt ;
    ImageView userimg;
    int usercount = 0 , checkcount = 0;
    CircleProgressBar signupprogressbar;
    Handler handler = new Handler();
    Runnable runnable;
    Uri profilepicurl = null;
    String uid , usertypecode;
    int imageWidth = 1000 , imageHeight = 1000;
    int finalI = 0 , setdatacounter = 0;
    String channelId;
    ArrayList<String> propicurllist;
    private NotificationCompat.Builder uploadNotification;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuserlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.crateusertoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Create User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        propicurllist = new ArrayList<>();
        propicurllist.clear();


        usernametxt = findViewById(R.id.username);
        userphnotxt = findViewById(R.id.userphno);
        userpasstxt = findViewById(R.id.userpass);

        userimg = findViewById(R.id.profileimsgeselector);

        submitbtn = findViewById(R.id.submitbtn);

        signupprogressbar = findViewById(R.id.signupprogressBar);

        selectusertype = findViewById(R.id.usertypeselector);
        ArrayAdapter<CharSequence> dressmanagelistadapter = new ArrayAdapter<CharSequence>(Createuser.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.createusertype));
        selectusertype.setAdapter(dressmanagelistadapter);

        showpasschk = findViewById(R.id.hideshowpassbtn);

        showpasschk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    userpasstxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

                else
                {
                    userpasstxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getusercount();

                if(usernametxt.getText().toString().trim().length() == 0)
                {
                    usernametxt.setError("is Empty");
                    setdatacounter+=1;
                }


                if(userpasstxt.getText().toString().trim().length() == 0)
                {
                    userpasstxt.setError("is Empty");
                    setdatacounter+=1;
                }

                if(userphnotxt.getText().toString().trim().length() < 10)
                {
                    userphnotxt.setError("Less than 10");
                    setdatacounter+=1;
                }

                if(userphnotxt.getText().toString().trim().length() == 0)

                {
                    userphnotxt.setError("is Empty");
                    setdatacounter+=1;
                }
                if(profilepicurl == null)
                {
                    Toast.makeText(Createuser.this , "Profile pic not selected", Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }


                if(setdatacounter==0)
                {
                    new checkuserdata().execute();

                }

                setdatacounter = 0;
            }
        });


        try {
            final Bitmap notificicon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            final Intent emptyIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(Createuser.this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            channelId = "1";
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(channelId, "Upload Profile pic", importance);
                    notificationManager.createNotificationChannel(mChannel);
                }
                uploadNotification = new NotificationCompat.Builder(Createuser.this, channelId);
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


                uploadNotification = new NotificationCompat.Builder(Createuser.this)
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

    }



    private void registeruser(String loc , String usertypecode , String name , String mobno , String password , String propicurl)
    {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userdataref = database.getReference("datarecords/deckoutusers/"+loc+"/"+usertypecode);

        Map<String, String> userData = new HashMap<String, String>();

        userData.put("Name", name);
        userData.put("Mobno", "+91 "+mobno);
        userData.put("Password", password);
        userData.put("checkblock" , "isfalse");
        userData.put("Profilepic", propicurl);
        userData.put("userid" ,usertypecode);

        userdataref.setValue(userData);

        setusercount();

        usernametxt.setText("");
        userphnotxt.setText("");
        userpasstxt.setText("");
        new taskupdater().settaskdata("Cat - Usercreted" , "#mnc" , usertypecode+" Registered Sucessfully.");
        setdialog(1);
    }




    private void getusercount()
    {
        String loc = selectusertype.getSelectedItem().toString().toLowerCase().replaceAll("\\s+","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userdataref = database.getReference("datarecords/deckoutusers/"+loc+"usercount");
        userdataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setusercounttovar (Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setusercounttovar(int usercount)
    {
        this.usercount = usercount;
    }

    private void setusercount()
    {
        String loc = selectusertype.getSelectedItem().toString().toLowerCase().replaceAll("\\s+","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userdataref = database.getReference("datarecords/deckoutusers/"+loc+"usercount");
        userdataref.setValue(usercount+1);

        setuserindex(usercount , usertypecode);
    }


    private  void setuserindex(int usercount , String usertypecode)
    {

        String loc = selectusertype.getSelectedItem().toString().replaceAll("\\s+","");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference setnameidref = database.getReference("dataindex/userindex/"+loc+"/Name");
        DatabaseReference setphoneidref = database.getReference("dataindex/userindex/"+loc+"/Phoneno");
        uid = usertypecode+usercount;
        setnameidref.child(usernametxt.getText().toString().trim()+"_ID"+usercount).setValue(uid);
        setphoneidref.child(userphnotxt.getText().toString().trim()).setValue(uid);

    }


    private void setdialog(int pos)
    {

        AlertDialog.Builder message = new AlertDialog.Builder(Createuser.this);
        message.setTitle("Message");
        AlertDialog alert ;
        switch (pos)
        {
            case 0 :
                message.setMessage("This Phone No. already Registered please use Another Phone No.");
                message.setCancelable(true);
                message.setNegativeButton("Ok",null);
                alert = message.create();
                alert.show();
                break;


            case 1 :
                message.setMessage("\nUser Registered Sucessfully");
                message.setCancelable(true);
                message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert = message.create();
                alert.setCancelable(false);
                alert.show();
                break;


        }
    }



    private  void  checkuserdetails()
    {
        if(checkcount == 0) {

            String loc = selectusertype.getSelectedItem().toString().replaceAll("\\s+","");
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference checkphoneidref = database.getReference("dataindex/userindex/"+loc+"/Phoneno");

            checkphoneidref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(userphnotxt.getText().toString()).exists()) {
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
                String loc = selectusertype.getSelectedItem().toString().replaceAll("\\s+","");
                if(selectusertype.getSelectedItemPosition()==0)
                    usertypecode = "DODUID";
                else
                    usertypecode = "DORUID";
                uploaduserprofilepic(loc , usertypecode+usercount , profilepicurl);

            }

            if (checkcount == 1)
            {
                setdialog(0);
            }


            checkcount = 0;

            handler.removeCallbacks(runnable);
            signupprogressbar.setVisibility(View.GONE);

        }

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

            mProgress = new ProgressDialog(Createuser.this);
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
                Toast.makeText(Createuser.this, "Failed to load image " + mUri, Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String filePath;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    filePath = getRealPathFromURI_API19(Createuser.this, data.getData());
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    filePath = getRealPathFromURI_API11to18(Createuser.this, data.getData());
                } else {
                    filePath = getRealPathFromURI_BelowAPI11(Createuser.this, data.getData());
                }

                Uri filePathUri = Uri.parse(filePath);
                loadAsync(filePathUri);
            }

            if (requestCode == 2 && resultCode == RESULT_OK) {

                        userimg.setImageURI(data.getData());

                        profilepicurl = Uri.fromFile(new File(data.getData().toString()));


            }


        }

        catch (Exception e)
        {

        }
    }


    private void uploaduserprofilepic(String loc , String usertypecode ,  Uri picfileuri ) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child("Profile/"+loc+"/"+usertypecode+"/profilepic.jpg");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                registeruser(loc , usertypecode , usernametxt.getText().toString(), userphnotxt.getText().toString(), userpasstxt.getText().toString( ), imgurl.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                Toast.makeText(Createuser.this,"Error ! to upload Dress 1 Img.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Profile Pic : "+filepath.getName());
                notificationManager.notify(1, uploadNotification.build());
            }
        });

    }

}
