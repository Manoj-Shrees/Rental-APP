package com.trendsetter.deckout_admin.Dressmanage;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import com.trendsetter.deckout_admin.Uploaddressdetails.uploaddressuserlistadapter;
import com.trendsetter.deckout_admin.Uploaddressdetails.uploaddressuserlistdata;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

public  class productlistitemadapter extends FirebaseRecyclerAdapter<productlistitemgetdata , productlistiitemholder>  implements OnIntentReceived
{
    SharedPreferences sp;
    private Context context;
    String userid ;
    int imageWidth = 1000 , imageHeight = 1000;
    Uri dressimg1uri = null , dressimg2uri = null, dressimg3uri = null, dressimg4uri= null ;
    int dressimgselectorpos =0 ,  setdatacounter= 0  ,selectpos = 0;
    String channelId , retailerid = null ,   retailernametxt = null;;
    Dialog retailerselectdpannel , dressdetailseditpannel;
    ArrayList<String> picurllist;
    private NotificationCompat.Builder uploadNotification;
    private NotificationManager notificationManager;
    EditText dressnametxt , dressdescrptxt , dresspricetxt , dress1titletxt , dress2titletxt , dress3titletxt;
    ImageView dressimg1selector , dressimg2selector , dressimg3selector , dressimg4selector ;
    LinearLayout dressclothinglayout;
    Button submitbtn ;
    AlertDialog editdressdialog;
    TextView selectretailerbtn;
    CheckBox xschkbox , schkbox , mchkbox , lchkbox , xlchkbox , xxlchkbox ;
    Spinner dresstype , dressparenttype , dresscattype , dresspairquant , dress1selector , dress2selector , dress3selector , dresssubparenttype , dressnewtype;

    public productlistitemadapter(@NonNull FirebaseRecyclerOptions<productlistitemgetdata> options , Context context ) {
        super(options);
        this.context = context;
        sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
    }

    @Override
    public productlistiitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dressmanageorderlistitem, parent, false);

        return  new productlistiitemholder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull productlistiitemholder holder, int position, @NonNull final productlistitemgetdata model) {
        holder.setimage(model.getImgurl1());
        holder.setname(model.getName());
        holder.setprice(model.getPrice());
        holder.setid(model.getProduct_id());
       holder.editbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        opendressdetailseditdialog(model.getName() , model.getDescription() , model.getPrice() ,model.getProduct_type()
                , model.getProductparent_type() , model.getSize_available() , model.getRetailername() , model.getRetailercode()
                , model.getProduct_id(), model.getDress_sizetitle() , model.getDress_code()
                , model.getNo_of_views() , model.getProduct_likes() , model.getTotal_ratings()
                , model.getImgurl1() , model.getImgurl2() , model.getImgurl3() , model.getImgurl4() , model.getProductsubparenttype() , model.getChecknew() );
    }
     });

        try {
            final Bitmap notificicon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            final Intent emptyIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            channelId = "1";
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(channelId, "Uploading files", importance);
                    notificationManager.createNotificationChannel(mChannel);
                }
                uploadNotification = new NotificationCompat.Builder(context, channelId);
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


                uploadNotification = new NotificationCompat.Builder(context)
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





       holder.addtohomepagebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               opendressposdialog(model.getImgurl1() , model.getProductparent_type().replace("#",""));
           }
       });
        
    }



    private int setproducttype(String producttype)
    {
        int pos = 0;

        if(producttype.equals("#Topwear"))
            pos = 0;

        if(producttype.equals("#Bottomwear"))
            pos = 1;

        if(producttype.equals("#Headwear"))
            pos = 2;

        if(producttype.equals("#Footwear"))
            pos = 3;

        if(producttype.equals("#Bellywear"))
            pos = 4;

        if(producttype.equals("#Others"))
            pos = 5;

        return pos;
    }


    private int setproductparenttype(String productparenttype)
    {
        int pos = 0;

        if(productparenttype.equals("#Classic"))
            pos = 0;

        if(productparenttype.equals("#Fancy"))
            pos = 1;

        if(productparenttype.equals("#State"))
            pos = 2;

        if(productparenttype.equals("#Western"))
            pos = 3;

        return pos;
    }


    private void setcehckbox(String sizeavail , String  dresssizetitle , String dresscode)
    {

        if (sizeavail.equals("#Ascr;"))
        {
            dressclothinglayout.setVisibility(View.GONE);
            dresscattype.setSelection(1);
            dress1selector.setAdapter(null);
            dress2selector.setAdapter(null);
            dress3selector.setAdapter(null);
        }

        else
            {
            sizeavail = sizeavail.replace("#", "").trim();
            dresscattype.setSelection(0);
            setdresspair(dresssizetitle , dresscode);
            String[] words = sizeavail.split(";");
            for (String w : words) {
                switch (w) {
                    case "XS":
                        xschkbox.setChecked(true);
                        break;

                    case "S":
                        schkbox.setChecked(true);
                        break;

                    case "M":
                        mchkbox.setChecked(true);
                        break;

                    case "L":
                        lchkbox.setChecked(true);
                        break;

                    case "XL":
                        xlchkbox.setChecked(true);
                        break;


                    case "XXL":
                        xxlchkbox.setChecked(true);
                        break;
                }

            }

        }
    }


    private void setdresspair(String dresssizetitle , String dresscode)
    {
         int dresspaircount = 0 ;

        String[] wordressizetitle = dresssizetitle.split(";");
        for (String w : wordressizetitle) {

            switch (dresspaircount) {
                case 0:
                    dress1titletxt.setText(w);
                    dresspaircount = 0;
                    break;

                case 1:
                    dress2titletxt.setText(w);
                    dresspaircount = 1;
                    break;

                case 2:
                    dress3titletxt.setText(w);
                    dresspaircount = 2;
                    break;

            }

        }


            String[] wordresscode = dresscode.split(";");
            for (String c : wordresscode) {

                switch (dresspaircount) {
                    case 0:
                        dress1selector.setSelection(getdresscodepos(c));
                        break;

                    case 1:
                        dress2selector.setSelection(getdresscodepos(c));
                        break;

                    case 2:
                        dress2selector.setSelection(getdresscodepos(c));
                        break;

                }

            }


            dresspairquant.setSelection(dresspaircount);





    }


    private int getdresscodepos(String dresscode)
    {

        int pos = 0;
        String[] wordresscode  =  dresscode.replace("#" , "").trim().split(";");
        for (String w : wordresscode) {
            switch (w) {

                case "KPant":
                    pos = 0;
                    break;

                case "KFrock":
                    pos = 1;
                    break;

                case "KTshirt":
                    pos = 2;
                    break;

                case "KShirt":
                    pos = 3;
                    break;

                case "KJump Suit":
                    pos = 4;
                    break;


                case "MPant":
                    pos = 5;
                    break;

                case "MShirt":
                    pos = 6;
                    break;

                case "WPant":
                    pos = 7;
                    break;

                case "WFrock":
                    pos = 8;
                    break;


                case "WTshirt":
                    pos = 9;
                    break;


                case "WShirt":
                    pos = 10;
                    break;

                case "WJump Suit":
                    pos = 11;
                    break;
            }

        }


        return pos;
    }



    
    
    private  void opendressdetailseditdialog(String dressname  , String dressdresc , String dressprice , String producttype
            , String productparent_type , String sizetxt , String retailernametxt , String retailerid , String productid
            , String dress_sizetitle , String dresscode  , String no_of_viewstxt , String product_likestxt
            , String total_ratingstxt , String pic1url , String pic2url , String pic3url , String pic4url , String subparenttype , String newoldtype)
    {


        picurllist = new ArrayList<>();
        picurllist.clear();
        this.retailerid = retailerid;
        this.retailernametxt = retailernametxt;

        dressdetailseditpannel = new Dialog(context);
        dressdetailseditpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dressdetailseditpannel.getWindow().setWindowAnimations(R.style.animateddialog);
        dressdetailseditpannel.setCanceledOnTouchOutside(false);
        dressdetailseditpannel.setCancelable(true);
        dressdetailseditpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dressdetailseditpannel.setContentView(R.layout.dressdetailseditlayout);

        dressnametxt = dressdetailseditpannel.findViewById(R.id.dressname);
        dressnametxt.setText(dressname);
        dressdescrptxt = dressdetailseditpannel.findViewById(R.id.dressdescrp);
        dressdescrptxt.setText(dressdresc);
        dresspricetxt = dressdetailseditpannel.findViewById(R.id.dressprice);
        dresspricetxt.setText(dressprice);

        selectretailerbtn = dressdetailseditpannel.findViewById(R.id.selectdressbtn);
        selectretailerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openselctretailerselectdialog();
            }
        });

        selectretailerbtn.setText("Selected Retailer Details:\n\nUser ID : "+retailerid+"\nUser Name : "+retailernametxt+"\n\nNote : click again for reselect");


        dress1titletxt = dressdetailseditpannel.findViewById(R.id.dress1title);
        dress2titletxt = dressdetailseditpannel.findViewById(R.id.dress2title);
        dress3titletxt = dressdetailseditpannel.findViewById(R.id.dress3title);

        xschkbox = dressdetailseditpannel.findViewById(R.id.xscb);
        schkbox = dressdetailseditpannel.findViewById(R.id.scb);
        mchkbox = dressdetailseditpannel.findViewById(R.id.mcb);
        lchkbox = dressdetailseditpannel.findViewById(R.id.lcb);
        xlchkbox = dressdetailseditpannel.findViewById(R.id.xlcb);
        xxlchkbox = dressdetailseditpannel.findViewById(R.id.xxlcb);

         dressimg1selector = dressdetailseditpannel.findViewById(R.id.dressimg1selector);
         dressimg2selector = dressdetailseditpannel.findViewById(R.id.dressimg2selector);
         dressimg3selector = dressdetailseditpannel.findViewById(R.id.dressimg3selector);
         dressimg4selector = dressdetailseditpannel.findViewById(R.id.dressimg4selector);

        ImageView changepannelclosebtn = dressdetailseditpannel.findViewById(R.id.dresseditdialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dressdetailseditpannel.dismiss();
            }
        });

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


        submitbtn = dressdetailseditpannel.findViewById(R.id.dressuploadbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!xschkbox.isChecked() && !schkbox.isChecked() && !mchkbox.isChecked() && !lchkbox.isChecked() && !xlchkbox.isChecked() && !xxlchkbox.isChecked() )
                {
                    Toast.makeText(context , "please select atleast 1 size" , Toast.LENGTH_LONG).show();
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



                if(dress1titletxt.getText().toString().trim().length() == 0)
                {
                    dress1titletxt.setError("is Empty");
                    setdatacounter+=1;
                }

                if(dress2titletxt.getText().toString().trim().length() == 0 && dresspairquant.getSelectedItemPosition()==1)
                {
                    dress2titletxt.setError("is Empty");
                    setdatacounter+=1;
                }

                if(dress3titletxt.getText().toString().trim().length() == 0 && dresspairquant.getSelectedItemPosition()==2)
                {
                    dress3titletxt.setError("is Empty");
                    setdatacounter+=1;
                }


                if (setdatacounter == 0)
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(context);
                    message.setTitle("Uploading data on process");
                    message.setMessage("\nPlease wait for a while");
                    message.setCancelable(false);
                    editdressdialog = message.create();
                    editdressdialog.show();

                    uploadproductimg1( dressimg1uri, pic1url , pic2url , pic3url , pic4url , productid , no_of_viewstxt , product_likestxt , total_ratingstxt );


                }
                setdatacounter = 0;

            }
        });



        dressclothinglayout = dressdetailseditpannel.findViewById(R.id.clothingdatalayout);
        ArrayAdapter<CharSequence> dresscodelistdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.dresscode));
        RelativeLayout dress2spinnerlayout = dressdetailseditpannel.findViewById(R.id.dress2type);
        RelativeLayout dress3spinnerlayout = dressdetailseditpannel.findViewById(R.id.dress3type);
        dress1selector = dressdetailseditpannel.findViewById(R.id.dress1typeselector);
        setspinnerscrolling(dress1selector);
        dress1selector.setAdapter(dresscodelistdapter);
        dress2selector = dressdetailseditpannel.findViewById(R.id.dress2typeselector);
        dress3selector = dressdetailseditpannel.findViewById(R.id.dress3typeselector);


        dresstype = dressdetailseditpannel.findViewById(R.id.dresstypeselector);
        ArrayAdapter<CharSequence> dresstypeadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.dresstype));
        dresstype.setAdapter(dresstypeadapter);
        dresstype.setSelection(setproducttype(producttype));



        dressparenttype = dressdetailseditpannel.findViewById(R.id.dressparenttypeselector);
        ArrayAdapter<CharSequence>  dresstypeparentadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.dressparenttype));
        dressparenttype.setAdapter(dresstypeparentadapter);
        dressparenttype.setSelection(setproductparenttype(productparent_type));

        dresssubparenttype = dressdetailseditpannel.findViewById(R.id.dresssubparenttypeselector);
        ArrayAdapter<CharSequence>  dresstypesubparentadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.homepagedresssubtype));
        dresssubparenttype.setAdapter(dresstypesubparentadapter);

        dressnewtype = dressdetailseditpannel.findViewById(R.id.dresstypenewselector);
        ArrayAdapter<CharSequence>  dressnewtypeadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.dressnewtype));
        dressnewtype.setAdapter(dressnewtypeadapter);

        dresscattype = dressdetailseditpannel.findViewById(R.id.dressscattypeselector);
        ArrayAdapter<CharSequence>  dresstypecatadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.dresscattype));
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

        dresspairquant = dressdetailseditpannel.findViewById(R.id.dresspairquantselector);
        ArrayAdapter<CharSequence>  dresspairquantadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.dresspairquant));
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


        if (newoldtype.equals("#T"))
        {
            dressnewtype.setSelection(0);
        }

        else
        {
            dressnewtype.setSelection(1);
        }



        setsubparenttype(subparenttype);




        setcehckbox(sizetxt , dress_sizetitle , dresscode);


        dressdetailseditpannel.show();
        Window window = dressdetailseditpannel.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }



    private void setsubparenttype(String type)
{
    switch (type)
    {
        case  "#Kids" :
            dresssubparenttype.setSelection(0);
            break;

        case "#Mens" :
            dresssubparenttype.setSelection(1);
            break;

        case "#Womens" :
            dresssubparenttype.setSelection(2);
            break;


    }
}


    private  void opendressposdialog(String imgurl , String type)
    {


        final Dialog dresspospannel = new Dialog(context);
        dresspospannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dresspospannel.getWindow().setWindowAnimations(R.style.animateddialog);
        dresspospannel.setCanceledOnTouchOutside(false);
        dresspospannel.setCancelable(true);
        dresspospannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dresspospannel.setContentView(R.layout.dressmangehomepageshortcutitemsdialog);




        ImageView changepannelclosebtn = dresspospannel.findViewById(R.id.setpostialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dresspospannel.dismiss();
            }
        });


        TextView shrt1 = dresspospannel.findViewById(R.id.shortcutimg1);
        TextView shrt2 = dresspospannel.findViewById(R.id.shortcutimg2);
        TextView shrt3 = dresspospannel.findViewById(R.id.shortcutimg3);
        TextView shrt4 = dresspospannel.findViewById(R.id.shortcutimg4);
        TextView shrt5 = dresspospannel.findViewById(R.id.shortcutimg5);
        TextView shrt6 = dresspospannel.findViewById(R.id.shortcutimg6);
        TextView shrt7 = dresspospannel.findViewById(R.id.shortcutimg7);
        TextView shrt8 = dresspospannel.findViewById(R.id.shortcutimg8);
        TextView shrt9 = dresspospannel.findViewById(R.id.shortcutimg9);


        shrt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 1;

                shrt1.setBackgroundResource(R.drawable.selectdressposbg);
                shrt1.setTextColor(Color.WHITE);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });


        shrt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 2;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.selectdressposbg);
                shrt2.setTextColor(Color.WHITE);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });



        shrt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpos = 3;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.selectdressposbg);
                shrt3.setTextColor(Color.WHITE);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });


        shrt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 4;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.selectdressposbg);
                shrt4.setTextColor(Color.WHITE);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });


        shrt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 5;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.selectdressposbg);
                shrt5.setTextColor(Color.WHITE);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });


        shrt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 6;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.selectdressposbg);
                shrt6.setTextColor(Color.WHITE);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });


        shrt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 7;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.selectdressposbg);
                shrt7.setTextColor(Color.WHITE);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });


        shrt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 8;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.selectdressposbg);
                shrt8.setTextColor(Color.WHITE);

                shrt9.setBackgroundResource(R.drawable.box);
                shrt9.setTextColor(Color.BLACK);


            }
        });



        shrt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpos = 9;

                shrt1.setBackgroundResource(R.drawable.box);
                shrt1.setTextColor(Color.BLACK);

                shrt2.setBackgroundResource(R.drawable.box);
                shrt2.setTextColor(Color.BLACK);

                shrt3.setBackgroundResource(R.drawable.box);
                shrt3.setTextColor(Color.BLACK);

                shrt4.setBackgroundResource(R.drawable.box);
                shrt4.setTextColor(Color.BLACK);

                shrt5.setBackgroundResource(R.drawable.box);
                shrt5.setTextColor(Color.BLACK);

                shrt6.setBackgroundResource(R.drawable.box);
                shrt6.setTextColor(Color.BLACK);

                shrt7.setBackgroundResource(R.drawable.box);
                shrt7.setTextColor(Color.BLACK);

                shrt8.setBackgroundResource(R.drawable.box);
                shrt8.setTextColor(Color.BLACK);

                shrt9.setBackgroundResource(R.drawable.selectdressposbg);
                shrt9.setTextColor(Color.WHITE);


            }
        });

        Button setposbtn = dresspospannel.findViewById(R.id.dressmagaersetposbtn);

        setposbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectpos == 0)
                {
                    Toast.makeText(context , "Plaese Select Position first" , Toast.LENGTH_LONG).show();
                }

                else
                {
                    setdataatpos(selectpos , type , imgurl );
                    AlertDialog.Builder message = new AlertDialog.Builder(context);
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("\n\nSucessfully added to homepage shortcuts");
                    message.setCancelable(false);
                    message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dresspospannel.dismiss();
                            new taskupdater().settaskdata("Cat - Dress Manage - Homepage Shortcut " , "#mdrs" , "Position updated Sucessfully.");
                        }
                    });
                    alert = message.create();
                    alert.show();
                }

            }
        });


        dresspospannel.show();
        Window window = dresspospannel.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    private  void  setdataatpos(int pos , String type ,String imgurl)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dressposref = database.getReference("datarecords/Homepageshortcutproductlist/"+type+"/img"+pos);
        dressposref.setValue(imgurl);

    }




    private void pickphoto()
    {
        Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.setType("image/*");
        ((Activity) context).startActivityForResult(pickPhoto, 1);
    }

    private boolean opencropper(final Uri uri, final Bitmap bitmap) {

        Intent intent = new Intent(context, Imagecropper.class);
        intent.setData(uri);
        ((Activity) context).startActivityForResult(intent, 2);
        return true;
    }



    private void loadAsync(final Uri uri) {
        DownloadAsync task = new DownloadAsync();
        task.execute(uri);
    }

    @Override
    public void onIntent(Intent data, int resultCode , int requestCode) {

        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String filePath;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    filePath = getRealPathFromURI_API19(context, data.getData());
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    filePath = getRealPathFromURI_API11to18(context, data.getData());
                } else {
                    filePath = getRealPathFromURI_BelowAPI11(context, data.getData());
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

    class DownloadAsync extends AsyncTask<Uri, Void, Bitmap> implements DialogInterface.OnCancelListener {

        ProgressDialog mProgress;
        private Uri mUri;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgress = new ProgressDialog(context);
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
                Toast.makeText(context, "Failed to load image " + mUri, Toast.LENGTH_SHORT).show();
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



    private void setdata(String productidtxt , String no_of_viewstxt , String product_likestxt , String total_ratingstxt)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dressdataref = database.getReference("datarecords/Product/Productdetails/"+productidtxt);

        Map<String, String> dressdata = new HashMap<String, String>();


        dressdata.put("Product_type","#"+dresstype.getSelectedItem().toString().trim());
        dressdata.put("Productparent_type","#"+dressparenttype.getSelectedItem().toString().trim());
        dressdata.put("Productsubparenttype" , "#"+dresssubparenttype.getSelectedItem().toString().trim());
        dressdata.put("name", dressnametxt.getText().toString().trim());
        dressdata.put("description" ,dressdescrptxt.getText().toString().trim());
        dressdata.put("no_of_views" , no_of_viewstxt);
        dressdata.put("price" , dresspricetxt.getText().toString().trim());
        dressdata.put("product_id" , productidtxt);
        dressdata.put("product_likes" ,product_likestxt);
        dressdata.put("total_ratings" ,total_ratingstxt);
        dressdata.put("imgurl1" , picurllist.get(0));
        dressdata.put("imgurl2" , picurllist.get(1));
        dressdata.put("imgurl3" , picurllist.get(2));
        dressdata.put("imgurl4" , picurllist.get(3));
        dressdata.put("retailercode" , retailerid);
        dressdata.put("retailername",retailernametxt);



        if (dressnewtype.getSelectedItemPosition() == 0)
        {
            dressdata.put("checknew" , "#T");
        }

        else
        {
            dressdata.put("checknew" , "#F");
        }



        String sizecodestr = "";
        String dresstitle ="";
        if (dresspairquant.getSelectedItemPosition()==0)
        {
            sizecodestr = "#"+dress1selector.getSelectedItem().toString()+";";
            dresstitle = dress1titletxt.getText().toString()+";";
        }

        if (dresspairquant.getSelectedItemPosition()==1)
        {
            sizecodestr = "#"+dress1selector.getSelectedItem().toString()+";"+"#"+dress2selector.getSelectedItem().toString()+";";
            dresstitle = dress1titletxt.getText().toString()+";"+dress2titletxt.getText().toString()+";";
        }


        if (dresspairquant.getSelectedItemPosition()==2)
        {
            sizecodestr = "#"+dress1selector.getSelectedItem().toString()+";"+"#"+dress2selector.getSelectedItem().toString()+";"+dress3selector.getSelectedItem().toString()+";";
            dresstitle = dress1titletxt.getText().toString()+";"+dress2titletxt.getText().toString()+";"+dress3titletxt.getText().toString()+";";
        }

        dressdata.put("dress_code" ,sizecodestr);
        dressdata.put("dress_sizetitle", dresstitle);



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

        editdressdialog.dismiss();
        AlertDialog.Builder message = new AlertDialog.Builder(context);
        message.setTitle("Message");
        AlertDialog alert;
        message.setMessage("\nDress data Updated Sucessfully");
        message.setCancelable(false);
        message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dressdetailseditpannel.dismiss();
                picurllist.clear();
                dressimg1uri = null;
                dressimg2uri = null;
                dressimg3uri = null;
                dressimg4uri = null;
            }
        });
        alert = message.create();
        alert.show();


        new taskupdater().settaskdata("Cat - Dress Manage - Dress data " , "#mdrs" , "data updated Sucessfully.");



    }


    private void uploadproductimg1( Uri picfileuri , String pic1url , String pic2url  , String pic3url  , String pic4url , String productidtxt , String no_of_viewstxt , String product_likestxt , String total_ratingstxt) {

        if (picfileuri == null)
        {
            addtodressimgurllist(pic1url);
            uploadproductimg2(dressimg2uri  , pic2url  , pic3url , pic4url ,  productidtxt , no_of_viewstxt , product_likestxt , total_ratingstxt);
        }

        else {

            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" productimage/" + productidtxt + "/" + "ic_productimg1.jpg");
            filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(1, uploadNotification.build());
                    addtodressimgurllist(imgurl.toString());
                    uploadproductimg2(dressimg2uri, pic2url , pic3url ,  pic4url  , productidtxt , no_of_viewstxt , product_likestxt , total_ratingstxt);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(1, uploadNotification.build());
                    Toast.makeText(context, "Error ! to upload Dress 1 Img.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Dress img1 : " + filepath.getName());
                    notificationManager.notify(1, uploadNotification.build());
                }
            });

        }

    }


    private void uploadproductimg2( Uri picfileuri , String pic2url , String pic3url , String pic4url ,  String productidtxt , String no_of_viewstxt , String product_likestxt , String total_ratingstxt) {

        if (picfileuri == null)
        {
            addtodressimgurllist(pic2url);
            uploadproductimg3(dressimg3uri , pic3url ,  pic4url ,  productidtxt , no_of_viewstxt , product_likestxt , total_ratingstxt);
        }

        else {
            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" productimage/" + productidtxt+ "/" + "ic_productimg2.jpg");
            filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(2, uploadNotification.build());
                    uploadproductimg3(dressimg3uri , pic3url ,  pic4url , productidtxt , no_of_viewstxt , product_likestxt , total_ratingstxt);
                    addtodressimgurllist(imgurl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(2, uploadNotification.build());
                    Toast.makeText(context, "Error ! to upload Dress 2 Img.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Dress img2 : " + filepath.getName());
                    notificationManager.notify(2, uploadNotification.build());
                }
            });

        }

    }

    private void uploadproductimg3( Uri picfileuri , String pic3url , String pic4url ,  String productidtxt , String no_of_viewstxt , String product_likestxt , String total_ratingstxt ) {

        if (picfileuri == null)
        {
            addtodressimgurllist(pic3url);
            uploadproductimg4(dressimg4uri , pic4url , productidtxt , no_of_viewstxt , product_likestxt , total_ratingstxt);
        }

        else {

            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" productimage/" + productidtxt + "/" + "ic_productimg3.jpg");
            filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(3, uploadNotification.build());
                    uploadproductimg4(dressimg4uri, pic4url , productidtxt , no_of_viewstxt , product_likestxt , total_ratingstxt);
                    addtodressimgurllist(imgurl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(3, uploadNotification.build());
                    Toast.makeText(context, "Error ! to upload Dress 3 Img.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Dress img3 : " + filepath.getName());
                    notificationManager.notify(3, uploadNotification.build());
                }
            });

        }

    }

    private void uploadproductimg4( Uri picfileuri , String pic4url , String productidtxt ,  String no_of_viewstxt , String product_likestxt , String total_ratingstxt) {
        if (picfileuri == null)
        {
            addtodressimgurllist(pic4url);
            setdata(productidtxt ,  no_of_viewstxt ,  product_likestxt ,  total_ratingstxt);
        }
        else {

            StorageReference storageref = FirebaseStorage.getInstance().getReference();
            final StorageReference filepath = storageref.child(" productimage/" + productidtxt + "/" + "ic_productimg4.jpg");
            filepath.putFile(picfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri imgurl = urlTask.getResult();
                    uploadNotification.setContentText("Done");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(4, uploadNotification.build());
                    addtodressimgurllist(imgurl.toString());
                    setdata(productidtxt ,  no_of_viewstxt ,  product_likestxt ,  total_ratingstxt);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadNotification.setContentText("Failed");
                    uploadNotification.setOngoing(false);
                    notificationManager.notify(4, uploadNotification.build());
                    Toast.makeText(context, "Error ! to upload Dress 4 Img . ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                    uploadNotification.setProgress(100, progress.intValue(), false);
                    uploadNotification.setContentInfo(fileinfo);
                    uploadNotification.setContentText("Uploading");
                    uploadNotification.setContentTitle("Dress img4 : " + filepath.getName());
                    notificationManager.notify(4, uploadNotification.build());
                }
            });

        }

    }


    private void openselctretailerselectdialog()
    {

        retailerselectdpannel = new Dialog(context);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        userlistview.setLayoutManager(layoutManager);

        userlistview.setNestedScrollingEnabled(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/Retailer");
        Query query=ref;
        FirebaseRecyclerOptions<uploaddressuserlistdata> options = new FirebaseRecyclerOptions.Builder<uploaddressuserlistdata>()
                .setQuery(query, uploaddressuserlistdata.class)
                .build();

        uploaddressuserlistadapter userlistadapter = new uploaddressuserlistadapter(options,context);
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
        SharedPreferences sharedpreferences = context.getSharedPreferences("deckoutretailerid", Context.MODE_PRIVATE);
        String userid = sharedpreferences.getString("selectedid", null);
        String name = sharedpreferences.getString("selectedname", null);
        if(userid==null)
        {

            Toast.makeText(context, "Retailer Not selected", Toast.LENGTH_SHORT).show();
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