package com.trendsetter.deck_out.Cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.RevealAnimation;
import com.trendsetter.deck_out.Payment.Payment;
import com.trendsetter.deck_out.Productselector.ProductselectorList;
import com.trendsetter.deck_out.R;

public class Mycart  extends AppCompatActivity {

    RecyclerView mycartrecylerview;
    mycartitemadapter mycartitemadapter;
    RevealAnimation mRevealAnimation;
    TextView  no_of_items , totalprice , netpayableamount , payableamounthighlight , mycartpricedetailheadtxt;
    LinearLayout noitemfound;
    Button additems , paybtn;
    RelativeLayout bottombarlayout , pricepannellayout;
    ScrollView mycartdatalist;
    SharedPreferences sp;
    String userid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycartlayout);
        sp=getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");

        Toolbar toolbar = (Toolbar) findViewById(R.id.mycarttoolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        RelativeLayout rootlayout = findViewById(R.id.mycartrootlayout);
        mRevealAnimation = new RevealAnimation(rootlayout, intent, this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MY CART");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencartsmessagedialog();
            }
        });
        noitemfound = findViewById(R.id.mycartnoitemfound);
        bottombarlayout = findViewById(R.id.mycartbottom_layout);
        pricepannellayout = findViewById(R.id.Pricepannel);
        mycartdatalist = findViewById(R.id.mycartdatalist);
        mycartpricedetailheadtxt = findViewById(R.id.mycartpricedetailheadtxt);
        additems = findViewById(R.id.wishlistadditemsbtn);
        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mycart.this , ProductselectorList.class).putExtra("dresstype" , "Classic"));
            }
        });

        no_of_items = findViewById(R.id.mycartpricedetailsnoofitems);
        totalprice = findViewById(R.id.mycartpricedetailprice);
        netpayableamount = findViewById(R.id.mycartgrandtotalprice);
        payableamounthighlight = findViewById(R.id.mycartpayableamounthighlight);

        paybtn = findViewById(R.id.paybtn);

        mycartrecylerview = findViewById(R.id.mycard_product_detail);
        mycartrecylerview.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Mycart.this);
        mycartrecylerview.setLayoutManager(layoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mycart");
        Query query=ref;
        FirebaseRecyclerOptions<mycartgetdata> options = new FirebaseRecyclerOptions.Builder<mycartgetdata>()
                .setQuery(query, mycartgetdata.class)
                .build();
        mycartitemadapter = new mycartitemadapter(options,Mycart.this , userid);
        mycartrecylerview.setAdapter(mycartitemadapter);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mycart.this, Payment.class).putExtra("userid" , userid));
            }
        });

        setNo_of_items();
        settotalprice();
        getnoofselecteditems();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mycartitemadapter.startListening();
    }


    private void  settotalprice()
    {
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycarttotalprice");
        itemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue().toString().equals("0"))
                {
                    pricepannellayout.setVisibility(View.GONE);
                    mycartpricedetailheadtxt.setVisibility(View.GONE);
                    bottombarlayout.setVisibility(View.GONE);
                }


                else if (Long.parseLong(dataSnapshot.getValue().toString()) < 0)
                {
                    pricepannellayout.setVisibility(View.GONE);
                    mycartpricedetailheadtxt.setVisibility(View.GONE);
                    bottombarlayout.setVisibility(View.GONE);
                }

                else {
                    totalprice.setText("₹ " + dataSnapshot.getValue().toString());
                    netpayableamount.setText("₹ " + (Long.valueOf(dataSnapshot.getValue().toString()) + 2000));
                    setnetamountpayable("" + (Long.valueOf(dataSnapshot.getValue().toString()) + 2000), no_of_items.getText().toString());
                    pricepannellayout.setVisibility(View.VISIBLE);
                    mycartpricedetailheadtxt.setVisibility(View.VISIBLE);
                    bottombarlayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setnetamountpayable(String price , String quant)
    {
        payableamounthighlight.setText("Total : "+price+"  |  Selected Items ( "+quant+" )");
    }

    private  void  setNo_of_items()
    {

        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartitemcount");
        itemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    checkdata(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getnoofselecteditems()
    {
        DatabaseReference selecteditemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        selecteditemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                no_of_items.setText(dataSnapshot.getValue().toString());
                setnetamountpayable(""+netpayableamount.getText().toString() , ""+Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void checkdata(int check)
    {
        if (check == 0)
        {
            noitemfound.setVisibility(View.VISIBLE);
            bottombarlayout.setVisibility(View.GONE);
            mycartdatalist.setVisibility(View.GONE);
        }

        else
        {
            bottombarlayout.setVisibility(View.VISIBLE);
            mycartdatalist.setVisibility(View.VISIBLE);
            noitemfound.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        opencartsmessagedialog();
    }


    private void opencartsmessagedialog()
    {

        AlertDialog.Builder  cartdialog = new AlertDialog.Builder(this);
        cartdialog.setTitle("Message");
        cartdialog.setMessage("\nDo you want to cancel the purchase ?");
        cartdialog.setPositiveButton("Continue shopping" , null);
        cartdialog.setNegativeButton("exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        cartdialog.show();

    }


}
