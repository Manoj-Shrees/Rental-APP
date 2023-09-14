package com.trendsetter.deck_out.Productdetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Calender.customviews.DateRangeCalendarView;
import com.trendsetter.deck_out.Extra.Drawablesdata;
import com.trendsetter.deck_out.Extra.Likescounter;
import com.trendsetter.deck_out.Extra.Sizerangegetadapter;
import com.trendsetter.deck_out.Login_Signup.MainActivity;
import com.trendsetter.deck_out.Productselector.ProductselectorList;
import com.trendsetter.deck_out.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentSelectRequestListener;


/**
 * Created by Dreamer on 19-11-2017.
 */

public class Productdetail extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private Button orderbtn , sizexsbtn , sizesbtn ,sizembtn ,sizelbtn , sizexlbtn , sizexxlbtn , zipcodecheckbtn  , sizerangesbtn;
    private Dialog sizedetailpannel;
    private TextView  sizechartbtn ,sizetxt , sizehighttxt , productlikestxt , producttopic , customerreviewtxt , productprice , zipcodechecktxt , expmorelessreviewtxt , sizepanttitle , sizetshirttitle , sizeshirttitle , sizefrocktitle , sizejumpsuittitle;
    private EditText zipcodeinput;
    private ImageView sharebutton ;
    private PhotoView chartview;
    RelativeLayout productrootlayout;
    private ImageButton expreviewarrow;
    private ArrayList<String> myImageList ;
    productdetail_reviewadapter reviewadapter;
    RecyclerView reviewlist, similarproductlist;
    ExpandableTextView productdesc;
    RatingBar producttotalreview;
    private  FirebaseDatabase database;
    private DateRangeCalendarView calendar;
    private LinearLayout expreviewlistbtn , sizechartlayout ;
    private RelativeLayout productaddtowishlistbtn;
    private Boolean reviewcheck = false;
    private ProgressBar reviewpbar;
    Drawable erroricon;
    View sizesep;
    LikeButton  productlikebtn;
    int  lastmonth =0  , mycartitem=0;
    long wishlistcount = 0 ;
    FragmentManager fragmentManager ;
    Sizerangegetadapter getadapter = new Sizerangegetadapter();
    FirebaseRecyclerOptions<productreviewgetdata> reviewoptions;
    SegmentedControl segmentedControl;
    SharedPreferences sp;
    private  String userid , retailerphno , usertype ,  retailerid;



    private Spinner pantlengthspinner , pantwaistspinner ,
            tshirtlengthspinner , tshirtchestspinner , tshirtshoulderspinner ,
            shirtlengthspinner , shirtchestspinner , shirtsleevespinner ,
            frocklengthspinner , frockwaistspinner , frockchestspinner , frockhipspinner ,
            jumpsuitlengthspinner , jumpsuitwaistspinner , jumpsuitchestspinner , jumpsuithipspinner
            ,quantspinner;

    private  LinearLayout pantrootlayout , tshirtrootlayout , shirtrootlayout , frockrootlayout , jumpsuitrootlayout ;

    private RelativeLayout tshirtchestlayout , tshirtshoulderlayout ,  shirtchestlayout , shirtsleevelayout , frockchestlayout ,frockhiplayout ;

    private  String kidspantsmall , kidspantmedium , kidspantlarge , kidspantxlarge ,
            kidsjumpsuitsmall , kidsjumpsuitmedium , kidsjumpsuitlarge , kidsjumpsuitxlarge ,
            kidsfrocksmall , kidsfrockmedium , kidsfrocklarge , kidsfrockxlarge ,
            kidstshirtsmall , kidstshirtmedium , kidstshirtlarge , kidstshirtxlarge ,
            kidsshirtsmall , kidsshirtmedium , kidsshirtlarge , kidsshirtxlarge ,

            menspantsmall , menspantmedium , menspantlarge , menspantxlarge , menspantxxlarge ,
            menstshirtsmall , menstshirtmedium , menstshirtlarge , menstshirtxlarge , menstshirtxxlarge ,
            mensshirtsmall , mensshirtmedium , mensshirtlarge , mensshirtxlarge , mensshirtxxlarge ,

            womenpantxsmall , womenpantsmall , womenpantmedium , womenpantlarge , womenpantxlarge ,  womenpantxxlarge ,
            womenjumpsuitxsmall , womenjumpsuitsmall , womenjumpsuitmedium , womenjumpsuitlarge , womenjumpsuitxlarge , womenjumpsuitxxlarge ,
            womenfrockxsmall ,  womenfrocksmall , womenfrockmedium , womenfrocklarge , womenfrockxlarge , womenfrockxxlarge ,
            womentshirtxsmall , womentshirtsmall , womentshirtmedium , womentshirtlarge , womentshirtxlarge ,womentshirtxxlarge ,
            womenshirtxsmall , womenshirtsmall , womenshirtmedium , womenshirtlarge , womenshirtxlarge  , womenshirtxxlarge
            , sizecharttype , sizetxtdata =""  ;

    private Boolean checkasscr = false;




    private String productnametxt , productdesctxt , productlikes , productdresscode  , productidtxt , productnoofviewstxt , productsizeavailtxt , productpricetxt , producttotalratingstxt ,productimgurl1txt , productimgurl2txt , productimgurl3txt , productimgurl4txt , productimgurl5txt , productimgurl6txt , sizeheadertxt , sizecattype , sizetypetitle , Productparent_typetxt ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp=getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
        usertype = sp.getString("usertype","");

        productnametxt = getIntent().getExtras().getString("productname");
        productdesctxt = getIntent().getExtras().getString("productdesc");
        productdresscode = getIntent().getExtras().getString("productdresscode");
        sizetypetitle = getIntent().getExtras().getString("productsizetitle");
        productidtxt   = getIntent().getExtras().getString("productid");
        productnoofviewstxt = getIntent().getExtras().getString("productnoofviews");
        productsizeavailtxt = getIntent().getExtras().getString("productsizeavail");
        productpricetxt = getIntent().getExtras().getString("productprice");
        producttotalratingstxt = getIntent().getExtras().getString("producttotalratings");
        productlikes = getIntent().getExtras().getString("product_likes");
        Productparent_typetxt = getIntent().getExtras().getString("Productparent_type");
        productimgurl1txt = getIntent().getExtras().getString("productimgurl1");
        productimgurl2txt = getIntent().getExtras().getString("productimgurl2");
        productimgurl3txt = getIntent().getExtras().getString("productimgurl3");
        productimgurl4txt = getIntent().getExtras().getString("productimgurl4");
        retailerid = getIntent().getExtras().getString("retailerid");

        database = FirebaseDatabase.getInstance();
        erroricon = new Drawablesdata().getdrawablewhite(Productdetail.this);
        if (!usertype.equals("#guest")) {
            getcartitemcount();
            getwishlistcount();
        }
        setContentView(R.layout.productdetaillayout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.productdetailtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(productnametxt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fragmentManager = getSupportFragmentManager();
        myImageList = new ArrayList<>();
        setimageslider();
        productrootlayout = findViewById(R.id.productrootlayout);

        zipcodeinput = findViewById(R.id.productzipcodeinput);
        zipcodecheckbtn = findViewById(R.id.zipcodecheck_btn);
        sizehighttxt = findViewById(R.id.sizetxthighlight);

        sizedetailpannel = new Dialog(Productdetail.this);
        sizedetailpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sizedetailpannel.getWindow().setWindowAnimations(R.style.animateddialog);
        sizedetailpannel.setCanceledOnTouchOutside(false);
        sizedetailpannel.setCancelable(true);
        sizedetailpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sizedetailpannel.setContentView(R.layout.productdetailsizedetailslayout);
        pantrootlayout = sizedetailpannel.findViewById(R.id.pantrootlayout);
        tshirtrootlayout = sizedetailpannel.findViewById(R.id.tshirtrootlayout);
        shirtrootlayout = sizedetailpannel.findViewById(R.id.shirtrootlayout);
        frockrootlayout = sizedetailpannel.findViewById(R.id.frockrootlayout);
        jumpsuitrootlayout = sizedetailpannel.findViewById(R.id.jumpsuitrootlayout);



        sizepanttitle = sizedetailpannel.findViewById(R.id.panttitle);
        sizetshirttitle = sizedetailpannel.findViewById(R.id.tshirttitle);
        sizeshirttitle = sizedetailpannel.findViewById(R.id.shirttitle);
        sizefrocktitle = sizedetailpannel.findViewById(R.id.frocktitle);
        sizejumpsuittitle = sizedetailpannel.findViewById(R.id.jumpsuittitle);

        sizerangesbtn = sizedetailpannel.findViewById(R.id.productsizedatabtn);

        productlikestxt = findViewById(R.id.productliketxt);
        productlikebtn = findViewById(R.id.like_btn);

        zipcodechecktxt = findViewById(R.id.zipcodechecktxt);
        expreviewlistbtn = findViewById(R.id.expreview_btn);
        expmorelessreviewtxt = findViewById(R.id.morelessviewtxt);
        expreviewarrow = findViewById(R.id.expreviewarrow);

        reviewpbar = findViewById(R.id.reviewprogressbar);

        productaddtowishlistbtn = findViewById(R.id.productaddtowishlist);


        reviewlist = findViewById(R.id.reviewlist);
        sharebutton = findViewById(R.id.productshare_btn);
        orderbtn = findViewById(R.id.orderbtn);
        sizechartbtn = findViewById(R.id.productdetailsizehelp);
        sizetxt = findViewById(R.id.productsizetxt);
        sizechartlayout = findViewById(R.id.selectsizelayout);
        sizesep = findViewById(R.id.productsizedSplitLine_hor2);


        productdesc = findViewById(R.id.productdesc);
        productdesc.setText( productdesctxt);

        producttopic = findViewById(R.id.producttopic);
        producttopic.setText(productnametxt);

        customerreviewtxt = findViewById(R.id.customerreviewtxt);
        customerreviewtxt.setText(productnoofviewstxt+" Customer reviewed");

        productprice = findViewById(R.id.productprice);
        productprice.setText("Rental ₹ "+productpricetxt+" for 4 days");

        producttotalreview = findViewById(R.id.totalreview);
        producttotalreview.setRating(Float.parseFloat(producttotalratingstxt));


        sizexsbtn = findViewById(R.id.xsbtn);
        sizesbtn = findViewById(R.id.sbtn);
        sizembtn = findViewById(R.id.mbtn);
        sizelbtn = findViewById(R.id.lbtn);
        sizexlbtn = findViewById(R.id.xlbtn);
        sizexxlbtn = findViewById(R.id.xxlbtn);

        initimage();
        initsimilarlist();
        setlikes();
        checkproductlikes();
        getuserphnumber();

        expreviewlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getreviewdata().execute();

            }
        });


        sizexsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sizexsbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                sizexsbtn.setTextColor(Color.WHITE);
                sizesbtn.setTextColor(Color.BLACK);
                sizembtn.setTextColor(Color.BLACK);
                sizelbtn.setTextColor(Color.BLACK);
                sizexlbtn.setTextColor(Color.BLACK);
                sizexxlbtn.setTextColor(Color.BLACK);

                sizeheadertxt = "Selected Size : Extra Small";
                sizecattype = "XSmall";
                new getsizecatdata().execute();
            }
        });

        sizesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizesbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                sizexsbtn.setTextColor(Color.BLACK);
                sizesbtn.setTextColor(Color.WHITE);
                sizembtn.setTextColor(Color.BLACK);
                sizelbtn.setTextColor(Color.BLACK);
                sizexlbtn.setTextColor(Color.BLACK);
                sizexxlbtn.setTextColor(Color.BLACK);


                sizeheadertxt = "Selected Size : Small";
                sizecattype = "Small";
                new getsizecatdata().execute();

            }
        });


        sizembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizembtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                sizexsbtn.setTextColor(Color.BLACK);
                sizesbtn.setTextColor(Color.BLACK);
                sizembtn.setTextColor(Color.WHITE);
                sizelbtn.setTextColor(Color.BLACK);
                sizexlbtn.setTextColor(Color.BLACK);
                sizexxlbtn.setTextColor(Color.BLACK);


                sizeheadertxt = "Selected Size : Medium";
                sizecattype = "Medium";
                new getsizecatdata().execute();
            }
        });

        sizelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizelbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                sizexsbtn.setTextColor(Color.BLACK);
                sizesbtn.setTextColor(Color.BLACK);
                sizembtn.setTextColor(Color.BLACK);
                sizelbtn.setTextColor(Color.WHITE);
                sizexlbtn.setTextColor(Color.BLACK);
                sizexxlbtn.setTextColor(Color.BLACK);

                sizeheadertxt = "Selected Size : Large" ;
                sizecattype = "Large";
                new getsizecatdata().execute();
            }
        });


        sizexlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexlbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                sizexsbtn.setTextColor(Color.BLACK);
                sizesbtn.setTextColor(Color.BLACK);
                sizembtn.setTextColor(Color.BLACK);
                sizelbtn.setTextColor(Color.BLACK);
                sizexlbtn.setTextColor(Color.WHITE);
                sizexxlbtn.setTextColor(Color.BLACK);


                sizeheadertxt = "Selected Size : Extra Large";
                sizecattype = "XLarge";
                new getsizecatdata().execute();
            }
        });


        sizexxlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                sizexxlbtn.setBackgroundResource(R.drawable.selectedcolorincircle);

                sizexsbtn.setTextColor(Color.BLACK);
                sizesbtn.setTextColor(Color.BLACK);
                sizembtn.setTextColor(Color.BLACK);
                sizelbtn.setTextColor(Color.BLACK);
                sizexlbtn.setTextColor(Color.BLACK);
                sizexxlbtn.setTextColor(Color.WHITE);


                sizeheadertxt = "Selected Size : Extra Extra Large" ;
                sizecattype = "XXLarge";
                new getsizecatdata().execute();
            }
        });

        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderbtn.getText().toString().equals("Rent Now")) {


                    if (!usertype.equals("#guest")) {

                        if (sizetxtdata.equals("") && checkasscr == false) {
                            Snackbar snackbar = Snackbar
                                    .make(productrootlayout, "Please Select Dress Size ", Snackbar.LENGTH_LONG);
                            snackbar.getView().setBackgroundColor(Color.RED);
                            TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            snackbar.show();
                        }

                        else {

                            final Dialog orderpreviewdialog = new Dialog(Productdetail.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            orderpreviewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            orderpreviewdialog.getWindow().setWindowAnimations(R.style.animateddialog);
                            orderpreviewdialog.setCanceledOnTouchOutside(false);
                            orderpreviewdialog.setCancelable(false);
                            orderpreviewdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            orderpreviewdialog.setContentView(R.layout.dateselector);
                            Button cancelpaymentbtn = orderpreviewdialog.findViewById(R.id.tocancelbtn);
                            cancelpaymentbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    orderpreviewdialog.dismiss();
                                }
                            });

                            TextView selectedsize = orderpreviewdialog.findViewById(R.id.selectedsize);
                            selectedsize.setText(sizeheadertxt);
                            TextView orderproductname = orderpreviewdialog.findViewById(R.id.productnametxt);
                            TextView ordertpricetxt = orderpreviewdialog.findViewById(R.id.dresspricetxt);
                            orderproductname.setText(productnametxt);
                            ordertpricetxt.setText("₹ " + productpricetxt);
                            final TextView sizedata = orderpreviewdialog.findViewById(R.id.dateselectorsizetxt);
                            sizedata.setText(Html.fromHtml(sizetxtdata));
                            Button addtocartbtn = orderpreviewdialog.findViewById(R.id.proceedtopaymentbtn);
                            EditText tagname = orderpreviewdialog.findViewById(R.id.dresstagname);
                            final RelativeLayout rootlayout = orderpreviewdialog.findViewById(R.id.dateselectorroot);
                            addtocartbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (calendar.getStartDate() == null) {
                                        Snackbar snackbar = Snackbar
                                                .make(rootlayout, "Please Select Duration from Calender", Snackbar.LENGTH_LONG);
                                        snackbar.getView().setBackgroundColor(Color.RED);
                                        TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                                        textView.setTextColor(Color.WHITE);
                                        snackbar.show();
                                    } else {

                                        if (tagname.getText().length() == 0) {
                                            tagname.setError("is Empty");
                                        } else {
                                            Date startdate = calendar.getStartDate().getTime();
                                            Date enddate = calendar.getEndDate().getTime();
                                            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

                                            String dur = dateformat.format(startdate) + " to " + dateformat.format(enddate);
                                            setdatatocart(tagname.getText().toString(), dur, sizetxtdata, quantspinner.getSelectedItem().toString().trim());
                                            orderpreviewdialog.dismiss();
                                        }
                                    }


                                }
                            });


                            calendar = orderpreviewdialog.findViewById(R.id.productcalendar);
                            calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
                                @Override
                                public void onFirstDateSelected(Calendar startDate) {

                                    Calendar later = Calendar.getInstance();
                                    Calendar checkcalender = Calendar.getInstance();

                                    int currenmonth = checkcalender.get(Calendar.MONTH);

                                    int selecteddate = startDate.get(Calendar.DATE);
                                    int selectedtmonth = startDate.get(Calendar.MONTH);
                                    int selecteddateyear = startDate.get(Calendar.YEAR);
                                    int getdayofmonth = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);


                                    later.set(Calendar.YEAR, selecteddateyear);
                                    later.set(Calendar.MONTH, selectedtmonth);
                                    later.set(Calendar.DATE, selecteddate + 5);


                                    switch (currenmonth) {
                                        case 9:
                                            lastmonth = 0;
                                            break;

                                        case 10:
                                            lastmonth = 1;
                                            break;

                                        case 11:
                                            lastmonth = 2;
                                            break;

                                        default:
                                            lastmonth = currenmonth + 3;
                                            break;


                                    }

                                    if (checkcalender.get(Calendar.DATE) == selecteddate && checkcalender.get(Calendar.MONTH) == selectedtmonth && checkcalender.get(Calendar.YEAR) == selecteddateyear) {
                                        calendar.setStartDate(null);
                                    }

                                    if (lastmonth == selectedtmonth) {
                                        if (selecteddate + 5 > 30 && getdayofmonth == 30) {
                                            calendar.setStartDate(null);
                                        } else if (selecteddate + 5 > 31 && getdayofmonth == 31) {
                                            calendar.setStartDate(null);
                                        } else if (selectedtmonth == 1 && selecteddate + 5 > 28 && getdayofmonth == 28) {
                                            calendar.setStartDate(null);
                                        } else if (selectedtmonth == 1 && selecteddate + 5 > 29 && getdayofmonth == 29) {
                                            calendar.setStartDate(null);
                                        } else {
                                            calendar.setEndDate(later);
                                        }
                                    } else {
                                        calendar.setEndDate(later);
                                    }


                                }

                                @Override
                                public void onDateRangeSelected(Calendar startDate, Calendar endDate) {


                                }
                            });


                            orderpreviewdialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                            orderpreviewdialog.show();
                        }

                    }

                    else
                    {
                        openguestusermsg();
                    }

                }


                else
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(Productdetail.this);
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("\nThis Item is not Avialable for now .");
                    message.setCancelable(true);

                    message.setPositiveButton("ok", null);
                    alert = message.create();
                    alert.show();
                }

            }

        });

        zipcodecheckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(zipcodeinput.getText().toString().trim().length() == 0)
                {

                    zipcodeinput.setError("is Empty" , erroricon);

                }

                else {

                    checkzipcode();

                }

            }
        });

        productaddtowishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!usertype.equals("#guest")) {

                    AlertDialog.Builder message = new AlertDialog.Builder(Productdetail.this);
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("\nDo you want to Add this item to Wishlist ?");
                    message.setCancelable(true);

                    message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Productdetail.this, "Please Wait", Toast.LENGTH_SHORT).show();
                            addtowishlist();
                        }
                    });
                    message.setNegativeButton("No", null);
                    alert = message.create();
                    alert.show();

                }

                else
                {
                    openguestusermsg();
                }

            }
        });


        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Productdetail.this , "Please Wait" , Toast.LENGTH_SHORT).show();
                String data = "Hey please check this application:\n\n  \"The Dress Rental Application\" \n\nClick below link to Download this Application\n" + "https://play.google.com/store/apps/details?id=" +getPackageName();
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_TEXT,data);
                startActivity(Intent.createChooser(shareintent,"Share with"));
            }

        });


        productlikebtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                setlikecountplus();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
               setlikecountminus();
            }
        });

         quantspinner = (Spinner) findViewById(R.id.productquantity);
        String[] quant = {"1", "2", "3", "4", "5", "More"};
        ArrayAdapter<CharSequence> quantAdapter = new ArrayAdapter<CharSequence>(Productdetail.this, R.layout.spinner_text, quant);
        quantAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        quantspinner.setAdapter(quantAdapter);

        quantspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (quantspinner.getSelectedItem().equals("More"))
                {
                    final Dialog quantmoretpannel = new Dialog(Productdetail.this);
                    quantmoretpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    quantmoretpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                    quantmoretpannel.setCanceledOnTouchOutside(false);
                    quantmoretpannel.setCancelable(false);
                    quantmoretpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    quantmoretpannel.setContentView(R.layout.quantmorelayout);
                    ImageView quantmoretpannelpdialogclose = (ImageView) quantmoretpannel.findViewById(R.id.quantmoredialogclose);
                    quantmoretpannelpdialogclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            quantmoretpannel.dismiss();
                            quantspinner.setSelection(0);
                        }
                    });

                    final TextView quantinput = quantmoretpannel.findViewById(R.id.quantityinput);
                    Button getquantbtn = quantmoretpannel.findViewById(R.id.quantitysubmitbtn);

                    getquantbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String[] newquant = {"1", "2", "3", "4", "5",quantinput.getText().toString().trim(),"More"};
                            ArrayAdapter<CharSequence> newquantAdapter = new ArrayAdapter<CharSequence>(Productdetail.this, R.layout.spinner_text, newquant);
                            newquantAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            quantspinner.setAdapter(newquantAdapter);
                            quantspinner.setSelection(5);
                            quantmoretpannel.dismiss();
                        }
                    });

                    quantmoretpannel.show();
                    Window window = quantmoretpannel.getWindow();
                    window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



       String sizedata [] = productsizeavailtxt.split(";");

       for (int pos = 0 ; pos < sizedata.length ; pos++)
       {
           switch (sizedata[pos])
           {
               case "#XS" :
                   sizexsbtn.setVisibility(View.VISIBLE);
                   break;

               case "#S" :
                   sizesbtn.setVisibility(View.VISIBLE);
                   break;

               case "#M" :
                   sizembtn.setVisibility(View.VISIBLE);
                   break;

               case "#L" :
                   sizelbtn.setVisibility(View.VISIBLE);
                   break;

               case "#XL" :
                   sizexlbtn.setVisibility(View.VISIBLE);
                   break;

               case "#XXL" :
                   sizexxlbtn.setVisibility(View.VISIBLE);
                   break;

               case "#Ascr" :
                   sizetxt.setVisibility(View.GONE);
                   sizechartlayout.setVisibility(View.GONE);
                   sizechartbtn.setVisibility(View.GONE);
                   sizesep.setVisibility(View.GONE);
                   checkasscr = true;
                   break;

           }
       }
    }




    private void initsimilarlist() {
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/Product/Productdetails");
        Query query=ref;
        FirebaseRecyclerOptions<similarproductlistitemgetdata> options = new FirebaseRecyclerOptions.Builder<similarproductlistitemgetdata>()
                .setQuery(query, similarproductlistitemgetdata.class)
                .build();
        similarproductlist = findViewById(R.id.viewsimilarlist);
        similarproductlistitemadapter adapter = new similarproductlistitemadapter(options , Productdetail.this);
        StaggeredGridLayoutManager stagaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        similarproductlist.setLayoutManager(stagaggeredGridLayoutManager);
        similarproductlist.setNestedScrollingEnabled(false);
        similarproductlist.setAdapter(adapter);
        adapter.startListening();
    }

    private void initimage() {

        mPager = (ViewPager) findViewById(R.id.imageslidepager);
        mPager.setAdapter(new Productslideimageadapter(Productdetail.this, myImageList,productnametxt));

        final CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.slideindicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        NUM_PAGES = myImageList.size();

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


        sizechartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog sizechartpannel = new Dialog(Productdetail.this);
                sizechartpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                sizechartpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                sizechartpannel.setCanceledOnTouchOutside(false);
                sizechartpannel.setCancelable(false);
                sizechartpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sizechartpannel.setContentView(R.layout.sizechartlayout);
                ImageView sizechartpannelpdialogclose = (ImageView) sizechartpannel.findViewById(R.id.sizechartpdialogclose);
                sizechartpannelpdialogclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sizechartpannel.dismiss();
                    }
                });

                sizecharttype = productdresscode.substring(0,2);
                chartview =  sizechartpannel.findViewById(R.id.sizechartitemimg);


                segmentedControl = (SegmentedControl) sizechartpannel.findViewById(R.id.segmented_control);
                segmentedControl.setTopLeftRadius(60);
                segmentedControl.setBottomRightRadius(60);
                segmentedControl.setRadiusForEverySegment(true);
                setsizecharttype(sizecharttype);



                sizechartpannel.show();
                Window window = sizechartpannel.getWindow();
                window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });



        DatabaseReference ref = database.getReference("datarecords/Product/Productreviews/"+productidtxt);
        Query query=ref.limitToFirst(1);
        reviewoptions = new FirebaseRecyclerOptions.Builder<productreviewgetdata>()
                .setQuery(query, productreviewgetdata.class)
                .build();


        reviewadapter = new productdetail_reviewadapter(reviewoptions , Productdetail.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Productdetail.this);
        reviewlist.setLayoutManager(layoutManager);
        reviewlist.setNestedScrollingEnabled(false);
        reviewlist.setAdapter(reviewadapter);
        reviewadapter.startListening();


    }




    private void setimageslider() {
        myImageList.add(productimgurl1txt);
        myImageList.add(productimgurl2txt);
        myImageList.add(productimgurl3txt);
        myImageList.add(productimgurl4txt);
    }




    private void checkzipcode()
    {
        DatabaseReference landmarkref = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist");
        landmarkref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(zipcodeinput.getText().toString()))
                {
                    zipcodechecktxt.setText("Available in ");
                    getZipcoddata();
                }

                else
                {
                    zipcodechecktxt.setText("Sorry for Inconvenience , Delivery not Possible here \n\' please try another Location \'");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void getZipcoddata()
    {
        DatabaseReference landmarkref = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist/"+zipcodeinput.getText().toString()+"/landmark");
        landmarkref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               zipcodechecktxt.append(""+dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference regionref = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist/"+zipcodeinput.getText().toString()+"/region");
        regionref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zipcodechecktxt.append(" "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference zipcode = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist/"+zipcodeinput.getText().toString()+"/zipcode");
        zipcode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zipcodechecktxt.append(" - "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }



    public class getreviewdata extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            reviewlist.setVisibility(View.GONE);
            reviewpbar.setVisibility(View.VISIBLE);

            if (reviewcheck == false) {
                DatabaseReference ref = database.getReference("datarecords/Product/Productreviews/" + productidtxt);
                Query query = ref;
                reviewoptions = new FirebaseRecyclerOptions.Builder<productreviewgetdata>()
                        .setQuery(query, productreviewgetdata.class)
                        .build();


                reviewadapter = new productdetail_reviewadapter(reviewoptions, Productdetail.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Productdetail.this);
                reviewlist.setLayoutManager(layoutManager);
                reviewlist.setNestedScrollingEnabled(false);
                expmorelessreviewtxt.setText("Less Reviews");
                expreviewarrow.setImageResource(R.drawable.ic_uparrow);

            }

            else
            {

                DatabaseReference ref = database.getReference("datarecords/Product/Productreviews/" + productidtxt);
                Query query = ref.limitToFirst(1);
                reviewoptions = new FirebaseRecyclerOptions.Builder<productreviewgetdata>()
                        .setQuery(query, productreviewgetdata.class)
                        .build();


                reviewadapter = new productdetail_reviewadapter(reviewoptions, Productdetail.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Productdetail.this);
                reviewlist.setLayoutManager(layoutManager);
                reviewlist.setNestedScrollingEnabled(false);
                expmorelessreviewtxt.setText("More Reviews");
                expreviewarrow.setImageResource(R.drawable.ic_downarrow);
            }

        }

        @SuppressLint("WrongThread")
        @Override
        protected Object doInBackground(Object[] objects) {
           if(reviewcheck == false)
           {
               reviewlist.setAdapter(reviewadapter);
               reviewadapter.startListening();
               reviewcheck = true;
           }

           else
           {
               reviewlist.setAdapter(reviewadapter);
               reviewadapter.startListening();
               reviewcheck = false;
           }


            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            reviewlist.setVisibility(View.VISIBLE);
            reviewpbar.setVisibility(View.GONE);

        }
    }



    public  void opensizedetails(String code ,String typetitle , String headtxt , String type)
    {

        final TextView sizetype = sizedetailpannel.findViewById(R.id.sizetypetxt);
        sizetype.setText(headtxt);


        inistilizedata();

        tshirtchestlayout = sizedetailpannel.findViewById(R.id.tshirtchestlayout);
        tshirtshoulderlayout = sizedetailpannel.findViewById(R.id.tshirtshoulderlayout);
        shirtchestlayout = sizedetailpannel.findViewById(R.id.shirtchestlayout);
        shirtsleevelayout = sizedetailpannel.findViewById(R.id.shirtsleevelayout);
        frockchestlayout = sizedetailpannel.findViewById(R.id.frockchestlayout);
        frockhiplayout = sizedetailpannel.findViewById(R.id.frockhiplayout);


        pantlengthspinner =  sizedetailpannel.findViewById(R.id.pantlengthselector);
        pantwaistspinner =  sizedetailpannel.findViewById(R.id.pantwaistselector);

        tshirtlengthspinner =  sizedetailpannel.findViewById(R.id.tshirtlengthselector);
        tshirtchestspinner =  sizedetailpannel.findViewById(R.id.tshirtchestselector);
        tshirtshoulderspinner =  sizedetailpannel.findViewById(R.id.tshirtshoulderselector);

        shirtlengthspinner = sizedetailpannel.findViewById(R.id.shirtlengthselector);
        shirtchestspinner = sizedetailpannel.findViewById(R.id.shirtchestselector);
        shirtsleevespinner = sizedetailpannel.findViewById(R.id.shirtsleeveselector);

        frocklengthspinner = sizedetailpannel.findViewById(R.id.frocklengthselector);
        frockwaistspinner = sizedetailpannel.findViewById(R.id.frockwaistselector);
        frockchestspinner = sizedetailpannel.findViewById(R.id.frockchestselector);
        frockhipspinner = sizedetailpannel.findViewById(R.id.frockhipselector);

        jumpsuitlengthspinner = sizedetailpannel.findViewById(R.id.jumpsuitlengthselector);
        jumpsuitwaistspinner = sizedetailpannel.findViewById(R.id.jumpsuitwaistselector);
        jumpsuitchestspinner = sizedetailpannel.findViewById(R.id.jumpsuitchestselector);
        jumpsuithipspinner = sizedetailpannel.findViewById(R.id.jumpsuithipselector);

        final ImageView pantlengtherror = sizedetailpannel.findViewById(R.id.pantlengtherrorimg);
        final ImageView pantwaisterror = sizedetailpannel.findViewById(R.id.pantwaisterrorimg);

        final ImageView tshirtlengtherror = sizedetailpannel.findViewById(R.id.tshirtlengtherrorimg);
        final ImageView tshirtchesterror = sizedetailpannel.findViewById(R.id.tshirtchesterrorimg);
        final ImageView tshirtshouldererror = sizedetailpannel.findViewById(R.id.tshirtshouldererrorimg);

        final ImageView shirtlengtherror = sizedetailpannel.findViewById(R.id.shirtlengtherrorimg);
        final ImageView shirtchesterror = sizedetailpannel.findViewById(R.id.shirtchesterrorimg);
        final ImageView shirtsleeveerror = sizedetailpannel.findViewById(R.id.shirtsleeveerrorimg);

        final ImageView frocklengtherror = sizedetailpannel.findViewById(R.id.frocklengtherrorimg);
        final ImageView frockwaisterror = sizedetailpannel.findViewById(R.id.frockwaisterrorimg);
        final ImageView frockchesterror = sizedetailpannel.findViewById(R.id.frockchesterrorimg);
        final ImageView frockhiperror = sizedetailpannel.findViewById(R.id.frockhiperrorimg);

        final ImageView jumpsuitlengtherror = sizedetailpannel.findViewById(R.id.jumpsuitlengtherrorimg);
        final ImageView jumpsuitwaisterror = sizedetailpannel.findViewById(R.id.jumpsuitwaisterrorimg);
        final ImageView jumpsuitchesterror = sizedetailpannel.findViewById(R.id.jumpsuitchesterrorimg);
        final ImageView jumpsuithipeerror = sizedetailpannel.findViewById(R.id.jumpsuithiperrorimg);




        sizerangesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str ="" ;
                int checkdata = 0 , errorcount=0;


                if (pantrootlayout.getVisibility() ==View.VISIBLE)
                {
                    if(pantlengthspinner.getSelectedItemPosition() == 0)
                    {
                        pantlengtherror.setVisibility(View.VISIBLE);
                        checkdata+=1;
                        errorcount+=1;
                    }

                    else
                    {
                        pantlengtherror.setVisibility(View.GONE);
                    }

                    if(pantwaistspinner.getSelectedItemPosition() == 0)
                    {
                        pantwaisterror.setVisibility(View.VISIBLE);
                        checkdata+=1;
                        errorcount+=1;
                    }

                    else
                    {
                        pantwaisterror.setVisibility(View.GONE);
                    }

                    if(checkdata == 0)
                    {
                        str += "<br><u>" + sizepanttitle.getText().toString().trim() + "</u><br><br>L = " + pantlengthspinner.getSelectedItem().toString() + "  |  W = " + pantwaistspinner.getSelectedItem().toString().trim();
                    }

                    str+="<br>";

                    checkdata=0;
                }

                if (frockrootlayout.getVisibility() ==View.VISIBLE) {

                    if (frocklengthspinner.getSelectedItemPosition() == 0) {
                        frocklengtherror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        frocklengtherror.setVisibility(View.GONE);
                    }


                    if (frockwaistspinner.getSelectedItemPosition() == 0) {
                       frockwaisterror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        frockwaisterror.setVisibility(View.GONE);
                    }


                    if (checkdata == 0) {

                        str += "<br><u>" + sizefrocktitle.getText().toString().trim() + "</u><br><br>L = " + frocklengthspinner.getSelectedItem().toString().trim() +
                                "  |  W = " + frockwaistspinner.getSelectedItem().toString().trim();
                    }

                    if (frockchestlayout.getVisibility() == View.VISIBLE) {
                        if (frockchestspinner.getSelectedItemPosition() == 0) {
                            frockchesterror.setVisibility(View.VISIBLE);
                            errorcount+=1;
                        }

                        else
                            {
                            frockchesterror.setVisibility(View.GONE);
                            str += " |  C = " + frockchestspinner.getSelectedItem().toString().trim();
                        }
                    }

                    if (frockhiplayout.getVisibility() == View.VISIBLE) {
                        if (frockhipspinner.getSelectedItemPosition() == 0) {
                            frockhiperror.setVisibility(View.VISIBLE);
                            errorcount+=1;
                        }

                        else
                            {
                                frockhiperror.setVisibility(View.GONE);
                            str += "| H = " + frockhipspinner.getSelectedItem().toString().trim();
                        }

                    }

                    str+="<br>";
                    checkdata = 0;

                }

                if (tshirtrootlayout.getVisibility() ==View.VISIBLE) {

                    if (tshirtlengthspinner.getSelectedItemPosition() == 0) {
                        tshirtlengtherror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        tshirtlengtherror.setVisibility(View.GONE);
                    }

                    if (checkdata == 0)
                    {
                        str += "<br><u>" + sizetshirttitle.getText().toString().trim() + "</u><br><br>L = " + tshirtlengthspinner.getSelectedItem().toString().trim();
                    }

                    if(tshirtchestlayout.getVisibility() == View.VISIBLE)
                    {
                        if (tshirtchestspinner.getSelectedItemPosition() == 0)
                        {
                            tshirtchesterror.setVisibility(View.VISIBLE);
                            errorcount+=1;
                        }

                        else
                         {
                            tshirtchesterror.setVisibility(View.GONE);
                            str += "  |  C = " + tshirtchestspinner.getSelectedItem().toString().trim();
                        }
                    }

                    if(tshirtshoulderlayout.getVisibility() == View.VISIBLE)
                    {
                        if(tshirtshoulderspinner.getSelectedItemPosition() == 0)
                        {
                            tshirtshouldererror.setVisibility(View.VISIBLE);
                            errorcount+=1;
                        }

                        else
                            {
                                tshirtshouldererror.setVisibility(View.GONE);
                            str += "  |  S = " + tshirtshoulderspinner.getSelectedItem().toString().trim();
                        }
                    }


                    str+="<br>";

                    checkdata = 0;


                }

                if (shirtrootlayout.getVisibility() ==View.VISIBLE)
                {

                    if (shirtlengthspinner.getSelectedItemPosition() == 0) {
                        shirtlengtherror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        shirtlengtherror.setVisibility(View.GONE);
                    }

                    if (checkdata == 0) {

                        str += "<br><u>" + sizeshirttitle.getText().toString().trim() + "</u><br><br>L = " + shirtlengthspinner.getSelectedItem().toString().trim();
                    }

                    if(shirtchestlayout.getVisibility() == View.VISIBLE)
                    {
                        if (shirtchestspinner.getSelectedItemPosition() == 0)
                        {
                            shirtchesterror.setVisibility(View.VISIBLE);
                            errorcount+=1;
                        }

                        else {
                            shirtchesterror.setVisibility(View.GONE);
                            str += "  |  C = " + shirtchestspinner.getSelectedItem().toString().trim();
                        }
                    }

                    if(shirtsleevelayout.getVisibility() == View.VISIBLE)
                    {
                        if(shirtsleevespinner.getSelectedItemPosition() == 0)
                        {
                           shirtsleeveerror.setVisibility(View.VISIBLE);
                            errorcount+=1;
                        }

                        else
                        {
                            shirtsleeveerror.setVisibility(View.GONE);
                            str+="  |  Sl  =  "+shirtsleevespinner.getSelectedItem().toString().trim();
                        }

                    }

                    str+="<br>";

                    checkdata = 0;
                }

                if (jumpsuitrootlayout.getVisibility() ==View.VISIBLE)
                {

                    if (jumpsuitlengthspinner.getSelectedItemPosition() == 0) {
                       jumpsuitlengtherror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        jumpsuitlengtherror.setVisibility(View.GONE);
                    }

                    if (jumpsuitchestspinner.getSelectedItemPosition() == 0) {
                        jumpsuitchesterror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        jumpsuitchesterror.setVisibility(View.GONE);
                    }


                    if (jumpsuitwaistspinner.getSelectedItemPosition() == 0) {
                       jumpsuitwaisterror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        jumpsuitwaisterror.setVisibility(View.GONE);
                    }

                    if (jumpsuithipspinner.getSelectedItemPosition() == 0) {
                       jumpsuithipeerror.setVisibility(View.VISIBLE);
                        checkdata += 1;
                        errorcount+=1;
                    }

                    else
                    {
                        jumpsuithipeerror.setVisibility(View.GONE);
                    }


                    if (checkdata == 0) {

                        str += "<br><u>" + sizejumpsuittitle.getText().toString().trim() +
                                "</u><br><br>L = " + jumpsuitlengthspinner.getSelectedItem().toString().trim() +
                                "  |  W = " + jumpsuitwaistspinner.getSelectedItem().toString().trim() +
                                "  |  C = " + jumpsuitchestspinner.getSelectedItem().toString().trim() +
                                "  |  H = " + jumpsuithipspinner.getSelectedItem().toString().trim();
                    }

                    str+="<br>";
                    checkdata = 0;

                }


                if(errorcount == 0)
                {
                    setsizedata(str);
                    sizedetailpannel.dismiss();
                }


                errorcount = 0;



            }
        });



        setadaptertype(code ,typetitle , type);


        ImageView changepannelclosebtn = sizedetailpannel.findViewById(R.id.mycartsizedetaildialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizedetailpannel.dismiss();
            }
        });


    }




    private void setPantadap(String code)
    {

        if(code.trim().equals(kidspantsmall.trim()))
        {
            getadapter.setdata(Productdetail.this ,kidspantsmall, getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizesmalldapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizesmalldapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(kidspantmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidspantmedium, getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizemediumadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizemediumadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(kidspantlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidspantlarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizelargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizelargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(kidspantxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidspantxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizexlargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizexlargeladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(menspantsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,menspantsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizesmalladapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizesmalladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,menspantsmall , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizemediumadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizemediumadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,menspantlarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizelargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizelargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,menspantxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizeextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizeextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,menspantxxlarge , getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizeextraextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizeextraextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantxsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenpantxsmall , getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizeextrasmalladapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizeextrasmalladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenpantsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizesmalladapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizesmalladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(womenpantmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenpantmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizemediumadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizemediumadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenpantlarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizelargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizelargeadaopter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenpantxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizeextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizeextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenpantxxlarge , getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizeextraextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizeextraextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


    }

    private void setFrockadap(String code)
    {

        if(code.trim().equals(kidsfrocksmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsfrocksmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizesmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizesmalladapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizesmalladapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsfrockmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsfrockmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizemediumadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizemediumadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizemediumadapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsfrocklarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsfrocklarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizelargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizelargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizelargeadapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(kidsfrockxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsfrockxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizeextralargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizeextralargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizeextralargeadapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womenfrockxsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenfrockxsmall , getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizeextrasmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizeextrasmalladapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizeextrasmalladapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womenfrocksmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenfrocksmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizesmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizesmalladapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizesmalladapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womenfrockmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenfrockmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizemediumadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizemediumadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizemediumadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womenfrocklarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenfrocklarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizelargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizelargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizelargeadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womenfrockxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenfrockxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizeextralargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizeextralargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizeextralargeadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womenfrockxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenfrockxxlarge , getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizeextraextralargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizeextraextralargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizeextraextralargeadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

    }

    private void setTshirtadap(String code)
    {
        if(code.trim().equals(kidstshirtsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidstshirtsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizesmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizesmalladapter();
            frockwaistspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidstshirtmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidstshirtmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizemediumadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizemediumadapter();
            frockwaistspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(kidstshirtlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidstshirtlarge, getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizelargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizelargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidstshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidstshirtxlarge, getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizeextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidstshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidstshirtxlarge, getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizeextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(menstshirtsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,menstshirtsmall, getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizesmalladapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizesmalladaper();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizesmalladapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,menstshirtmedium, getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizemediumadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizemediumadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizemediumadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,menstshirtlarge, getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizelargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizelargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizelargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,menstshirtxlarge, getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizeextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizeeextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,menstshirtxxlarge, getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizeextraextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizeextraextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizeextraextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }


        if(code.trim().equals(womentshirtxsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womentshirtxsmall, getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizeextrasmalladapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizeextrasmalladapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womentshirtsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womentshirtsmall, getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizesmalladapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizesmalladapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womentshirtmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,womentshirtmedium, getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizemediumadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizemediumadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womentshirtlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womentshirtlarge, getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizelargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizelargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womentshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womentshirtxlarge, getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizeextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womentshirtxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womentshirtxxlarge, getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizeextraextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizeextraextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);
        }


    }


    private void setShirtadap(String code)
    {

        if(code.trim().equals(kidsshirtsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsshirtsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizesmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizesmalladapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsshirtmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsshirtmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizemediumadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizemediumadapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsshirtlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsshirtlarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizelargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizelargeadapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsshirtxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizeextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizeextralargeadapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(mensshirtsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,mensshirtsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizesmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizesmalladapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizesmalladapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }


        if(code.trim().equals(mensshirtmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,mensshirtmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizemediumadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizemediumadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizemediumadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(mensshirtlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,mensshirtlarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizelargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizelargeadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizelargeadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(mensshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,mensshirtxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizeextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizeextralargeadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizeextraextralargeadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(mensshirtxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,mensshirtxxlarge , getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizeextraextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizeextralargeadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizeextraextralargeadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(womenshirtxsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenshirtxsmall , getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizeextrasmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenshirtsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizesmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenshirtmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizemediumadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenshirtlarge , getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizelargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }


        if(code.trim().equals(womenshirtxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenshirtxlarge , getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizeextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenshirtxxlarge , getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizeextraextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

    }

    private void setJumpsuitadap(String code)
    {

        if(code.trim().equals(kidsjumpsuitsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsjumpsuitsmall , getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizesmalladapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizesmalladapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizesmalladapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizesmalladapter();
            jumpsuithipspinner.setAdapter(hipadapter);

        }

        if(code.trim().equals(kidsjumpsuitmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsjumpsuitmedium , getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizemediumadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizemediumadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizemediumadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizemediumadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(kidsjumpsuitlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsjumpsuitlarge, getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizelargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizelargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizelargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizelargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(kidsjumpsuitxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,kidsjumpsuitxlarge, getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizeextralargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizeextralargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizeextralargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizeextralargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitxsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenjumpsuitxsmall, getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizeextrasmalladapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizeextrasmalladapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizeextrasmalladapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizeextrasmalladapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }


        if(code.trim().equals(womenjumpsuitsmall.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenjumpsuitsmall, getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizesmalladapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizesmalladapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizesmalladapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizesmalladapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitmedium.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenjumpsuitmedium, getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizemediumadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizemediumadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizemediumadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizemediumadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenjumpsuitlarge, getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizelargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizelargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizelargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizelargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenjumpsuitxlarge, getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizeextralargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizeextralargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizeextralargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizeextralargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitxxlarge.trim()))
        {

            getadapter.setdata(Productdetail.this ,womenjumpsuitxxlarge, getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizeextralargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizeextraextralargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizeextraextralargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizeextraextralargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }


    }


    private  void setsizecharttype(String code)
    {

        String kidscode = getResources().getString(R.string.kidscode);
        String menscode = getResources().getString(R.string.menscode);
        String woenscode = getResources().getString(R.string.womenscode);

       if(kidscode.equals(code))
       {
           segmentedControl.addSegments(getResources().getStringArray(R.array.kidsdressnames));
           segmentedControl.setSelectedSegment(0);
           setchart("Kids/pantcharturl");
           segmentedControl.setOnSegmentSelectRequestListener(new OnSegmentSelectRequestListener() {
               @Override
               public boolean onSegmentSelectRequest(SegmentViewHolder segmentViewHolder) {

                   int pos =  segmentViewHolder.getAbsolutePosition();

                   switch (pos)
                   {
                       case 0:
                       setchart("Kids/pantcharturl");
                       break;

                       case 1:
                           setchart("Kids/frockcharturl");
                           break;

                       case 2:
                           setchart("Kids/tshirtcharturl");
                           break;

                       case 3:
                           setchart("Kids/shirtcharturl");
                           break;

                       case 4:
                           setchart("Kids/jumpsuitcharturl");
                           break;


                   }
                   return true;
               }
           });
       }

        if(menscode.equals(code))
        {
            segmentedControl.addSegments(getResources().getStringArray(R.array.mensdressnames));
            segmentedControl.setSelectedSegment(0);
            setchart("Mens/pantcharturl");
            segmentedControl.setOnSegmentSelectRequestListener(new OnSegmentSelectRequestListener() {
                @Override
                public boolean onSegmentSelectRequest(SegmentViewHolder segmentViewHolder) {

                    int pos =  segmentViewHolder.getAbsolutePosition();
                    switch (pos)
                    {
                        case 0:
                            setchart("Mens/pantcharturl");
                            break;


                        case 1:
                            setchart("Mens/tshirtcharturl");
                            break;

                        case 2:
                            setchart("Mens/shirtcharturl");
                            break;


                    }
                    return true;
                }
            });
        }

        if(woenscode.equals(code))
        {
            segmentedControl.addSegments(getResources().getStringArray(R.array.womensdressnames));
            segmentedControl.setSelectedSegment(0);
            setchart("Womens/pantcharturl");
            segmentedControl.setOnSegmentSelectRequestListener(new OnSegmentSelectRequestListener() {
                @Override
                public boolean onSegmentSelectRequest(SegmentViewHolder segmentViewHolder) {

                    int pos =  segmentViewHolder.getAbsolutePosition();
                    switch (pos)
                    {
                        case 0:
                            setchart("Womens/pantcharturl");
                            break;

                        case 1:
                            setchart("Womens/frockcharturl");
                            break;

                        case 2:
                            setchart("Womens/tshirtcharturl");
                            break;

                        case 3:
                            setchart("Womens/shirtcharturl");
                            break;

                        case 4:
                            setchart("Womens/jumpsuitcharturl");
                            break;


                    }
                    return true;
                }
            });
        }

    }


    private void setsizedata(String str)
    {
        sizetxtdata = str;
        sizehighttxt.setVisibility(View.VISIBLE);
        sizehighttxt.setText(Html.fromHtml(str));
    }

    private void setchart(String loc)
    {
        DatabaseReference profilepicrefrence = FirebaseDatabase.getInstance().getReference("dataextra/Sizecharts/"+loc);
        profilepicrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_loadingthumb).fit().into(chartview, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void inistilizedata()
    {
        kidspantsmall = getResources().getString(R.string.kps);
        kidspantmedium = getResources().getString(R.string.kpm);
        kidspantlarge = getResources().getString(R.string.kpl);
        kidspantxlarge = getResources().getString(R.string.kpxl);

        kidsjumpsuitsmall = getResources().getString(R.string.kjss);
        kidsjumpsuitmedium = getResources().getString(R.string.kjsm);
        kidsjumpsuitlarge = getResources().getString(R.string.kjsl);
        kidsjumpsuitxlarge = getResources().getString(R.string.kjsxl);

        kidsfrocksmall = getResources().getString(R.string.kfs);
        kidsfrockmedium = getResources().getString(R.string.kfm);
        kidsfrocklarge = getResources().getString(R.string.kfl);
        kidsfrockxlarge = getResources().getString(R.string.kfxl);

        kidstshirtsmall = getResources().getString(R.string.ktss);
        kidstshirtmedium = getResources().getString(R.string.ktsm);
        kidstshirtlarge = getResources().getString(R.string.ktsl);
        kidstshirtxlarge = getResources().getString(R.string.ktsxl);

        kidsshirtsmall = getResources().getString(R.string.kss);
        kidsshirtmedium = getResources().getString(R.string.ksm);
        kidsshirtlarge = getResources().getString(R.string.ksl);
        kidsshirtxlarge = getResources().getString(R.string.ksxl);


        menspantsmall = getResources().getString(R.string.mps);
        menspantmedium = getResources().getString(R.string.mpm);
        menspantlarge= getResources().getString(R.string.mpl);
        menspantxlarge = getResources().getString(R.string.mpxl);
        menspantxxlarge = getResources().getString(R.string.mpxxl);

        menstshirtsmall = getResources().getString(R.string.mtss);
        menstshirtmedium = getResources().getString(R.string.mtsm);
        menstshirtlarge= getResources().getString(R.string.mtsl);
        menstshirtxlarge = getResources().getString(R.string.mtsxl);
        menstshirtxxlarge = getResources().getString(R.string.mtsxxl);

        mensshirtsmall = getResources().getString(R.string.mss);
        mensshirtmedium = getResources().getString(R.string.msm);
        mensshirtlarge= getResources().getString(R.string.msl);
        mensshirtxlarge = getResources().getString(R.string.msxl);
        mensshirtxxlarge = getResources().getString(R.string.msxxl);

        womenpantxsmall =  getResources().getString(R.string.wpxs);
        womenpantsmall =  getResources().getString(R.string.wps);
        womenpantmedium =  getResources().getString(R.string.wpm);
        womenpantlarge =  getResources().getString(R.string.wpl);
        womenpantxlarge =  getResources().getString(R.string.wpxl);
        womenpantxxlarge =  getResources().getString(R.string.wpxxl);

        womenjumpsuitxsmall =  getResources().getString(R.string.wjsxs);
        womenjumpsuitsmall =  getResources().getString(R.string.wjss);
        womenjumpsuitmedium =  getResources().getString(R.string.wjsm);
        womenjumpsuitlarge =  getResources().getString(R.string.wjsl);
        womenjumpsuitxlarge =  getResources().getString(R.string.wjsxl);
        womenjumpsuitxxlarge =  getResources().getString(R.string.wjsxxl);


        womenfrockxsmall =  getResources().getString(R.string.wfxs);
        womenfrocksmall =  getResources().getString(R.string.wfs);
        womenfrockmedium =  getResources().getString(R.string.wfm);
        womenfrocklarge =  getResources().getString(R.string.wfl);
        womenfrockxlarge =  getResources().getString(R.string.wfxl);
        womenfrockxxlarge =  getResources().getString(R.string.wfxxl);


        womentshirtxsmall =  getResources().getString(R.string.wtsxs);
        womentshirtsmall =  getResources().getString(R.string.wtss);
        womentshirtmedium =  getResources().getString(R.string.wtsm);
        womentshirtlarge =  getResources().getString(R.string.wtsl);
        womentshirtxlarge =  getResources().getString(R.string.wtsxl);
        womentshirtxxlarge =  getResources().getString(R.string.wtsxxl);

        womenshirtxsmall =  getResources().getString(R.string.wsxs);
        womenshirtsmall =  getResources().getString(R.string.wss);
        womenshirtmedium =  getResources().getString(R.string.wsm);
        womenshirtlarge =  getResources().getString(R.string.wsl);
        womenshirtxlarge =  getResources().getString(R.string.wsxl);
        womenshirtxxlarge =  getResources().getString(R.string.wsxxl);
    }


    private void setadaptertype(String code ,String typetitle , String type )
    {
        if(code.substring(2).equals(getResources().getString(R.string.p)))
        {
            pantrootlayout.setVisibility(View.VISIBLE);
            sizepanttitle.setText(typetitle);
            setspinnerscrolling(pantlengthspinner);
            setspinnerscrolling(pantwaistspinner);
            setPantadap(code+type);
        }

        if(code.substring(2).equals(getResources().getString(R.string.f)))
        {
            frockrootlayout.setVisibility(View.VISIBLE);
            sizefrocktitle.setText(typetitle);
            setspinnerscrolling(frocklengthspinner);
            setspinnerscrolling(frockwaistspinner);
            setspinnerscrolling(frockchestspinner);
            setspinnerscrolling(frockhipspinner);
            setFrockadap(code+type);
        }

        if(code.substring(2).equals(getResources().getString(R.string.js)))
        {
            jumpsuitrootlayout.setVisibility(View.VISIBLE);
            sizejumpsuittitle.setText(typetitle);
            setspinnerscrolling(jumpsuitlengthspinner);
            setspinnerscrolling(jumpsuitwaistspinner);
            setspinnerscrolling(jumpsuitchestspinner);
            setspinnerscrolling(jumpsuithipspinner);
            setJumpsuitadap(code+type);
        }

        if(code.substring(2).equals(getResources().getString(R.string.ts)))
        {
            tshirtrootlayout.setVisibility(View.VISIBLE);
            sizetshirttitle.setText(typetitle);
            setspinnerscrolling(tshirtlengthspinner);
            setspinnerscrolling(tshirtchestspinner);
            setspinnerscrolling(tshirtshoulderspinner);
            setTshirtadap(code+type);
        }

        if(code.substring(2).equals(getResources().getString(R.string.s)))
        {
            shirtrootlayout.setVisibility(View.VISIBLE);
            sizeshirttitle.setText(typetitle);
            setspinnerscrolling(shirtlengthspinner);
            setspinnerscrolling(shirtchestspinner);
            setspinnerscrolling(shirtsleevespinner);
            setShirtadap(code+type);
        }
    }


    private void getadaptertype(String code ,String typetitle , String headertxt ,String cattype)
    {

        String adaptypedata [] = code.split(";");
        String typeTitledata [] = typetitle.split(";");

        for (int pos = 0 ; pos < adaptypedata.length ; pos++)
        {
            opensizedetails(adaptypedata[pos],typeTitledata[pos], headertxt , cattype);
        }

    }


    private void setlikes()
    {
        if(Long.valueOf(productlikes) > 999)
        {
            productlikestxt.setText(new Likescounter().setcounts(Long.valueOf(productlikes))+" likes");
        }

        else
        {
            productlikestxt.setText(productlikes+" likes");
        }
    }


    private void setlikecountplus()
    {
        DatabaseReference likeref = database.getReference("datarecords/Product/Productdetails/"+productidtxt+"/product_likes");
        likeref.setValue(""+(Long.valueOf(productlikes)+1));
        productlikes=""+(Long.valueOf(productlikes)+1);
        setlikes();
        setlikedval(false);

    }

    private void setlikecountminus()
    {
        DatabaseReference likeref = database.getReference("datarecords/Product/Productdetails/"+productidtxt+"/product_likes");
        likeref.setValue(""+(Long.valueOf(productlikes)-1));
        productlikes =  ""+(Long.valueOf(productlikes)-1);
        setlikes();
        setlikedval(true);
    }

    private  void addtowishlist()
    {

        ProgressDialog progressdialog = new ProgressDialog(getApplicationContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        final DatabaseReference wishlistref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlist");
        wishlistref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(productidtxt))
                {
                    progressdialog.dismiss();
                    AlertDialog.Builder message = new AlertDialog.Builder(Productdetail.this);
                    message.setTitle("Message");
                    AlertDialog alert;
                            message.setMessage("Item Already Added to Wishlist");
                            message.setCancelable(true);

                            message.setNegativeButton("Ok",null);
                    alert = message.create();
                    alert.show();
                }

                else
                {
                    Map<String, String> productData = new HashMap<String, String>();

                    productData.put("product_id",productidtxt);
                    productData.put("name", productnametxt);
                    productData.put("price", productpricetxt);
                    productData.put("dress_code" , productdresscode);
                    productData.put("dress_sizetitle" , sizetypetitle);
                    productData.put("no_of_views" , productnoofviewstxt);
                    productData.put("size_available" , productsizeavailtxt);
                    productData.put("description" , productdesctxt);
                    productData.put("total_ratings" , producttotalratingstxt);
                    productData.put("product_likes" , productlikes);
                    productData.put("imgurl1" , productimgurl1txt);
                    productData.put("imgurl2" , productimgurl2txt);
                    productData.put("imgurl3" , productimgurl3txt);
                    productData.put("imgurl4" , productimgurl4txt);
                    productData.put("Productparent_type" , Productparent_typetxt);
                    productData.put("retailercode" , retailerid);


                    wishlistref.child(productidtxt).setValue(productData);
                    setwishlistcount();
                    progressdialog.dismiss();
                    Toast.makeText(Productdetail.this , "Sucessfully Added to Wishlist" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void removeitemfromwishlist()
    {

        DatabaseReference getwishlistcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlist");
        getwishlistcountref.child(productidtxt).setValue(null);
        mywishlistdecreaseitemcount();

    }

    private void mywishlistdecreaseitemcount()
    {
        DatabaseReference getwishlistcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        getwishlistcountref.setValue(""+(wishlistcount - 1));

    }


    private void getwishlistcount()
    {
        DatabaseReference getwishlistcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        getwishlistcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wishlistcount = Long.valueOf(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setwishlistcount()
    {
        DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        wishlistref.setValue(wishlistcount+1);
    }

    private void setspinnerscrolling(Spinner spinner)
    {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
    }



    private void checkproductlikes()
    {
        final DatabaseReference likesref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/productliked");
        likesref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(productidtxt))
                {
                   productlikebtn.setLiked(true);
                }

                else
                {
                    productlikebtn.setLiked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setlikedval(final boolean checkfunctype)
    {
        final DatabaseReference likesref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/productliked");
        if(checkfunctype == true)
        {
            likesref.child(productidtxt).setValue(null);
        }

        else
        {
            likesref.child(productidtxt).setValue("Liked");
        }
    }


    private void getcartitemcount()
    {
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartitemcount");
        itemcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mycartitem = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setcartitemcount()
    {
        String itemcount = ""+(mycartitem+1);
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartitemcount");
        itemcountref.setValue(itemcount);
    }




    private  void setdatatocart( String tagname , final String duration , final String sizedata  , final String quant)
    {
        final DatabaseReference cartref = database.getReference("datarecords/deckoutusers/Client/"+userid+"/mycart");
        cartref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(productidtxt))
                {

                    AlertDialog.Builder message = new AlertDialog.Builder(Productdetail.this);
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("Item Already Added to Cart");
                    message.setCancelable(true);

                    message.setNegativeButton("Ok",null);
                    alert = message.create();
                    alert.show();
                }

                else
                {

                    Map<String, String> productData = new HashMap<String, String>();

                    productData.put("product_id",productidtxt);
                    productData.put("name", productnametxt);
                    productData.put("price", productpricetxt);
                    productData.put("dress_code" , productdresscode);
                    productData.put("dress_sizetitle" , sizetypetitle);
                    productData.put("no_of_views" , productnoofviewstxt);
                    productData.put("size_available" , productsizeavailtxt);
                    productData.put("description" , productdesctxt);
                    productData.put("Productparent_type",Productparent_typetxt);
                    productData.put("duration" , duration);
                    productData.put("sizedata" , sizedata);
                    productData.put("dressquant",quant);
                    productData.put("total_ratings" , producttotalratingstxt);
                    productData.put("product_likes" , productlikes);
                    productData.put("imgurl1" , productimgurl1txt);
                    productData.put("imgurl2" , productimgurl2txt);
                    productData.put("imgurl3" , productimgurl3txt);
                    productData.put("imgurl4" , productimgurl4txt);
                    productData.put("sizecattype" , sizecattype);
                    productData.put("sizeselected" , sizeheadertxt);
                    productData.put("retailercode" , retailerid);


                    cartref.child(productidtxt).setValue(productData);
                    Toast.makeText(Productdetail.this , "Sucessfully Added to Cart" , Toast.LENGTH_SHORT).show();
                    setcartitemcount();
                    removeitemfromwishlist();
                    settagname(tagname ,productidtxt );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    public class getsizecatdata extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            getadaptertype(productdresscode , sizetypetitle,sizeheadertxt,sizecattype);
        }

        @Override
        protected Object doInBackground(Object[] params) {

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
            sizedetailpannel.show();

            Window window = sizedetailpannel.getWindow();
            window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }


    private void settagname(String tagnametxt ,String pid)
    {

        tagnametxt = "Tagname : "+tagnametxt.replace("Tagname : " , "");
        final DatabaseReference tagnameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pid+"/tagname");
        tagnameref.setValue(tagnametxt);


        finish();
    }


    private void checkproductavail(String checkretailerid)
    {
        final DatabaseReference likesref = database.getReference("blockedusers/phno");
        likesref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(checkretailerid))
                {
                    setproductavail(true);
                }

                else
                {
                    setproductavail(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getuserphnumber()
    {
        DatabaseReference retailerphnoref = database.getReference("datarecords/deckoutusers/Retailer/"+retailerid+"/Mobno");
        retailerphnoref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setretailerphnumber(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setretailerphnumber(String retailerphno)
    {
        this.retailerphno = retailerphno;

        checkproductavail(retailerid);
    }

    private  void setproductavail(Boolean checkpoductavial)
    {
        if (checkpoductavial != false)
        {
            orderbtn.setBackgroundResource(R.drawable.resendbtnred);
            orderbtn.setText("Product not avialable");
        }

        else
        {
            orderbtn.setBackgroundResource(R.drawable.buttonroundcornerblack);
            orderbtn.setText("Rent Now");
        }

    }


    private void openguestusermsg()
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Productdetail.this);
        builder1.setTitle("Message");
        builder1.setMessage("\nplease register or login to acess this service.\n\nDo you want to register or Login ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Productdetail.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
