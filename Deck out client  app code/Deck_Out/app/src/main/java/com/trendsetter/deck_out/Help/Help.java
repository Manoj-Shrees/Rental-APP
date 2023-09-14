package com.trendsetter.deck_out.Help;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.NonScrollExpandableListView;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Help extends AppCompatActivity{
   ArrayList<String> menu;
   HashMap<String, List<String>> listDataansChild;
   HashMap<String, List<String>> listDataimgChild;
   NonScrollExpandableListView helpexpllist;
   helpexplistadapter  listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helplayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.helptoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Help");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        helpexpllist =  findViewById(R.id.helpexplist);
        menu = new ArrayList<>();
        listDataansChild = new HashMap<>();
        listDataimgChild = new HashMap<>();
        menu.clear();
        listDataimgChild.clear();
        listDataansChild.clear();


        new getdata().execute();


    }


    private void getdatalist()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference parentReference = database.getReference("dataextra/Help");

        //reading data from firebase
        parentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    final String datakey= snapshot.getKey();
                    final DatabaseReference childref = parentReference;
                    childref.child(datakey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                String key= snapshot.getKey();
                                String value=snapshot.getValue().toString();


                                if (key.equals("Answer"))
                                {
                                    setListDataansChild(datakey,value);
                                }

                                if (key.equals("Imgurl"))
                                {
                                    setListDataimgChild(datakey,value);
                                }

                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        }



        private void setListDataansChild(String datakey , String data)
        {

            menu.add(datakey);
            ArrayList<String> childlist = new ArrayList<>();
            childlist.add(data);
            listDataansChild.put(datakey, childlist);

        }

        private void setListDataimgChild(String datakey , String data)
        {
            ArrayList<String> childlist = new ArrayList<>();
            childlist.add(data);
            listDataimgChild.put(datakey, childlist);
        }



    public class getdata extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getdatalist();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            listAdapter = new helpexplistadapter(Help.this,listDataansChild, listDataimgChild ,menu);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            helpexpllist.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }

    }
