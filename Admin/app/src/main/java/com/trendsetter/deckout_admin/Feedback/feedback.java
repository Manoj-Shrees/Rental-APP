package com.trendsetter.deckout_admin.Feedback;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.trendsetter.deckout_admin.R;

public class feedback extends AppCompatActivity {

    Spinner feedbackcatselector ;
    RecyclerView feedbacklist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbacklayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.feedbacktoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        feedbackcatselector = findViewById(R.id.feedbackcatselector);
        ArrayAdapter<CharSequence> dressmanagelistadapter = new ArrayAdapter<CharSequence>(feedback.this, R.layout.spinnertxtlayout,getResources().getStringArray(R.array.feedbackcat));
        feedbackcatselector.setAdapter(dressmanagelistadapter);

        feedbackcatselector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        setfeedbackdata("#rae");
                        break;

                    case 1:
                        setfeedbackdata("#sgad");
                        break;


                    case 2:
                        setfeedbackdata("#ane");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        feedbacklist = findViewById(R.id.feedbackrecylerview);
        setfeedbackdata("#rae");

    }


    private void setfeedbackdata(String filtercode)
    {
        DatabaseReference homepagedressref = FirebaseDatabase.getInstance().getReference("dataextra/Feedback");
        Query homepagedressquery = homepagedressref.orderByChild("type").equalTo(filtercode);

        final FirebaseRecyclerOptions<feedbackgetdata> options = new FirebaseRecyclerOptions.Builder<feedbackgetdata>()
                .setQuery(homepagedressquery, feedbackgetdata.class)
                .build();
        feedbackadapter adapter = new feedbackadapter(options, feedback.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(feedback.this);
        feedbacklist.setLayoutManager(layoutManager);
        feedbacklist.setAdapter(adapter);
        adapter.startListening();
    }
}
