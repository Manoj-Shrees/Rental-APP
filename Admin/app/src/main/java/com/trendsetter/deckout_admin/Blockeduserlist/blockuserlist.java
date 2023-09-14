package com.trendsetter.deckout_admin.Blockeduserlist;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.trendsetter.deckout_admin.Extra.DataHelper;
import com.trendsetter.deckout_admin.Extra.DataSuggestion;
import com.trendsetter.deckout_admin.R;

import java.util.List;

public class blockuserlist extends AppCompatActivity {


    FloatingSearchView blockedsearchuser;
    RecyclerView blockeduserlistview ;
    Blockeduserlistadapter blockeduserlistadapter;
    private String mLastQuery = "" , usertype = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userblocklayout);
        blockedsearchuser = findViewById(R.id.userblocklistsearchbar);
        blockedsearchuser.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                finish();
            }
        });

        usertype  = getIntent().getExtras().getString("usercattype");


        blockedsearchuser.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, 3);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(blockuserlist.this, "Oops! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                }
            }

        });


        blockedsearchuser.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    blockedsearchuser.clearSuggestions();
                } else {

                    blockedsearchuser.showProgress();
                    DataHelper.findSuggestions(usertype, newQuery, 5,
                            250, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<DataSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    blockedsearchuser.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    blockedsearchuser.hideProgress();
                                }
                            });

                }

            }
        });


        blockedsearchuser.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                mLastQuery = searchSuggestion.getBody();
                setuserdata(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                if(query.isEmpty())
                {
                    Toast.makeText(blockuserlist.this,"phone No. is necessary",Toast.LENGTH_SHORT).show();
                }

                else {
                    setuserdata(query);
                }


            }
        });

        blockedsearchuser.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                blockedsearchuser.swapSuggestions(DataHelper.getHistory(blockuserlist.this, 3));

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFocusCleared() {

                blockedsearchuser.setSearchBarTitle(mLastQuery);

            }
        });


        blockeduserlistview = findViewById(R.id.userblocklistview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(blockuserlist.this);
        blockeduserlistview.setLayoutManager(layoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/"+usertype);
        Query query = ref.orderByChild("checkblock").equalTo("istrue");
        FirebaseRecyclerOptions<blockeduserdata> options = new FirebaseRecyclerOptions.Builder<blockeduserdata>()
                .setQuery(query, blockeduserdata.class)
                .build();

        blockeduserlistadapter = new Blockeduserlistadapter(options,blockuserlist.this , usertype);
        blockeduserlistview.setAdapter(blockeduserlistadapter);
        blockeduserlistadapter.startListening();
    }


    private void setuserdata(String str)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/"+usertype);
        Query query=ref.orderByChild("Mobno").startAt(str).endAt(str+"\uf8ff");
        FirebaseRecyclerOptions<blockeduserdata> options = new FirebaseRecyclerOptions.Builder<blockeduserdata>()
                .setQuery(query, blockeduserdata.class)
                .build();

        blockeduserlistadapter = new Blockeduserlistadapter(options,blockuserlist.this , usertype);
        blockeduserlistview.setAdapter(blockeduserlistadapter);
        blockeduserlistadapter.startListening();
    }

}
