package com.trendsetter.deckout_Delivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    ImageView openqrbtn ;
    EditText ordernotxt;
    Button getdeatilsbtn;
    String userid ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid = getIntent().getExtras().getString("userid");

        Toolbar toolbar = (Toolbar) findViewById(R.id.homepagetoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Loading...");
        getSupportActionBar().setSubtitle("Welcome");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        ordernotxt = findViewById(R.id.ordrno);
        getdeatilsbtn = findViewById(R.id.getordrdetailsbtn);

        openqrbtn = findViewById(R.id.qrbtn);
        openqrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).setBeepEnabled(true).setOrientationLocked(false).setCaptureActivity(QRcodescanner.class).initiateScan();
                         }
        });

        setusername();

        ScrollView scrollView_main = (ScrollView)findViewById(R.id.homepagescrollview);

        scrollView_main.smoothScrollTo(0,0);

        getdeatilsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ordernotxt.getText().toString().trim().length() == 0) {
                ordernotxt.setError("is Empty");
                }

                else
                {
                    checkorderid();
                    Toast.makeText(MainActivity.this , "please wait .." , Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    private void setusername()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productidref = database.getReference("datarecords/deckoutusers/Deliveryboy/"+userid+"/Name");
        productidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getSupportActionBar().setTitle(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private  void checkorderid()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productidref = database.getReference("orderdetaillist");
        productidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ordernotxt.getText().toString()))
                {
                    openorderdeatils(ordernotxt.getText().toString());
                }

               else
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                    message.setTitle("Error");
                    AlertDialog alert;
                    message.setMessage("\nOrder Id not Matched");
                    message.setCancelable(true);
                    message.setNegativeButton("Ok", null);
                    alert = message.create();
                    alert.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }

            else {
                if (result.getContents().contains("DOQR;")) {
                    openorderdeatils(result.getContents().replace("DOQR;" ,""));
                }

                else {
                    Toast.makeText(this, "Invalid code", Toast.LENGTH_LONG).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void openorderdeatils(String id)
    {
        startActivity(new Intent(MainActivity.this, Orderdetails.class).putExtra("orderid", id));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homepagemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logoutbtn:

                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setTitle("Logout");
                AlertDialog alert;
                message.setMessage("\nDo you want to Logout ? ");
                message.setCancelable(true);
                message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this , Login.class));
                        getSharedPreferences("Login", MODE_PRIVATE).edit().clear().commit();
                    }
                });
                message.setNegativeButton("No", null);
                alert = message.create();
                alert.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
