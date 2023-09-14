package com.trendsetter.deck_out.Productselector;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Cart.Mycart;
import com.trendsetter.deck_out.Extra.RevealAnimation;
import com.trendsetter.deck_out.Homepage.homepage;
import com.trendsetter.deck_out.Login_Signup.MainActivity;
import com.trendsetter.deck_out.R;
import com.trendsetter.deck_out.Searchproduct.Searchproduct;
import com.trendsetter.deck_out.Wishlist.Mywishlist;

public class ProductselectorList extends AppCompatActivity {

    RecyclerView mycard_product_detaillist;
    LinearLayout produclisttfilterbtn , produclisttsortbtn;
    SharedPreferences sp;
    String userid , dresstypetxt , productserachitem ,  proudcttypetxt , usertype;
    Button  cartbadgebtn, wishlistbadgebtn ;
    ImageView productlistsearchbtn , productlistowishlist , productlistocartbtn;
    DatabaseReference ref;
    Query query = null;
    BottomSheetDialog filterdialog , sortdialog;
    TextView filterbykids , filterbymens , filterbywomens , filterbyclassic , filterbyfancy , filterbystate
            , filterbywestern  , dressproducttypetxt , sortbynew , sortbyprice ;
    ImageView selectkids , selectmens , selectwomens , selectclassic , selectfancy , selectstate , selectwestern
             , selectnew  , selectprice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlistlayout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        productserachitem = getIntent().getExtras().getString("searchitemtxt");
        sp=getSharedPreferences("Login", MODE_PRIVATE);
        userid = sp.getString("userid","");
        usertype = sp.getString("usertype","");

        dresstypetxt = getIntent().getExtras().getString("dresstype");
        proudcttypetxt = getIntent().getExtras().getString("Product_type");

        Toolbar toolbar = (Toolbar) findViewById(R.id.productlisttoolbar);
        setSupportActionBar(toolbar);


        filterdialog = new BottomSheetDialog (this);
        filterdialog.setContentView(R.layout.productselectorfilterlayout);
        filterbykids = filterdialog.findViewById(R.id.filterbykids);
        filterbymens = filterdialog.findViewById(R.id.filterbymens);
        filterbywomens = filterdialog.findViewById(R.id.filterbywomens);
        filterbyclassic = filterdialog.findViewById(R.id.filterbyclassic);
        filterbyfancy = filterdialog.findViewById(R.id.filterbyfancy);
        filterbystate = filterdialog.findViewById(R.id.filterbystate);
        filterbywestern = filterdialog.findViewById(R.id.filterbywestern);

        selectkids = filterdialog.findViewById(R.id.selectkids);
        selectmens = filterdialog.findViewById(R.id.selectmens);
        selectwomens = filterdialog.findViewById(R.id.selectwomens);
        selectclassic = filterdialog.findViewById(R.id.selectclassic);
        selectfancy = filterdialog.findViewById(R.id.selectfancy);
        selectstate = filterdialog.findViewById(R.id.selectstate);
        selectwestern = filterdialog.findViewById(R.id.selectwestern);


        sortdialog = new BottomSheetDialog (this);
        sortdialog.setContentView(R.layout.productselectorsortlayout);

        sortbynew = sortdialog.findViewById(R.id.sortbywhatnew);
        sortbyprice = sortdialog.findViewById(R.id.sortbyprice);

        selectnew = sortdialog.findViewById(R.id.selectwhatnew);
        selectprice = sortdialog.findViewById(R.id.selectprice);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dressproducttypetxt  = findViewById(R.id.dresstype);


        cartbadgebtn = findViewById(R.id.productselectorcartbadge);
        wishlistbadgebtn = findViewById(R.id.productselectorwishlistbadge);

        produclisttfilterbtn = findViewById(R.id.produclisttfilterbtn);
        produclisttsortbtn = findViewById(R.id.produclisttsortbtn);

        productlistsearchbtn = findViewById(R.id.productselectrosearchbtn);
        productlistsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                startRevealActivity(v , new Intent(getApplicationContext(), Searchproduct.class));
            }
        });

        productlistowishlist = findViewById(R.id.productselectromenu_wishlist);
        productlistowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!usertype.equals("#guest")) {
                    startRevealActivity(v, new Intent(getApplicationContext(), Mywishlist.class));
                }

                else
                {
                    openguestusermsg();
                }
            }
        });


        productlistocartbtn = findViewById(R.id.productselectromenu_cart);
        productlistocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usertype.equals("#guest")) {
                    startRevealActivity(v , new Intent(getApplicationContext(), Mycart.class));
                }


                else
                {
                    openguestusermsg();
                }
            }
        });

        mycard_product_detaillist = findViewById(R.id.mycard_product_detail);
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
         ref = database.getReference("datarecords/Product/Productdetails");
        if(productserachitem == null)
        {
            if (proudcttypetxt != null)
            {
                query=ref.orderByChild("Product_type").equalTo("#"+proudcttypetxt);
                dresstypetxt = proudcttypetxt;
            }

            else {
                query = ref.orderByChild("Productparent_type").equalTo("#" + dresstypetxt);
            }
        }

        else {
            query = ref.orderByChild("name").startAt(productserachitem.substring(0,4)).endAt(productserachitem.substring(0,4)+"\uf8ff");
            dresstypetxt = productserachitem;
        }



        getproductdata(query , dresstypetxt);



        produclisttfilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilterdialog();
            }
        });


        produclisttsortbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opensortdialog();

            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!usertype.equals("#guest")) {
            setbadgecount();
        }

        else
        {
            cartbadgebtn.setText("0");
            wishlistbadgebtn.setText("0");
        }
    }


    private void getproductdata(Query query , String headtype)
    {
        FirebaseRecyclerOptions<productlistitemgetdata> options = new FirebaseRecyclerOptions.Builder<productlistitemgetdata>()
                .setQuery(query, productlistitemgetdata.class)
                .build();
        productlistitemadapter adapter = new productlistitemadapter(options ,ProductselectorList.this);
        StaggeredGridLayoutManager stagaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mycard_product_detaillist.setLayoutManager(stagaggeredGridLayoutManager);
        mycard_product_detaillist.setAdapter(adapter);
        adapter.startListening();
        filterdialog.dismiss();

        dressproducttypetxt.setText(headtype);

    }


    private void sortproductdata(Query query)
    {
        FirebaseRecyclerOptions<productlistitemgetdata> options = new FirebaseRecyclerOptions.Builder<productlistitemgetdata>()
                .setQuery(query, productlistitemgetdata.class)
                .build();
        productlistitemadapter adapter = new productlistitemadapter(options ,ProductselectorList.this);
        StaggeredGridLayoutManager stagaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mycard_product_detaillist.setLayoutManager(stagaggeredGridLayoutManager);
        mycard_product_detaillist.setAdapter(adapter);
        adapter.startListening();
        sortdialog.dismiss();
        dressproducttypetxt.setText("All Dresses");
    }

    private void  opensortdialog()
    {


        sortbynew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectnew.setVisibility(View.VISIBLE);
                selectprice.setVisibility(View.GONE);
                query = ref.orderByChild("checknew").equalTo("#T");
                sortproductdata(query);
            }
        });

        sortbyprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectnew.setVisibility(View.GONE);
                selectprice.setVisibility(View.VISIBLE);
                query = ref.orderByChild("price");
                sortproductdata(query);
            }
        });

        sortdialog.show();
    }



    private void  openfilterdialog()
    {



        filterbykids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectkids.setVisibility(View.VISIBLE);
                selectmens.setVisibility(View.GONE);
                selectwomens.setVisibility(View.GONE);
                selectclassic.setVisibility(View.GONE);
                selectfancy.setVisibility(View.GONE);
                selectstate.setVisibility(View.GONE);
                selectwestern.setVisibility(View.GONE);
                query = ref.orderByChild("Productsubparenttype").equalTo("#Kids");
                getproductdata(query , filterbykids.getText().toString());
            }
        });

        filterbymens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectmens.setVisibility(View.VISIBLE);
                selectkids.setVisibility(View.GONE);
                selectwomens.setVisibility(View.GONE);
                selectclassic.setVisibility(View.GONE);
                selectfancy.setVisibility(View.GONE);
                selectstate.setVisibility(View.GONE);
                selectwestern.setVisibility(View.GONE);
                query = ref.orderByChild("Productsubparenttype").equalTo("#Mens");
                getproductdata(query , filterbymens.getText().toString());
            }
        });

        filterbywomens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectwomens.setVisibility(View.VISIBLE);
                selectmens.setVisibility(View.GONE);
                selectkids.setVisibility(View.GONE);
                selectclassic.setVisibility(View.GONE);
                selectfancy.setVisibility(View.GONE);
                selectstate.setVisibility(View.GONE);
                selectwestern.setVisibility(View.GONE);
                query = ref.orderByChild("Productsubparenttype").equalTo("#Womens");
                getproductdata(query , filterbywomens.getText().toString());
            }
        });


        filterbyclassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectclassic.setVisibility(View.VISIBLE);
                selectmens.setVisibility(View.GONE);
                selectwomens.setVisibility(View.GONE);
                selectkids.setVisibility(View.GONE);
                selectfancy.setVisibility(View.GONE);
                selectstate.setVisibility(View.GONE);
                selectwestern.setVisibility(View.GONE);
                query = ref.orderByChild("Productparent_type").equalTo("#Classic");
                getproductdata(query , filterbyclassic.getText().toString());
            }
        });


        filterbyfancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectfancy.setVisibility(View.VISIBLE);
                selectmens.setVisibility(View.GONE);
                selectwomens.setVisibility(View.GONE);
                selectclassic.setVisibility(View.GONE);
                selectkids.setVisibility(View.GONE);
                selectstate.setVisibility(View.GONE);
                selectwestern.setVisibility(View.GONE);
                query = ref.orderByChild("Productparent_type").equalTo("#Fancy");
                getproductdata(query , filterbyfancy.getText().toString());
            }
        });


        filterbystate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectstate.setVisibility(View.VISIBLE);
                selectmens.setVisibility(View.GONE);
                selectwomens.setVisibility(View.GONE);
                selectclassic.setVisibility(View.GONE);
                selectfancy.setVisibility(View.GONE);
                selectkids.setVisibility(View.GONE);
                selectwestern.setVisibility(View.GONE);
                query = ref.orderByChild("Productparent_type").equalTo("#State");
                getproductdata(query , filterbystate.getText().toString());
            }
        });


        filterbywestern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectwestern.setVisibility(View.VISIBLE);
                selectmens.setVisibility(View.GONE);
                selectwomens.setVisibility(View.GONE);
                selectclassic.setVisibility(View.GONE);
                selectfancy.setVisibility(View.GONE);
                selectstate.setVisibility(View.GONE);
                selectkids.setVisibility(View.GONE);
                query = ref.orderByChild("Productparent_type").equalTo("#Western");
                getproductdata(query , filterbywestern.getText().toString());
            }
        });



        filterdialog.show();
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


    private void openguestusermsg()
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ProductselectorList.this);
        builder1.setTitle("Message");
        builder1.setMessage("\nplease register or login to acess this service.\n\nDo you want to register or Login ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ProductselectorList.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
