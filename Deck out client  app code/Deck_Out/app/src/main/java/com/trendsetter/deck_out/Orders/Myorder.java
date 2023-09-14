package com.trendsetter.deck_out.Orders;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

import java.util.List;


public class Myorder  extends AppCompatActivity {

    RecyclerView myorderrecylerview;
    TextView myoredrpendingtxt , myordertotaltxt;
    String useridtxt = "" , mLastQuery;
    FloatingSearchView searchorders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorderslayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myordertoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MY ORDERS");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        searchorders = findViewById(R.id.myorderSearchbar);

        searchorders.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchorders.clearSuggestions();
                } else {

                    searchorders.showProgress();
                    orderlistDataHelper.findSuggestions( newQuery, 5,
                            250, new orderlistDataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<orderlistDataSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    searchorders.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    searchorders.hideProgress();
                                }
                            });

                }

            }
        });


        searchorders.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                mLastQuery = searchSuggestion.getBody();
                setorderlistdata(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                if(query.isEmpty())
                {
                    Toast.makeText(Myorder.this,"Order ID  is necessary",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    setorderlistdata(query);
                }


            }
        });

        searchorders.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                searchorders.swapSuggestions(orderlistDataHelper.getHistory(Myorder.this, 3));

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
               // searchorders.setSearchBarTitle(mLastQuery);
                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns


            }
        });

        useridtxt = getIntent().getExtras().getString("userid");

        myoredrpendingtxt = findViewById(R.id.myorderpendingorders);
        myordertotaltxt = findViewById(R.id.myordertotalorders);

        myorderrecylerview = findViewById(R.id.myorder_product_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Myorder.this);
        myorderrecylerview.setLayoutManager(layoutManager);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orderdetaillist/Client");
        Query query=ref.orderByChild("initdate");
        FirebaseRecyclerOptions<myorderlistgetdata> options = new FirebaseRecyclerOptions.Builder<myorderlistgetdata>()
                .setQuery(query, myorderlistgetdata.class)
                .build();
        myorderitemadapter myorderitemadapter = new myorderitemadapter(options , Myorder.this);
        myorderrecylerview.setAdapter(myorderitemadapter);
        myorderitemadapter.startListening();

        getdata();


    }




    private void getdata()
    {
        final DatabaseReference orderpendingcount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+useridtxt+"/myorderpendingcount");
        orderpendingcount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              myoredrpendingtxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference ordertotalcount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+useridtxt+"/myordertotalcount");
        ordertotalcount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myordertotaltxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private  void setnewadapter(String str)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Myorder.this);
        myorderrecylerview.setLayoutManager(layoutManager);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orderdetaillist/Client");
        Query query=ref.orderByChild("cust_id").equalTo(str);
        FirebaseRecyclerOptions<myorderlistgetdata> options = new FirebaseRecyclerOptions.Builder<myorderlistgetdata>()
                .setQuery(query, myorderlistgetdata.class)
                .build();
        myorderitemadapter myorderitemadapter = new myorderitemadapter(options , Myorder.this);
        myorderrecylerview.setAdapter(myorderitemadapter);
        myorderitemadapter.startListening();
    }

    private void setorderlistdata(String str)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("dataindex/orderindex");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(str))
                {
                    setnewadapter(dataSnapshot.child(str).getValue().toString());
                }

                else
                {
                    Toast.makeText(Myorder.this , "Order ID. not found" ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }







}
