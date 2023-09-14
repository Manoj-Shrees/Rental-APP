package com.trendsetter.deck_out.Exploreoptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Cart.Mycart;
import com.trendsetter.deck_out.Extra.NonScrollExpandableListView;
import com.trendsetter.deck_out.Extra.RevealAnimation;
import com.trendsetter.deck_out.R;
import com.trendsetter.deck_out.Searchproduct.Searchproduct;
import com.trendsetter.deck_out.Wishlist.Mywishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploreOption extends AppCompatActivity  {
    RecyclerView Explorelist;
    NonScrollExpandableListView expListView;
    ArrayList<String> menu;
    List<String> fn1,fn2;
    Button exploresearchbtn;
    ImageView explorecartbtn , explorewishlist , exploretopimg , explorebanner1,explorebanner2,explorebanner3,explorebanner4,explorebanner5,explorebanner6 ;
    exploreexplistadapter listAdapter;
    HashMap<String,List<String>> listDataChild;
    ScrollView scrollView;
    TextView expheadtxt;
    SharedPreferences sp;
    Query query;
    String userid;
    Button  cartbadgebtn, wishlistbadgebtn ;
    DatabaseReference ref;
    private String  productserachitem ,dresstypetxt ;
    ImageView exploreoptsearchbtn , exploreoptowishlist , exploreoptocartbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exploreoption);
        scrollView = (ScrollView) findViewById(R.id.explorescrollview);
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");

        Toolbar toolbar = (Toolbar) findViewById(R.id.exploreproducttoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        expheadtxt = findViewById(R.id.expoptionheadtxt);


        productserachitem = getIntent().getExtras().getString("searchitemtxt");

        dresstypetxt = getIntent().getExtras().getString("dresstype");



        Explorelist = findViewById(R.id.suggestselectorlist);

        cartbadgebtn = findViewById(R.id.exoplorecartbadge);
        wishlistbadgebtn = findViewById(R.id.exoplorewishlistbadge);


        exploreoptsearchbtn = findViewById(R.id.exploresearchbtn);
        exploreoptsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                startRevealActivity(v , new Intent(getApplicationContext(), Searchproduct.class));
            }
        });

        exploreoptowishlist = findViewById(R.id.exploremenu_wishlist);
        exploreoptowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRevealActivity(v , new Intent(getApplicationContext(), Mywishlist.class));
            }
        });


        exploreoptocartbtn = findViewById(R.id.exploremenu_cart);
        exploreoptocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRevealActivity(v , new Intent(getApplicationContext(), Mycart.class));
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
         ref = database.getReference("datarecords/Product/Productdetails");

        if (productserachitem != null) {
            expheadtxt.setText(productserachitem);
            setadsbanner(productserachitem);
            productserachitem = productserachitem.toLowerCase();
            productserachitem = productserachitem.substring(0, 1).toUpperCase() + productserachitem.substring(1);
            query = ref.orderByChild("Productsubparenttype").equalTo("#" + productserachitem);
        }

        else
        {
            expheadtxt.setText(dresstypetxt);
            setadsbanner(dresstypetxt);
            dresstypetxt = dresstypetxt.toLowerCase();
            dresstypetxt = dresstypetxt.substring(0, 1).toUpperCase() + dresstypetxt.substring(1);
            query = ref.orderByChild("Productparent_type").equalTo("#" + dresstypetxt);
        }
        FirebaseRecyclerOptions<exploreoptionprofuctgetdata> options = new FirebaseRecyclerOptions.Builder<exploreoptionprofuctgetdata>()
                .setQuery(query, exploreoptionprofuctgetdata.class)
                .build();

        exploreitemadapter adapter = new exploreitemadapter(options ,ExploreOption.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Explorelist.setLayoutManager(layoutManager);
        Explorelist.setAdapter(adapter);
        adapter.startListening();


        exploretopimg = findViewById(R.id.exploretopimg);
        explorebanner1 = findViewById(R.id.explorebanner1);
        explorebanner2 = findViewById(R.id.explorebanner2);
        explorebanner3 = findViewById(R.id.explorebanner3);
        explorebanner4 = findViewById(R.id.explorebanner4);
        explorebanner5 = findViewById(R.id.explorebanner5);
        explorebanner6 = findViewById(R.id.explorebanner6);

        expheadtxt = findViewById(R.id.expoptionheadtxt);


        menu = new ArrayList<>();
        fn1= new ArrayList<String>();
        fn2= new ArrayList<String>();
        listDataChild = new HashMap<>();

        setarraylist();
        expListView =  findViewById(R.id.optionselectorlist);
        listAdapter = new exploreexplistadapter(ExploreOption.this,menu, listDataChild);
        expListView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onStart() {
        super.onStart();
        setbadgecount();
    }

    private void setarraylist()
    {
        menu.add("Clothing");
        menu.add("Accessories");


        fn1.add("Topwear");
        fn1.add("Bottomwear");



        fn2.add("Headwear");
        fn2.add("Footwear");
        fn2.add("Bellywear");
        fn2.add("Others");


        listDataChild.put(menu.get(0), fn1);
        listDataChild.put(menu.get(1), fn2);

    }


    private void setadsbanner(String optiontype)
    {
        DatabaseReference expadssquatop = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbannersquare1imgurl");
        expadssquatop.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(exploretopimg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference expadsbanner1 = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbanner1imgurl");
        expadsbanner1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(explorebanner1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference expadsbanner2 = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbanner2imgurl");
        expadsbanner2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(explorebanner2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference expadssqua1 = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbannersquare2imgurl");
        expadssqua1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(explorebanner3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference expadssqua2 = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbannersquare3imgurl");
        expadssqua2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(explorebanner4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference expadsbanner3 = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbanner3imgurl");
        expadsbanner3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(explorebanner5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference expadsbanner4 = FirebaseDatabase.getInstance().getReference("datarecords/Exploreitems/"+optiontype+"/adsbanner4imgurl");
        expadsbanner4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(explorebanner6);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void startRevealActivity(View v , Intent intent) {
        //calculates the center of the View v you are passing
        int revealX = (int) (v.getX() + v.getWidth() / 2);
        int revealY = (int) (v.getY() + v.getHeight() / 2);

        //create an intent, that launches the second activity and pass the x and y coordinates
        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        //just start the activity as an shared transition, but set the options bundle to null
        ActivityCompat.startActivity(this, intent, null);

        //to prevent strange behaviours override the pending transitions
        overridePendingTransition(0, 0);
    }


    private void setbadgecount()
    {
        DatabaseReference mycartitemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartitemcount");
        mycartitemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartbadgebtn.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mywishlistitemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        mywishlistitemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wishlistbadgebtn.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
