package com.trendsetter.deck_out.Orders;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.trendsetter.deck_out.R;

public class Myorderproductlist  extends AppCompatActivity {

    RecyclerView productlistrecycler;
    String orderid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorderproductlist);
        orderid = getIntent().getExtras().getString("orderidcode");

        Toolbar toolbar = (Toolbar) findViewById(R.id.myorderproductlisttoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Order List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productlistrecycler = findViewById(R.id.myorderproductlistrecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Myorderproductlist.this);
        productlistrecycler.setLayoutManager(layoutManager);

        DatabaseReference productlistref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Client/"+orderid+"/ordersublist");
        Query productlistquery = productlistref;

        final FirebaseRecyclerOptions<myorderproductlistgetdata> options = new FirebaseRecyclerOptions.Builder<myorderproductlistgetdata>()
                .setQuery(productlistquery, myorderproductlistgetdata.class)
                .build();

         myorderproductlistadapter itemadapter = new myorderproductlistadapter(options , Myorderproductlist.this);
        productlistrecycler.setAdapter(itemadapter);
        itemadapter.startListening();
    }
}
