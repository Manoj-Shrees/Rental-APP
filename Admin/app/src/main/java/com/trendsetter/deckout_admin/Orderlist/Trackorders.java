package com.trendsetter.deckout_admin.Orderlist;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.trendsetter.deckout_admin.R;

import java.util.ArrayList;


public class Trackorders extends AppCompatActivity {

    RecyclerView trackorderrecyclerView;
    TextView trackpnametxt , trackitemtypetxt , tracktpricetxt , trackordernotxt  ,  track_delivery_addrstxt;
    String itemname , itemtype , itemtprice , itemorderno , itemdeladdrs ;
    ArrayList<String>  itemtrackdates;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackorderlayout);

        itemname = getIntent().getExtras().getString("itemname");
        itemtype = getIntent().getExtras().getString("itemtype");
        itemtprice = getIntent().getExtras().getString("itemtprice");
        itemtrackdates = (ArrayList<String>) getIntent().getSerializableExtra("itemtrackdates");
        itemorderno = getIntent().getExtras().getString("itemorderno");
        itemdeladdrs = getIntent().getExtras().getString("itemorderdeladdrs");

        Toolbar toolbar = (Toolbar) findViewById(R.id.trackordertoolbar);
        setSupportActionBar(toolbar);

        trackpnametxt = findViewById(R.id.track_product_name);
        trackitemtypetxt = findViewById(R.id.trackitemtype);
        tracktpricetxt = findViewById(R.id.tracktprice);
        trackordernotxt = findViewById(R.id.trackorderno);
        track_delivery_addrstxt = findViewById(R.id.track_delivery_addrs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Track Order");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        trackorderrecyclerView = findViewById(R.id.trackorderrecycler);
        trackorderitemadapter trackorderitemadapter = new trackorderitemadapter(itemtrackdates);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Trackorders.this);
        trackorderrecyclerView.setLayoutManager(layoutManager);
        trackorderrecyclerView.setAdapter(trackorderitemadapter);


        trackpnametxt.setText(itemname);
        trackitemtypetxt.setText(itemtype);
        tracktpricetxt.setText("Due Amt : ₹ "+itemtprice);
        trackordernotxt.setText("Order No. : "+itemorderno);
        track_delivery_addrstxt.setText("Delivery Address : "+itemdeladdrs);


    }



}
