package com.trendsetter.deckout_admin.Taskhistory;

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
import com.trendsetter.deckout_admin.R;

public class Taskhistory extends AppCompatActivity {

    Toolbar tasktoolbar ;
    RecyclerView taskhistorylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskhistorylistlayout);

        tasktoolbar = findViewById(R.id.tashistorylisttoolbar);
        setSupportActionBar(tasktoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Task History");
        tasktoolbar.setTitleTextColor(Color.WHITE);
        tasktoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskhistorylist = findViewById(R.id.taskhistorylist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Taskhistory.this);
        taskhistorylist.setLayoutManager(layoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("deckoutadmin/taskhistory");
        Query query=ref.orderByChild("date");
        FirebaseRecyclerOptions<taskhistorygetdata> options = new FirebaseRecyclerOptions.Builder<taskhistorygetdata>()
                .setQuery(query, taskhistorygetdata.class)
                .build();

        taskhistoryadapter blockeduserlistadapter = new taskhistoryadapter(options, Taskhistory.this);
        taskhistorylist.setAdapter(blockeduserlistadapter);
        blockeduserlistadapter.startListening();


    }
}
