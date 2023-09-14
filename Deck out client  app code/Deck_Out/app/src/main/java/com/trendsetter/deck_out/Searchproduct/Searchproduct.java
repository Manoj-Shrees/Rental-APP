package com.trendsetter.deck_out.Searchproduct;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.trendsetter.deck_out.ColorSuggestion;
import com.trendsetter.deck_out.DataHelper;
import com.trendsetter.deck_out.Extra.Noserachfound;
import com.trendsetter.deck_out.Extra.RevealAnimation;
import com.trendsetter.deck_out.Homepage.Topshortcutlistgetdata;
import com.trendsetter.deck_out.Homepage.homepageshortcutadapter;
import com.trendsetter.deck_out.Productselector.ProductselectorList;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.List;

public class Searchproduct extends AppCompatActivity {

    RecyclerView searchcatlayout;
    ListView mostserarchlist;
    FloatingSearchView searchproduct;
    ArrayList<String>  mostsearchlists;
    private String mLastQuery = "";
    RevealAnimation mRevealAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        Intent intent = this.getIntent();
        RelativeLayout rootlayout = findViewById(R.id.searchlayoutcontainer);
        mRevealAnimation = new RevealAnimation(rootlayout, intent, this);

        searchcatlayout = findViewById(R.id.searchlayoutrecyler);
        mostserarchlist = findViewById(R.id.searchmostsearchlist);
        mostsearchlists = new ArrayList<>();
        setMostserarchlist();

        searchproduct = findViewById(R.id.productsearchbar);
        searchproduct.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
               finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        searchcatlayout.setLayoutManager(layoutManager);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query=ref.child("datarecords/Homepageshortcutlist").orderByChild("position");
        FirebaseRecyclerOptions<Topshortcutlistgetdata> options =
                new FirebaseRecyclerOptions.Builder<Topshortcutlistgetdata>()
                        .setQuery(query, Topshortcutlistgetdata.class)
                        .build();
        homepageshortcutadapter shortcutadapter = new homepageshortcutadapter(options,Searchproduct.this);
        searchcatlayout.setAdapter(shortcutadapter);
        shortcutadapter.startListening();


        final ArrayAdapter<String> mostsearchadapter = new ArrayAdapter<>(Searchproduct.this, R.layout.listviewtext, mostsearchlists);

        mostserarchlist.setTextFilterEnabled(true);
        mostserarchlist.setAdapter(mostsearchadapter);
        mostserarchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Searchproduct.this , ProductselectorList.class).putExtra("searchitemtxt" , mostsearchlists.get(position)));
            }
        });


        searchproduct.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals(""))
                {
                    searchproduct.clearSuggestions();
                }

                else
                {

                    searchproduct.showProgress();
                    DataHelper.findSuggestions(Searchproduct.this, newQuery, 5,
                            250, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<ColorSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    searchproduct.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    searchproduct.hideProgress();
                                }
                            });

                }

            }
        });


        searchproduct.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                mLastQuery = searchSuggestion.getBody();
                opensearchedproduct(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                if(query.isEmpty())
                {
                    Toast.makeText(Searchproduct.this,"Dress name should not be empty",Toast.LENGTH_SHORT).show();
                }

                else {
                    opensearchedproduct(query);
                }


            }
        });

        searchproduct.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                searchproduct.swapSuggestions(DataHelper.getHistory(Searchproduct.this, 3));
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                searchproduct.setSearchBarTitle(mLastQuery);
                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns


            }
        });


    }


        private void setMostserarchlist()
        {
        mostsearchlists.clear();
        SharedPreferences mSharedPreference = getSharedPreferences("mostsearchlistdata", Context.MODE_PRIVATE);
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist0", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist1", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist2", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist3", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist4", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist5", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist6", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist7", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist8", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist9", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist10", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist11", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist12", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist13", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist14", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist15", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist16", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist17", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist18", ""));
        mostsearchlists.add(mSharedPreference.getString("mostsearchlist19", ""));


    }


    private void  opensearchedproduct(final String dressname)
    {

        DatabaseReference dressdetails = FirebaseDatabase.getInstance().getReference("dataindex/productindex");
        dressdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(dressname).exists()) {
                    startActivity(new Intent(Searchproduct.this , ProductselectorList.class).putExtra("searchitemtxt" , dressname));
                }

                else
                {
                    startActivity(new Intent(Searchproduct.this , Noserachfound.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}






