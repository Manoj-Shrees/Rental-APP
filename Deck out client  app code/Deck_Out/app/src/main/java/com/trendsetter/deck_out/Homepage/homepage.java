package com.trendsetter.deck_out.Homepage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.rupins.drawercardbehaviour.CardDrawerLayout;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Cart.Mycart;
import com.trendsetter.deck_out.DepthPageTransformer;
import com.trendsetter.deck_out.Extra.CheckConnectivity;
import com.trendsetter.deck_out.Extra.Introapp;
import com.trendsetter.deck_out.Extra.NonScrollExpandableListView;
import com.trendsetter.deck_out.Extra.NonScrollListView;
import com.trendsetter.deck_out.Extra.RevealAnimation;
import com.trendsetter.deck_out.FAQ.Faqs;
import com.trendsetter.deck_out.Help.Help;
import com.trendsetter.deck_out.Legals.Legal;
import com.trendsetter.deck_out.Login_Signup.MainActivity;
import com.trendsetter.deck_out.Orders.Myorder;
import com.trendsetter.deck_out.Profile.Profiledetail;
import com.trendsetter.deck_out.R;
import com.trendsetter.deck_out.Searchproduct.Searchproduct;
import com.trendsetter.deck_out.Wishlist.Mywishlist;
import com.viewpagerindicator.CirclePageIndicator;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView productshortcutlist , homepageshortcutlist;
    Toolbar homepagetoolbar;
    private String mLastQuery = "" , userid , usertype;
    private SharedPreferences sp;
    private ImageView headerbackgroundimage , imgbottomads1 , imgbottomads2;
    private CircleImageView profileimageview;
    private TextView usernametxt;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    Button homepagesearchbtn , cartbadgebtn, wishlistbadgebtn ;
    ImageView homepagecartbtn , homepagewishlist;
    NonScrollExpandableListView optionexpListView;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private ArrayList<hompageslideImagemodel> imageModelArrayList;
    private static ArrayList<String> myImageList ;
    private  FirebaseDatabase database;
    private Timer swipeTimer;

    FirebaseRecyclerOptions<Topshortcutlistgetdata>  options;
    homepageshortcutadapter shortcutadapter;

    FirebaseRecyclerOptions<productshortcutlistgetdata> productshortcutoptions;
    productshortcutlistadapter  productadapter;


    FirebaseRecyclerOptions<hompagebottomgallerygetdata> gallerytoptions;
    homepagebottomgalleryadapter  galleryadapter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myImageList = new ArrayList<>();
        setContentView(R.layout.activity_homepage);
        userid = getIntent().getExtras().getString("userid");
        sp=getSharedPreferences("Login", MODE_PRIVATE);
        usertype = sp.getString("usertype","");

        OneSignal.sendTag("user_id",userid);
        OneSignal.sendTag("app_type","Client");

        CardDrawerLayout drawer = (CardDrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setViewScale(Gravity.START, 0.75f);
        drawer.setViewElevation(Gravity.START, 30);
        drawer.setViewScrimColor(Gravity.START, Color.TRANSPARENT);
        drawer.setDrawerElevation(Gravity.START, 20);
        drawer.setRadius(Gravity.START, 25);
        productshortcutlist = findViewById(R.id.productshortcutlist);

        profileimageview = findViewById(R.id.headerimageview);
        usernametxt = findViewById(R.id.headerusername);

        cartbadgebtn = findViewById(R.id.cartbadge);
        wishlistbadgebtn = findViewById(R.id.whislistbadge);


        homepagetoolbar = findViewById(R.id.homepagetoolbar);
        setSupportActionBar(homepagetoolbar);
        homepagetoolbar.setTitleTextColor(Color.WHITE);
        homepagetoolbar.setTitle("DECK OUT");
        homepageshortcutlist = findViewById(R.id.homepageshortcutlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        homepageshortcutlist.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/Homepageshortcutlist");
        Query query=ref.orderByChild("position");
        options = new FirebaseRecyclerOptions.Builder<Topshortcutlistgetdata>()
                        .setQuery(query, Topshortcutlistgetdata.class)
                        .build();

        homepagesearchbtn = findViewById(R.id.homepagesearchbtn);
        homepagesearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                startRevealActivity(v , new Intent(getApplicationContext(), Searchproduct.class));
            }
        });

        homepagewishlist = findViewById(R.id.homepagemenu_wishlist);
        homepagewishlist.setOnClickListener(new View.OnClickListener() {
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


        homepagecartbtn = findViewById(R.id.homepagemenu_cart);
        homepagecartbtn.setOnClickListener(new View.OnClickListener() {
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



        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, homepagetoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.White));
        drawer.setDrawerShadow(null, GravityCompat.START);
        drawer.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();


        fragmentManager = getSupportFragmentManager();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        imageModelArrayList = new ArrayList<>();



        headerbackgroundimage = findViewById(R.id.headerbackgroundimage);
        headerbackgroundimage.setAlpha(0.2f);
        headerbackgroundimage.setBackgroundColor(Color.TRANSPARENT);
        headerbackgroundimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usertype.equals("#guest")) {
                    startActivity(new Intent(homepage.this, Profiledetail.class));

                }

                else
                {
                    openguestusermsg();
                }
            }
        });





        ArrayList<String> optionlist = new ArrayList<String>();
        optionlist.add("CLASSIC");
        optionlist.add("FANCY");
        optionlist.add("STATE");
        optionlist.add("WESTERN");

        ArrayList<String> menulist = new ArrayList<String>();
        menulist.add("Explore");

        ArrayList<String> optionlistname = new ArrayList<String>();
        optionlistname.add("MY WISH LIST");
        optionlistname.add("MY CART");
        optionlistname.add("MY ORDERS");
        optionlistname.add("Help");
        optionlistname.add("FAQ");
        optionlistname.add("LEGAL");
        optionlistname.add("LOGOUT");

        ArrayList<Integer> optionlisticon = new ArrayList<Integer>();
        optionlisticon.add(R.drawable.ic_wishlist);
        optionlisticon.add(R.drawable.ic_menucart);
        optionlisticon.add(R.drawable.ic_myorder);
        optionlisticon.add(R.drawable.ic_help);
        optionlisticon.add(R.drawable.ic_faq);
        optionlisticon.add(R.drawable.ic_legal);
        optionlisticon.add(R.drawable.ic_logout);

        optionexpListView =  findViewById(R.id.Exploreexplist);
        Expandablelistadapter listAdapter = new Expandablelistadapter(homepage.this , menulist,optionlist);
        optionexpListView.setAdapter(listAdapter);;



        NonScrollListView optionlistview  = findViewById(R.id.optionlistview);
        optionlistadapter optionlistadapter = new optionlistadapter(homepage.this ,optionlistname,optionlisticon);
        optionlistview.setAdapter(optionlistadapter);
        optionlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                optionexpListView.collapseGroup(0);

                if(position == 0)
                {
                    if (!usertype.equals("#guest"))
                    {
                        startActivity(new Intent(homepage.this , Mywishlist.class));
                    }

                    else
                    {
                        openguestusermsg();
                    }
                }

                if(position == 1)
                {
                    if (!usertype.equals("#guest"))
                    {
                        setmycartselectedtotalprice();
                        setmycartitemselectionlist();
                        setmycartitemselectedcount();
                        setmycartselecteddate();
                        startActivity(new Intent(homepage.this , Mycart.class));
                    }

                    else
                    {
                        openguestusermsg();
                    }

                }

                if(position == 2)
                {
                    if (!usertype.equals("#guest"))
                    {
                        startActivity(new Intent(homepage.this , Myorder.class).putExtra("userid" , userid));
                    }

                    else
                    {
                        openguestusermsg();
                    }

                }

                if(position == 3)
                {
                    startActivity(new Intent(homepage.this , Help.class));
                }

                if(position == 4)
                {
                    startActivity(new Intent(homepage.this , Faqs.class));
                }

                if(position == 5)
                {
                    startActivity(new Intent(homepage.this , Legal.class));
                }

                if(position == 6)
                {
                    startActivity(new Intent(homepage.this , MainActivity.class));
                    finish();
                    getSharedPreferences("Login", MODE_PRIVATE).edit().clear().commit();
                }

            }
        });


        DatabaseReference productshortcutref = database.getReference("datarecords/Homepageshortcutproductlist");
        Query productshortcutquery=productshortcutref;
        productshortcutoptions = new FirebaseRecyclerOptions.Builder<productshortcutlistgetdata>()
                .setQuery(productshortcutquery, productshortcutlistgetdata.class)
                .build();

        productadapter = new productshortcutlistadapter(productshortcutoptions,homepage.this);
        LinearLayoutManager productshortcutlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        productshortcutlist.setLayoutManager(productshortcutlayoutManager);
        productshortcutlist.setNestedScrollingEnabled(false);
        productshortcutlist.setAdapter(productadapter);


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void initslideimage() {

        mPager = (ViewPager) findViewById(R.id.slidepager);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mPager.setAdapter(new homepageslideImagePager(homepage.this,myImageList));

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.slideindicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        NUM_PAGES = myImageList.size();


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

                if(currentPage == myImageList.size() )
                {
                    mPager.setCurrentItem(0);
                }
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }





    private void initilizeshorcutlist()
    {
        RecyclerView  shorcutimglist = findViewById(R.id.homepageimgshortcutlist);

        DatabaseReference productshortcutref = database.getReference("datarecords/Homepagegallery");
        Query galleryquery=productshortcutref;
        gallerytoptions = new FirebaseRecyclerOptions.Builder<hompagebottomgallerygetdata>()
                .setQuery(galleryquery, hompagebottomgallerygetdata.class)
                .build();

        LinearLayoutManager shorcutimglayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        shorcutimglist.setLayoutManager(shorcutimglayoutManager);
        galleryadapter = new homepagebottomgalleryadapter(gallerytoptions , homepage.this);
        shorcutimglist.setAdapter(galleryadapter);
        galleryadapter.startListening();
    }


    @Override
    protected void onStart() {
        super.onStart();

        CheckConnectivity chkref = new CheckConnectivity(homepage.this);
        Button tryagainbtn = (Button) chkref.gettrybutton();
        tryagainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getdatafromserver(chkref  , true);
            }
        });

        getdatafromserver(chkref , false);

    }



    private void openguestusermsg()
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(homepage.this);
        builder1.setTitle("Message");
        builder1.setMessage("\nplease register or login to acess this service.\n\nDo you want to register or Login ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(homepage.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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

    private void getdatafromserver(CheckConnectivity chkref ,Boolean chk)
    {
        if (chkref.isInternetAvailable())
        {
            myImageList.clear();
            if (!usertype.equals("#guest")) {
                setprofileimages();
                setusername();
                setbadgecount();
            }

            else
            {
                profileimageview.setImageResource(R.drawable.ic_avatar);
                headerbackgroundimage.setImageResource(R.drawable.ic_avatar);
                cartbadgebtn.setText("0");
                wishlistbadgebtn.setText("0");
            }
            productadapter.startListening();
            SharedPreferences mSharedPreference = getSharedPreferences("adslistdata", Context.MODE_PRIVATE);
            myImageList.add(mSharedPreference.getString("adslist0", ""));
            myImageList.add(mSharedPreference.getString("adslist1", ""));
            myImageList.add(mSharedPreference.getString("adslist2", ""));
            myImageList.add(mSharedPreference.getString("adslist3", ""));
            initilizeshorcutlist();
            initslideimage();
            setbottomads();
            new gettingdata().execute();
            chkref.closedialog();
            if (chk == true)
            Toast.makeText(homepage.this , "Now You are connected to Internet." , Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(homepage.this , "Please make your Device Connected to Internet." , Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        swipeTimer.cancel();
    }



    public class gettingdata extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shortcutadapter = new homepageshortcutadapter(options,homepage.this);

        }

        @Override
        protected Object doInBackground(Object[] params) {

            shortcutadapter.startListening();
            try {
                Thread.sleep(500);
            }

            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            homepageshortcutlist.setAdapter(shortcutadapter);
        }
    }



    private void setbottomads()
    {
        imgbottomads1 = findViewById(R.id.homepageshortcutbanner1);

        imgbottomads2 = findViewById(R.id.homepageshortcutbanner2);

        DatabaseReference bottomads1 = database.getReference("datarecords/Homepagebottomadslist/bottomads1url");
        bottomads1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(imgbottomads1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference bottomads2 = database.getReference("datarecords/Homepagebottomadslist/bottomads2url");
        bottomads2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(imgbottomads2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void setprofileimages()
    {
        DatabaseReference profilepicrefrence = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Profilepic");
        profilepicrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_avatar).fit().into(profileimageview);
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_avatar).fit().into(headerbackgroundimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setusername()
    {
        DatabaseReference usernamerefrence = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Name");
        usernamerefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               usernametxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

        DatabaseReference mycartitemcountref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mycartitemcount");
        mycartitemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartbadgebtn.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mywishlistitemcountref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
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


    private void setmycartselectedtotalprice()
    {
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycarttotalprice");
        itemcountref.setValue("0");
    }

    private void setmycartitemselectionlist()
    {
        DatabaseReference  setidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditems");
        setidref.setValue("N/A");

    }

    private void setmycartitemselectedcount()
    {
        DatabaseReference  setidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        setidref.setValue("0");

    }


    private void setmycartselecteddate()
    {
        DatabaseReference  setselecteddateref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteddate");
        setselecteddateref.setValue("N/A");

    }



}
