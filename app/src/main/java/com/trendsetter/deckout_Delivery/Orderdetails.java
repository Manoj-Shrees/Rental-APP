package com.trendsetter.deckout_Delivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Orderdetails extends AppCompatActivity {

    String orderid = "";
    TextView ordernametxt  , orderidtxt , custnametxt , custaddrstxt , custphnotxt , paymntstats , ordrdur ;
    RecyclerView ordersubitemlist ;
    ImageView mapbtn ;
    Button setdeliverystatsbtn ;
    AlertDialog processalert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetailslayout);

        orderid = getIntent().getExtras().getString("orderid");


        Toolbar toolbar = (Toolbar) findViewById(R.id.orderdetailstoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Orders Details");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ordernametxt = findViewById(R.id.ordername);
        orderidtxt  = findViewById(R.id.orderid);
        custnametxt = findViewById(R.id.ordercustname);
        custaddrstxt = findViewById(R.id.ordercustaddrs);
        custphnotxt = findViewById(R.id.custphno);
        paymntstats = findViewById(R.id.orderpaymentstatus);
        ordrdur = findViewById(R.id.orderduration);

        setdeliverystatsbtn = findViewById(R.id.deliveredbtn);
        setdeliverystatsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder deleteitemmsg = new android.app.AlertDialog.Builder(Orderdetails.this);
                deleteitemmsg.setTitle("Message");
                deleteitemmsg.setMessage("\nDo you want to set Order as Delivered?");
                deleteitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setdeliverystatus();

                    }
                });
                deleteitemmsg.setNegativeButton("No", null);
                deleteitemmsg.show();


            }
        });



        mapbtn = findViewById(R.id.searchonmapbtn);
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+custaddrstxt.getText().toString()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        ordersubitemlist = findViewById(R.id.orderdetailsproductlist);
        ordersubitemlist.setNestedScrollingEnabled(false);


        checkorderdeliverystatus();

    }


    private  void checkorderdeliverystatus()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference deliverystatusref = database.getReference("orderdetaillist/Client/"+orderid+"/delivered_status");
        deliverystatusref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().equals("N/A"))
                {
                    setdata();
                }

                else
               {

                   AlertDialog.Builder message = new AlertDialog.Builder(Orderdetails.this);
                   message.setTitle("Delivery Status");
                   AlertDialog alert;
                   message.setMessage("\nOrder has been already Delivered on - "+dataSnapshot.getValue());
                   message.setCancelable(false);
                   message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
                       }
                   });
                   alert = message.create();
                   alert.show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setdata()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference productnameref = database.getReference("orderdetaillist/Client/"+orderid+"/name");
        productnameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordernametxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference productidref = database.getReference("orderdetaillist/Client/"+orderid+"/id");
        productidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderidtxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference productcust_nameref = database.getReference("orderdetaillist/Client/"+orderid+"/cust_name");
        productcust_nameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                custnametxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference productcust_addrsref = database.getReference("orderdetaillist/Client/"+orderid+"/cust_addrs");
        productcust_addrsref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                custaddrstxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference productdurationref = database.getReference("orderdetaillist/Client/"+orderid+"/duration");
        productdurationref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordrdur.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference productpaymnt_statref = database.getReference("orderdetaillist/Client/"+orderid+"/paymnt_stat");
        productpaymnt_statref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymntstats.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference productcust_phnoref = database.getReference("orderdetaillist/Client/"+orderid+"/cust_phoneno");
        productcust_phnoref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                custphnotxt.setText("call customer on - "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        DatabaseReference ref = database.getReference("orderdetaillist/Client/"+orderid+"/ordersublist");
        Query query = ref;

        FirebaseRecyclerOptions<orderdetailslistgetdata> options = new FirebaseRecyclerOptions.Builder<orderdetailslistgetdata>()
                .setQuery(query, orderdetailslistgetdata.class)
                .build();
        orderdetailslistadapter  adapter = new orderdetailslistadapter(options);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Orderdetails.this);
        ordersubitemlist.setLayoutManager(layoutManager);
        ordersubitemlist.setAdapter(adapter);
        adapter.startListening();
    }



    private void setdeliverystatus()
    {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference deliveryupdateref = database.getReference("orderdetaillist/Client/"+orderid+"/delivered_status");
        deliveryupdateref.setValue(sdf.format(new Date()));

        gettrackdatesfrmserver(orderid);

        new sendsmsbgtask().execute();


    }




    private  void sendsms(){

        new linktosmsserver().sendsms(Orderdetails.this  ,custphnotxt.getText().toString().replaceAll("[^0-9]", "") , custnametxt.getText().toString());
    }


    public class sendsmsbgtask extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            AlertDialog.Builder message = new AlertDialog.Builder(Orderdetails.this);
            message.setTitle("Processing");
            message.setMessage("\nPlease Wait for a while");
            message.setCancelable(false);
            processalert = message.create();
            processalert.show();
            sendsms();

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
            processalert.dismiss();
            AlertDialog.Builder message = new AlertDialog.Builder(Orderdetails.this);
            message.setTitle("Delivery Status");
            AlertDialog alert;
            message.setMessage("\nOrder has been Updated as Delivered");
            message.setCancelable(false);
            message.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert = message.create();
            alert.show();


        }
    }




    private  void gettrackdatesfrmserver(final String orderidtxt)
    {
        DatabaseReference trackupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Client/"+orderidtxt+"/trackdate");
        trackupdatemsgref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                settrackdates(dataSnapshot.getValue().toString() , orderidtxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






    private ArrayList<String> gettrackdates(String trackdatestxt)
    {
        ArrayList<String> dates = new ArrayList<>();
        dates.clear();
        String datesdata [] = trackdatestxt.split(";");
        for (int pos = 0 ; pos < datesdata.length ; pos++)
        {
            dates.add(datesdata[pos]);
        }

        return dates;
    }


    private void settrackdates(String message , String orderidtxt)
    {
        ArrayList <String> trackdates = gettrackdates(message);
        updatestatuspannel(trackdates.get(0)+";"+trackdates.get(1)+";"+trackdates.get(2)+";Delivered on "+getcurrdate()+";" , orderidtxt);
    }


    private void updatestatuspannel(String message , String orderidtxt)
    {
        DatabaseReference trackupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Client/"+orderidtxt+"/trackdate");
        trackupdatemsgref.setValue(message);
        deliveredorder(orderidtxt);
    }



    public String getcurrdate()
    {
        String date = "";


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        date = sdf.format(new Date());

        return date;
    }


    private  void  deliveredorder(String orderidtxt)
    {
        DatabaseReference trackupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Client/"+orderidtxt+"/isdelivered");
        trackupdatemsgref.setValue("#T");
    }


}
