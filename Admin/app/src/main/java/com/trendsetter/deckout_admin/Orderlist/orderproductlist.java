package com.trendsetter.deckout_admin.Orderlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.trendsetter.deckout_admin.Extra.Billgen;
import com.trendsetter.deckout_admin.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class orderproductlist extends AppCompatActivity {

    RecyclerView productlistrecycler;
    String orderid , orderduedate , orderinitdate , custname , custmobno , custdelhaddrs , paymenttype , totalamount;
    Button genratebillbtn ;
    Uri imguri;
    ArrayList<String> productnameslist;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderproductlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myorderproductlisttoolbar);
        setSupportActionBar(toolbar);
        orderid = getIntent().getExtras().getString("orderidcode");
        orderduedate = getIntent().getExtras().getString("orderduedate");
        orderinitdate = getIntent().getExtras().getString("orderinitdate");
        custname = getIntent().getExtras().getString("custname");
        custmobno = getIntent().getExtras().getString("custmobno");
        custdelhaddrs = getIntent().getExtras().getString("custdelhaddrs");
        paymenttype = getIntent().getExtras().getString("paymenttype");
        totalamount = getIntent().getExtras().getString("totalamount");



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ORDER SUB LIST");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        genratebillbtn = findViewById(R.id.billgenbtn);
        productlistrecycler = findViewById(R.id.myorderproductlistrecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(orderproductlist.this);
        productlistrecycler.setLayoutManager(layoutManager);
        DatabaseReference productlistref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Retailer/"+orderid+"/ordersublist");
        Query productlistquery = productlistref;

        final FirebaseRecyclerOptions<orderproductlistgetdata> options = new FirebaseRecyclerOptions.Builder<orderproductlistgetdata>()
                .setQuery(productlistquery, orderproductlistgetdata.class)
                .build();
         orderproductlistadapter itemadapter = new orderproductlistadapter(options , orderproductlist.this);
         productlistrecycler.setAdapter(itemadapter);
         itemadapter.startListening();


        genratebillbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              new genbills().execute();
            }
        });
    }

    private void getitemdata()
    {
        productnameslist = new ArrayList<>();
        productnameslist.clear();
        DatabaseReference productlistref = FirebaseDatabase.getInstance().getReference("orderdetaillist/"+orderid+"/ordersublist");
        productlistref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    setProductlistdata(dataSnapshot1.child("productname").getValue().toString()+";"+dataSnapshot1.child("productprice").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    private void setProductlistdata(String data)
    {
        productnameslist.add(data);
    }


    public void genrateqrcode(String codetxt)
    {
        String path = Environment.getExternalStorageDirectory().toString();
        FileOutputStream fOutputStream = null;
        File file = new File(Environment.getExternalStoragePublicDirectory("Deckoutadmin"), "");
        if (!file.exists()) {
            file.mkdirs();
        }

        try {

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(codetxt, BarcodeFormat.QR_CODE, 200, 200);
            File imgfile = new File(file , "qrcode.png");
            fOutputStream = new FileOutputStream(imgfile);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutputStream);

            MediaScannerConnection.scanFile(orderproductlist.this, new String[]{imgfile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                   runOnUiThread(() -> Toast.makeText(orderproductlist.this, "Opening bills Details", Toast.LENGTH_SHORT).show());
                }
            });


            imguri =  Uri.parse(imgfile.getPath());

            fOutputStream.flush();
            fOutputStream.close();

            ArrayList<String> productnames = this.productnameslist;

            startActivity(new Intent(orderproductlist.this, Billgen.class).putExtra("qrimgurl" , imguri.toString())
                    .putExtra("orderidcode" , orderid).putExtra("productnames", productnames)
                    .putExtra("orderduedate" , orderduedate).putExtra("orderinitdate" , orderinitdate)
                    .putExtra("custname" , custname).putExtra("custmobno" , custmobno)
                    .putExtra("custdelhaddrs" , custdelhaddrs).putExtra("paymenttype" , paymenttype)
                    .putExtra("totalamount" , totalamount));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(orderproductlist.this, "Save Failed", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(orderproductlist.this, "Save Failed ", Toast.LENGTH_SHORT).show();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



private  class genbills extends AsyncTask
{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getitemdata();
        Toast.makeText(orderproductlist.this , "Gathering data Please Wait ..." , Toast.LENGTH_SHORT).show();
        genratebillbtn.setEnabled(false);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
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
        genrateqrcode("DOQR;"+orderid);
        genratebillbtn.setEnabled(true);
    }
}

}
