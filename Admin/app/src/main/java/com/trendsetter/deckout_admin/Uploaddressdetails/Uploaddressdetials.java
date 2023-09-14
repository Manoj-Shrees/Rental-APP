package com.trendsetter.deckout_admin.Uploaddressdetails;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.naver.android.helloyako.imagecrop.util.BitmapLoadUtils;
import com.trendsetter.deckout_admin.Extra.Imagecropper;
import com.trendsetter.deckout_admin.Extra.taskupdater;
import com.trendsetter.deckout_admin.R;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Uploaddressdetials extends AppCompatActivity {

    int imageWidth = 1000 , imageHeight = 1000;
    Uri dressimg1uri = null , dressimg2uri = null, dressimg3uri = null, dressimg4uri= null ;
    int dressimgselectorpos =0 ,  setdatacounter= 0 , productcount;
    String channelId , retailerid = null , retailernametxt = null;
    Dialog retailerselectdpannel;
    ArrayList<String> picurllist;
    private NotificationCompat.Builder uploadNotification;
    private NotificationManager notificationManager;
    EditText dressnametxt , dressdescrptxt , dresspricetxt , dress1titletxt , dress2titletxt , dress3titletxt;
    ImageView dressimg1selector , dressimg2selector , dressimg3selector , dressimg4selector ;
    Button submitbtn ;
    TextView selectretailerbtn;
    CheckBox xschkbox , schkbox , mchkbox , lchkbox , xlchkbox , xxlchkbox ;
    Spinner dresstype , dressparenttype , dresssubparenttype , dressnewtype , dresscattype , dresspairquant , dress1selector , dress2selector , dress3selector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;

        setContentView(R.layout.dressuploadlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.uploaddresssetailstoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Enter Dress Details to upload");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectretailerbtn = findViewById(R.id.selectdressbtn);
        selectretailerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openselctretailerselectdialog();
            }
        });

        picurllist = new ArrayList<>();
        picurllist.clear();

        getproductcount();

        try {
            final Bitmap notificicon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            final Intent emptyIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(Uploaddressdetials.this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            channelId = "1";
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(channelId, "Uploading files", importance);
                    notificationManager.createNotificationChannel(mChannel);
                }
                uploadNotification = new NotificationCompat.Builder(Uploaddressdetials.this, channelId);
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


                uploadNotification = new NotificationCompat.Builder(Uploaddressdetials.this)
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


        dressnametxt = findViewById(R.id.dressname);
        dressdescrptxt = findViewById(R.id.dressdescrp);
        dresspricetxt = findViewById(R.id.dressprice);
        dress1titletxt = findViewById(R.id.dress1title);
        dress2titletxt = findViewById(R.id.dress2title);
        dress3titletxt = findViewById(R.id.dress3title);


        xschkbox = findViewById(R.id.xscb);
        schkbox = findViewById(R.id.scb);
        mchkbox = findViewById(R.id.mcb);
        lchkbox = findViewById(R.id.lcb);
        xlchkbox = findViewById(R.id.xlcb);
        xxlchkbox = findViewById(R.id.xxlcb);




         dressimg1selector = findViewById(R.id.dressimg1selector);
         dressimg2selector = findViewById(R.id.dressimg2selector);
         dressimg3selector = findViewById(R.id.dressimg3selector);
         dressimg4selector = findViewById(R.id.dressimg4selector);

        dressimg1selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=1;
            }
        });

        dressimg2selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=2;
            }
        });


        dressimg3selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=3;
            }
        });


        dressimg4selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto();
                dressimgselectorpos+=4;
            }
        });


        submitbtn = findViewById(R.id.dressuploadbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dressimg1uri == null) {
                    Toast.makeText(Uploaddressdetials.this , "Dress 1 not selected" , Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }

                if (dressimg2uri == null) {
                    Toast.makeText(Uploaddressdetials.this , "Dress 2 not selected" , Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }

                if (dressimg3uri == null) {
                    Toast.makeText(Uploaddressdetials.this , "Dress 3 not selected" , Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }

                if (dressimg4uri == null) {
                    Toast.makeText(Uploaddressdetials.this , "Dress 4 not selected" , Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }


                if (dresscattype.getSelectedItemPosition() == 0 && !xschkbox.isChecked() && !schkbox.isChecked() && !mchkbox.isChecked() && !lchkbox.isChecked() && !xlchkbox.isChecked() && !xxlchkbox.isChecked() )
                {
                    Toast.makeText(Uploaddressdetials.this , "please select atleast 1 size" , Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }


                if(dressnametxt.getText().toString().trim().length() == 0)
                {
                    dressnametxt.setError("is Empty");
                    setdatacounter+=1;
                }

                if(dressdescrptxt.getText().toString().trim().length() == 0)
                {
                    dressdescrptxt.setError("is Empty");
                    setdatacounter+=1;
                }


                if(dresspricetxt.getText().toString().trim().length() == 0)
                {
                    dresspricetxt.setError("is Empty");
                    setdatacounter+=1;
                }


                if (retailerid == null )
                {
                    Toast.makeText(Uploaddressdetials.this  , "Select the Retailer First . " , Toast.LENGTH_LONG).show();
                    setdatacounter+=1;
                }

                if(dresscattype.getSelectedItemPosition() == 0) {

                    if (dress1titletxt.getText().toString().trim().length() == 0) {
                        dress1titletxt.setError("is Empty");
                        setdatacounter += 1;
                    }

                    if (dress2titletxt.getText().toString().trim().length() == 0 && dresspairquant.getSelectedItemPosition() == 1) {
                        dress2titletxt.setError("is Empty");
                        setdatacounter += 1;
                    }

                    if (dress3titletxt.getText().toString().trim().length() == 0 && dresspairquant.getSelectedItemPosition() == 2) {
                        dress3titletxt.setError("is Empty");
                        setdatacounter += 1;
                    }


                }


                if (setdatacounter == 0)
                {

                   uploadproductimg1( dressimg1uri,productcount);

                    AlertDialog.Builder message = new AlertDialog.Builder(Uploaddressdetials.this);
                    message.setTitle("Uploading data on process");
                    AlertDialog alert;
                    message.setMessage("\nPlease wait for a while");
                    message.setCancelable(false);
                    alert = message.create();
                    alert.show();

                }


                setdatacounter = 0;

            }
        });



        LinearLayout dressclothinglayout = findViewById(R.id.clothingdatalayout);
        ArrayAdapter<CharSequence> dresscodelistdapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dresscode));
        EditText dress1titletxt = findViewById(R.id.dress1title);
        EditText dress2titletxt = findViewById(R.id.dress2title);
        EditText dress3titletxt = findViewById(R.id.dress3title);
        RelativeLayout dress2spinnerlayout = findViewById(R.id.dress2type);
        RelativeLayout dress3spinnerlayout = findViewById(R.id.dress3type);
        dress1selector = findViewById(R.id.dress1typeselector);
        setspinnerscrolling(dress1selector);
        dress1selector.setAdapter(dresscodelistdapter);
        dress2selector = findViewById(R.id.dress2typeselector);
        dress3selector = findViewById(R.id.dress3typeselector);



        dresstype = findViewById(R.id.dresstypeselector);
        ArrayAdapter<CharSequence>  dresstypeadapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dresstype));
        dresstype.setAdapter(dresstypeadapter);

        dressnewtype = findViewById(R.id.dresstypenewselector);
        ArrayAdapter<CharSequence>  dressnewtypeadapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dressnewtype));
        dressnewtype.setAdapter(dressnewtypeadapter);


        dressparenttype = findViewById(R.id.dressparenttypeselector);
        ArrayAdapter<CharSequence>  dresstypeparentadapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dressparenttype));
        dressparenttype.setAdapter(dresstypeparentadapter);

        dresssubparenttype = findViewById(R.id.dresssubparenttypeselector);
        ArrayAdapter<CharSequence>  dresstypesubparentadapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.homepagedresssubtype));
        dresssubparenttype.setAdapter(dresstypesubparentadapter);

        dresscattype = findViewById(R.id.dressscattypeselector);
        ArrayAdapter<CharSequence>  dresstypecatadapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dresscattype));
        dresscattype.setAdapter(dresstypecatadapter);
        dresscattype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)
                {
                    dressclothinglayout.setVisibility(View.VISIBLE);
                    setspinnerscrolling(dress1selector);
                    dress1selector.setAdapter(dresscodelistdapter);
                }

                else
                {
                    dressclothinglayout.setVisibility(View.GONE);
                    dress1selector.setAdapter(null);
                    dress2selector.setAdapter(null);
                    dress3selector.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dresspairquant = findViewById(R.id.dresspairquantselector);
        ArrayAdapter<CharSequence>  dresspairquantadapter = new ArrayAdapter<CharSequence>(Uploaddressdetials.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.dresspairquant));
        dresspairquant.setAdapter(dresspairquantadapter);
        dresspairquant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                {
                    dress2titletxt.setVisibility(View.GONE);
                    dress2spinnerlayout.setVisibility(View.GONE);
                    dress3titletxt.setVisibility(View.GONE);
                    dress3spinnerlayout.setVisibility(View.GONE);
                }

                else if(position == 1)
                {
                    dress2titletxt.setVisibility(View.VISIBLE);
                    dress2spinnerlayout.setVisibility(View.VISIBLE);
                    dress3titletxt.setVisibility(View.GONE);
                    dress3spinnerlayout.setVisibility(View.GONE);
                    dress2selector.setAdapter(dresscodelistdapter);
                    dress3selector.setAdapter(null);
                    setspinnerscrolling(dress2selector);
                }


                else
                {
                    dress2titletxt.setVisibility(View.VISIBLE);
                    dress2spinnerlayout.setVisibility(View.VISIBLE);
                    dress3titletxt.setVisibility(View.VISIBLE);
                    dress3spinnerlayout.setVisibility(View.VISIBLE);
                    dress2selector.setAdapter(dresscodelistdapter);
                    dress3selector.setAdapter(dresscodelistdapter);
                    setspinnerscrolling(dress2selector);
                    setspinnerscrolling(dress3selector);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            try {
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    String filePath;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        filePath = getRealPathFromURI_API19(Uploaddressdetials.this, data.getData());
                    } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                        filePath = getRealPathFromURI_API11to18(Uploaddressdetials.this, data.getData());
                    } else {
                        filePath = getRealPathFromURI_BelowAPI11(Uploaddressdetials.this, data.getData());
                    }

                    Uri filePathUri = Uri.parse(filePath);
                    loadAsync(filePathUri);
                }

                if (requestCode == 2 && resultCode == RESULT_OK) {
                   switch (dressimgselectorpos)
                   {
                       case 1:
                           dressimg1selector.setImageURI(data.getData());

                           dressimg1uri = Uri.fromFile(new File(data.getData().toString()));
                           break;

                       case 2 :
                           dressimg2selector.setImageURI(data.getData());
                           dressimg2uri = Uri.fromFile(new File(data.getData().toString()));
                           break;

                       case 3 :
                           dressimg3selector.setImageURI(data.getData());
                           dressimg3uri = Uri.fromFile(new File(data.getData().toString()));
                           break;

                       case 4 :
                           dressimg4selector.setImageURI(data.getData());
                           dressimg4uri =Uri.fromFile(new File(data.getData().toString()));
                           break;


                   }

                    dressimgselectorpos = 0;
                }

                if (data.getData() == null)
                {
                    dressimgselectorpos = 0;
                }

            }

            catch (Exception e)
            {
                dressimgselectorpos = 0;
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

            mProgress = new ProgressDialog(Uploaddressdetials.this);
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
                Toast.makeText(Uploaddressdetials.this, "Failed to load image " + mUri, Toast.LENGTH_SHORT).show();
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


    private void setspinnerscrolling(Spinner spinner)
    {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
    }


    private void addtodressimgurllist(String  imgurl)
    {
        picurllist.add(imgurl);

    }

    private  void getproductcount()
    {
        DatabaseReference productcountref = FirebaseDatabase.getInstance().getReference("datarecords/Product/productcount");
        productcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setproductcount(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setdata(int productcount)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dressdataref = database.getReference("datarecords/Product/Productdetails/PID0"+productcount);

        Map<String, String> dressdata = new HashMap<String, String>();

        dressdata.put("Product_type","#"+dresstype.getSelectedItem().toString().trim());
        dressdata.put("Productparent_type","#"+dressparenttype.getSelectedItem().toString().trim());
        dressdata.put("Productsubparenttype" , "#"+dresssubparenttype.getSelectedItem().toString().trim());
        dressdata.put("name", dressnametxt.getText().toString().trim());
        dressdata.put("description" ,dressdescrptxt.getText().toString().trim());
        dressdata.put("no_of_views" , "0");
        dressdata.put("price" , dresspricetxt.getText().toString().trim());
        dressdata.put("product_id" , "PID0"+productcount);
        dressdata.put("product_likes" , "0");
        dressdata.put("total_ratings" ,"0");
        dressdata.put("imgurl1" , picurllist.get(0));
        dressdata.put("imgurl2" , picurllist.get(1));
        dressdata.put("imgurl3" , picurllist.get(2));
        dressdata.put("imgurl4" , picurllist.get(3));
        dressdata.put("retailercode" , retailerid);
        dressdata.put("retailername",retailernametxt);

            String sizecodestr = "";
            String dresstitle ="";

            if (dressnewtype.getSelectedItemPosition() == 0)
            {
                dressdata.put("checknew" , "#T");
            }

            else
            {
                dressdata.put("checknew" , "#F");
            }

            if (dresscattype.getSelectedItemPosition() == 0) {
                if (dresspairquant.getSelectedItemPosition() == 0) {
                    sizecodestr = "#" + dress1selector.getSelectedItem().toString() + ";";
                    dresstitle = dress1titletxt.getText().toString() + ";";
                }

                if (dresspairquant.getSelectedItemPosition() == 1) {
                    sizecodestr = "#" + dress1selector.getSelectedItem().toString() + ";" + "#" + dress2selector.getSelectedItem().toString() + ";";
                    dresstitle = dress1titletxt.getText().toString() + ";" + dress2titletxt.getText().toString() + ";";
                }


                if (dresspairquant.getSelectedItemPosition() == 2) {
                    sizecodestr = "#" + dress1selector.getSelectedItem().toString() + ";" + "#" + dress2selector.getSelectedItem().toString() + ";" + dress3selector.getSelectedItem().toString() + ";";
                    dresstitle = dress1titletxt.getText().toString() + ";" + dress2titletxt.getText().toString() + ";" + dress3titletxt.getText().toString() + ";";
                }

                dressdata.put("dress_code", sizecodestr);
                dressdata.put("dress_sizetitle", dresstitle);

            }

            else
            {
                dressdata.put("dress_code", "N/A");
                dressdata.put("dress_sizetitle", "N/A");
            }



        if (dresscattype.getSelectedItemPosition() == 1)
        {
            dressdata.put("size_available" ,"#Ascr;");

        }

        else
        {
            String sizedata = "";
            if(xschkbox.isChecked())
            {
                sizedata ="#XS;";
            }

            if(schkbox.isChecked())
            {
                sizedata +="#S;";
            }


            if(mchkbox.isChecked())
            {
                sizedata +="#M;";
            }


            if(lchkbox.isChecked())
            {
                sizedata +="#L;";
            }


            if(xlchkbox.isChecked())
            {
                sizedata +="#XL;";
            }

            if(xxlchkbox.isChecked())
            {
                sizedata +="#XXL;";
            }

            dressdata.put("size_available" ,sizedata);
        }

        dressdataref.setValue(dressdata);

        updateprocustcount(productcount);
        updateproductindex(dressnametxt.getText().toString().trim() ,"PID0"+productcount );


        AlertDialog.Builder message = new AlertDialog.Builder(Uploaddressdetials.this);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nDress Uploaded Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               new taskupdater().settaskdata("Cat - Dress Upload" , "#dru" , "Dress data Uploaded Sucessfully.");
                finish();
            }
        });
        alert = message.create();
        alert.show();



    }



    private void updateproductindex(String productname , String productid) {
        DatabaseReference productindexref = FirebaseDatabase.getInstance().getReference("dataindex/productindex");
        productindexref.child(productname).setValue(productid);
    }


    private  void updateprocustcount(Integer productcount) {

        DatabaseReference productcountref = FirebaseDatabase.getInstance().getReference("datarecords/Product/productcount");
        productcountref.setValue(""+(productcount+1));
    }


    private void setproductcount(Integer productcount)
    {
      this.productcount = productcount;
    }


    private void uploadproductimg1( Uri picfileuri , Integer pcount) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child(" productimage/"+"PID0"+pcount+"/"+"ic_productimg1.jpg");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                uploadproductimg2(dressimg2uri , pcount);
             addtodressimgurllist(imgurl.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                Toast.makeText(Uploaddressdetials.this,"Error ! to upload Dress 1 Img.",Toast.LENGTH_SHORT).show();
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


    private void uploadproductimg2( Uri picfileuri , Integer pcount) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child(" productimage/"+"PID0"+pcount+"/"+"ic_productimg2.jpg");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(2, uploadNotification.build());
                uploadproductimg3(dressimg3uri , pcount);
                addtodressimgurllist(imgurl.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(2, uploadNotification.build());
                Toast.makeText(Uploaddressdetials.this,"Error ! to upload Dress 2 Img.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Dress img2 : "+filepath.getName());
                notificationManager.notify(2, uploadNotification.build());
            }
        });

    }

    private void uploadproductimg3( Uri picfileuri , Integer pcount) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child(" productimage/"+"PID0"+pcount+"/"+"ic_productimg3.jpg");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(3, uploadNotification.build());
                uploadproductimg4(dressimg4uri , pcount);
                addtodressimgurllist(imgurl.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(3, uploadNotification.build());
                Toast.makeText(Uploaddressdetials.this,"Error ! to upload Dress 3 Img.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Dress img3 : "+filepath.getName());
                notificationManager.notify(3, uploadNotification.build());
            }
        });

    }

    private void uploadproductimg4( Uri picfileuri , Integer pcount) {

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child(" productimage/"+"PID0"+pcount+"/"+"ic_productimg4.jpg");
        filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(4, uploadNotification.build());
                addtodressimgurllist(imgurl.toString());
                setdata(pcount);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(4, uploadNotification.build());
                Toast.makeText(Uploaddressdetials.this,"Error ! to upload Dress 4 Img . ",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                uploadNotification.setContentTitle("Dress img4 : "+filepath.getName());
                notificationManager.notify(4, uploadNotification.build());
            }
        });


    }


    private void openselctretailerselectdialog()
    {

        retailerselectdpannel = new Dialog(Uploaddressdetials.this);
        retailerselectdpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retailerselectdpannel.getWindow().setWindowAnimations(R.style.animateddialog);
        retailerselectdpannel.setCanceledOnTouchOutside(false);
        retailerselectdpannel.setCancelable(true);
        retailerselectdpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        retailerselectdpannel.setContentView(R.layout.dressuploadretailerlayout);


        ImageView changepannelclosebtn = retailerselectdpannel.findViewById(R.id.chooseoptiondialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retailerselectdpannel.dismiss();
            }
        });


        Button selectuserbtn = retailerselectdpannel.findViewById(R.id.retailerselectionbtn);
        selectuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkretaileruserid();
            }
        });


        RecyclerView userlistview =  retailerselectdpannel.findViewById(R.id.retailerlist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Uploaddressdetials.this);
        userlistview.setLayoutManager(layoutManager);

        userlistview.setNestedScrollingEnabled(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/Retailer");
        Query query=ref;
        FirebaseRecyclerOptions<uploaddressuserlistdata> options = new FirebaseRecyclerOptions.Builder<uploaddressuserlistdata>()
                .setQuery(query, uploaddressuserlistdata.class)
                .build();

        uploaddressuserlistadapter  userlistadapter = new uploaddressuserlistadapter(options,Uploaddressdetials.this);
        userlistview.setAdapter(userlistadapter);
        userlistadapter.startListening();

        retailerselectdpannel.show();
        Window window = retailerselectdpannel.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }



    private void checkretaileruserid()
    {
        SharedPreferences sharedpreferences = getSharedPreferences("deckoutretailerid", Context.MODE_PRIVATE);
        String userid = sharedpreferences.getString("selectedid", null);
        String name = sharedpreferences.getString("selectedname", null);
        if(userid==null)
        {

            Toast.makeText(Uploaddressdetials.this, "Retailer Not selected", Toast.LENGTH_SHORT).show();
            setdatacounter+=1;
        }

        else
        {
            selectretailerbtn.setText("Selected Retailer Details:\n\nUser ID : "+userid+"\nUser Name : "+name+"\n\nNote : click again for reselect");
            retailerid = userid;
            retailernametxt = name;
            retailerselectdpannel.dismiss();
        }

    }





    }