package com.trendsetter.deckout_admin.Orderlist;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.trendsetter.deckout_admin.R;

import java.util.List;


public class Orderslist extends AppCompatActivity {

    RecyclerView myorderrecylerview;
    TextView myoredrpendingtxt , myordertotaltxt;
    String checkordertype , mLastQuery;
    FloatingSearchView searchorders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlistlayout);

        checkordertype = getIntent().getExtras().getString("orderfiltertype");

        Toolbar toolbar = (Toolbar) findViewById(R.id.myordertoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ORDER LIST");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        searchorders = findViewById(R.id.orderlistSearchbar);

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
                    Toast.makeText(Orderslist.this,"Order ID  is necessary",Toast.LENGTH_SHORT).show();
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
                searchorders.swapSuggestions(orderlistDataHelper.getHistory(Orderslist.this, 3));

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                searchorders.setSearchBarTitle(mLastQuery);
                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns


            }
        });


        myorderrecylerview = findViewById(R.id.myorder_product_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Orderslist.this);
        myorderrecylerview.setLayoutManager(layoutManager);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orderdetaillist/Client");
        Query query = ref;
        if (checkordertype.equals("#T"))
        {
            query=ref.orderByChild("isdelivered").equalTo(checkordertype);
        }
        else
        {
            query=ref;
        }

        FirebaseRecyclerOptions<orderlistgetdata> options = new FirebaseRecyclerOptions.Builder<orderlistgetdata>()
                .setQuery(query, orderlistgetdata.class)
                .build();
        orderlistitemadapter orderitemadapter = new orderlistitemadapter(options , Orderslist.this);
        myorderrecylerview.setAdapter(orderitemadapter);
        orderitemadapter.startListening();



    }


    private  void setnewadapter(String str)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orderdetaillist/Client");
        Query query=ref.orderByChild("cust_id").equalTo(str);
        FirebaseRecyclerOptions<orderlistgetdata> options = new FirebaseRecyclerOptions.Builder<orderlistgetdata>()
                .setQuery(query, orderlistgetdata.class)
                .build();
        orderlistitemadapter orderitemadapter = new orderlistitemadapter(options , Orderslist.this);
        myorderrecylerview.setAdapter(orderitemadapter);
        orderitemadapter.startListening();
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
                    Toast.makeText(Orderslist.this , "Order ID. not found" ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
