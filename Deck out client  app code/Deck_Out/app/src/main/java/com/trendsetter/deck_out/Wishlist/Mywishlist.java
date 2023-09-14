package com.trendsetter.deck_out.Wishlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.RevealAnimation;
import com.trendsetter.deck_out.Productselector.ProductselectorList;
import com.trendsetter.deck_out.R;

public class Mywishlist extends AppCompatActivity {

    RecyclerView mycartrecylerview;
    FirebaseDatabase database;
    FirebaseRecyclerOptions<mywishlistgetdata> options;
    private TextView mywishlistitemaddcounttxt;
    RevealAnimation mRevealAnimation;
    LinearLayout noitemfound;
    Button additems;
    mywishlistritemadapter mycartitemadapter;
    SharedPreferences sp;
    String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywishlistlayout);

        sp=getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");

        Toolbar toolbar = (Toolbar) findViewById(R.id.myorderttoolbar);
        setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        RelativeLayout rootlayout = findViewById(R.id.mywishlistrootlayout);
        mRevealAnimation = new RevealAnimation(rootlayout, intent, this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MY WISH LIST");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        database = FirebaseDatabase.getInstance();
        mywishlistitemaddcounttxt = findViewById(R.id.mywishlistitemaddcount);
        noitemfound = findViewById(R.id.wishlistnoitemfound);
        additems = findViewById(R.id.wishlistadditemsbtn);
        setadditemcount();
        mycartrecylerview = findViewById(R.id.mywishlist_product_detail);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mycartrecylerview.setLayoutManager(staggeredGridLayoutManager);
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlist");
        Query query=ref;
        options = new FirebaseRecyclerOptions.Builder<mywishlistgetdata>()
                .setQuery(query, mywishlistgetdata.class)
                .build();
        mycartitemadapter = new mywishlistritemadapter(options , Mywishlist.this , userid);
        mycartrecylerview.setAdapter(mycartitemadapter);
        mycartitemadapter.startListening();

        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mywishlist.this , ProductselectorList.class).putExtra("dresstype" , "Classic"));
            }
        });


    }

    private void checkdata(int check)
    {
        if (check == 0)
        {
            noitemfound.setVisibility(View.VISIBLE);
            mycartrecylerview.setVisibility(View.GONE);
        }

        else
        {
            mycartrecylerview.setVisibility(View.VISIBLE);
            noitemfound.setVisibility(View.GONE);
        }
    }


    private void setadditemcount()
    {
        DatabaseReference itemcountref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        itemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mywishlistitemaddcounttxt.setText(dataSnapshot.getValue().toString());
                checkdata(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
