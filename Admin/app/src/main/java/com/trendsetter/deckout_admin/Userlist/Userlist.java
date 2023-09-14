package com.trendsetter.deckout_admin.Userlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.trendsetter.deckout_admin.Blockeduserlist.blockuserlist;
import com.trendsetter.deckout_admin.Extra.DataHelper;
import com.trendsetter.deckout_admin.Extra.DataSuggestion;
import com.trendsetter.deckout_admin.R;

import java.util.List;

public class Userlist  extends AppCompatActivity {
    
    FloatingSearchView searchuser;
    RecyclerView userlistview ;
    Userlistadapter userlistadapter;
    private String mLastQuery = "" , usertype = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlistlayout);
        searchuser = findViewById(R.id.userlistsearchbar);

        usertype  = getIntent().getExtras().getString("usercattype");

        searchuser.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                finish();
            }
        });



        searchuser.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                startActivity(new Intent(Userlist.this , blockuserlist.class).putExtra("usercattype" , usertype));
            }

        });


        searchuser.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchuser.clearSuggestions();
                } else {

                    searchuser.showProgress();
                    DataHelper.findSuggestions(usertype, newQuery, 5,
                            250, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<DataSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    searchuser.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    searchuser.hideProgress();
                                }
                            });

                }

            }
        });


        searchuser.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
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
                    Toast.makeText(Userlist.this,"Phone No. is necessary",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    setuserdata(query);
                }


            }
        });

        searchuser.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                searchuser.swapSuggestions(DataHelper.getHistory(Userlist.this, 3));

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                searchuser.setSearchBarTitle(mLastQuery);
                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns


            }
        });


        userlistview = findViewById(R.id.userlistview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Userlist.this);
        userlistview.setLayoutManager(layoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/"+usertype);
        Query query = ref.orderByChild("checkblock").equalTo("isfalse");
        FirebaseRecyclerOptions<userlistdata> options = new FirebaseRecyclerOptions.Builder<userlistdata>()
                .setQuery(query, userlistdata.class)
                .build();

        userlistadapter = new Userlistadapter(options,Userlist.this , usertype);
        userlistview.setAdapter(userlistadapter);
        userlistadapter.startListening();
    }


    private void setuserdata(String str)
    {
        str = "+91 "+str;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/"+usertype);
        Query query=ref.orderByChild("Mobno").startAt(str).endAt(str+"\uf8ff");
        FirebaseRecyclerOptions<userlistdata> options = new FirebaseRecyclerOptions.Builder<userlistdata>()
                .setQuery(query, userlistdata.class)
                .build();

        userlistadapter = new Userlistadapter(options,Userlist.this , usertype);
        userlistview.setAdapter(userlistadapter);
        userlistadapter.startListening();
    }




}
