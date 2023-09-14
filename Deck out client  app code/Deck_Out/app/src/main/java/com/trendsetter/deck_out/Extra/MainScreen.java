package com.trendsetter.deck_out.Extra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Homepage.homepage;
import com.trendsetter.deck_out.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainScreen  extends AppCompatActivity
{

    private SharedPreferences sp;
    String userref;
    String logintype;
    SharedPreferences sharedpreferencessliderads , sharedpreferencesmostsearch;
    SharedPreferences.Editor slideradseditor , mostsearcheditor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreenlayout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sp=getSharedPreferences("Login", MODE_PRIVATE);
        userref=sp.getString("userid","");
        logintype = sp.getString("usertype","");

        sharedpreferencessliderads = getSharedPreferences("adslistdata", Context.MODE_PRIVATE);
        slideradseditor = sharedpreferencessliderads.edit();
        sharedpreferencesmostsearch = getSharedPreferences("mostsearchlistdata", Context.MODE_PRIVATE);
        mostsearcheditor = sharedpreferencesmostsearch.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();
       new  setdatacache().execute();

/*        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.trendsetter.deck_out",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
    }




    private class setdatacache extends AsyncTask
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getadslistdatafromserver();
            getmostseracharraylist();
            new Datechecker(MainScreen.this).getdate();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            checklogindata();
           finish();
        }
    }


    private void getadslistdatafromserver()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference adsslider1 = database.getReference("datarecords/Hompageslideradslist/slideimage1url");
        adsslider1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setadsslideroffdata(0,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference adsslider2 = database.getReference("datarecords/Hompageslideradslist/slideimage2url");
        adsslider2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setadsslideroffdata(1,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference adsslider3 = database.getReference("datarecords/Hompageslideradslist/slideimage3url");
        adsslider3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setadsslideroffdata(2,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference adsslider4 = database.getReference("datarecords/Hompageslideradslist/slideimage4url");
        adsslider4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setadsslideroffdata(3,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getmostseracharraylist()
    {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();

        DatabaseReference mostsearchdata1 = database1.getReference("datarecords/Mostsearchlist/category1");
        mostsearchdata1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(0,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata2 = database1.getReference("datarecords/Mostsearchlist/category2");
        mostsearchdata2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(1,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata3 = database1.getReference("datarecords/Mostsearchlist/category3");
        mostsearchdata3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(2,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference mostsearchdata4 = database1.getReference("datarecords/Mostsearchlist/category4");
        mostsearchdata4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(3,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata5 = database1.getReference("datarecords/Mostsearchlist/category5");
        mostsearchdata5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(4,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mostsearchdata6 = database1.getReference("datarecords/Mostsearchlist/category6");
        mostsearchdata6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(5,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mostsearchdata7 = database1.getReference("datarecords/Mostsearchlist/category7");
        mostsearchdata7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(6,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata8 = database1.getReference("datarecords/Mostsearchlist/category8");
        mostsearchdata8.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(7,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata9 = database1.getReference("datarecords/Mostsearchlist/category9");
        mostsearchdata9.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(8,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata10 = database1.getReference("datarecords/Mostsearchlist/category10");
        mostsearchdata10.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(9,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata11 = database1.getReference("datarecords/Mostsearchlist/category11");
        mostsearchdata11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(10,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata12 = database1.getReference("datarecords/Mostsearchlist/category12");
        mostsearchdata12.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(11,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata13 = database1.getReference("datarecords/Mostsearchlist/category13");
        mostsearchdata13.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(12,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata14 = database1.getReference("datarecords/Mostsearchlist/category14");
        mostsearchdata14.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(13,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata15 = database1.getReference("datarecords/Mostsearchlist/category15");
        mostsearchdata15.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(14,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata16 = database1.getReference("datarecords/Mostsearchlist/category16");
        mostsearchdata16.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(15,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata17 = database1.getReference("datarecords/Mostsearchlist/category17");
        mostsearchdata17.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(16,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mostsearchdata18 = database1.getReference("datarecords/Mostsearchlist/category18");
        mostsearchdata18.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(17,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata19 = database1.getReference("datarecords/Mostsearchlist/category19");
        mostsearchdata19.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(18,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mostsearchdata20 = database1.getReference("datarecords/Mostsearchlist/category20");
        mostsearchdata20.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmosetsearchoffdata(19,dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setmosetsearchoffdata(int pos , String data)
    {
        mostsearcheditor.putString("mostsearchlist"+pos,data);
        mostsearcheditor.commit();
    }


    private void setadsslideroffdata(int pos , String data)
    {

        slideradseditor.putString("adslist"+pos,data);
        slideradseditor.commit();
    }

    private void checklogindata()
    {

       if(userref.equals(""))
       {
           if(logintype.equals(""))
           startActivity(new Intent(MainScreen.this , predatabcollector.class));
           else
           startActivity(new Intent(MainScreen.this , homepage.class).putExtra("userid",logintype));
       }

       else
       {
           startActivity(new Intent(MainScreen.this , homepage.class).putExtra("userid",userref));
       }
    }

   /* public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(">>key :  ", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(">>e", "printHashKey()", e);
        } catch (Exception e) {
            Log.e(">>e", "printHashKey()", e);
        }
    }*/
}
