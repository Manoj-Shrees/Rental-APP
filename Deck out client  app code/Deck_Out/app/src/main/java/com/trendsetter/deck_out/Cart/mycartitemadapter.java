package com.trendsetter.deck_out.Cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.trendsetter.deck_out.Calender.customviews.DateRangeCalendarView;
import com.trendsetter.deck_out.Productdetails.Productdetail;
import com.trendsetter.deck_out.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class mycartitemadapter extends FirebaseRecyclerAdapter<mycartgetdata ,mycartitemholder> {

    Context context;
    int mycartitem = 0,lastmonth =0 ;
    String userid;
    Map  <String  , String>  orderdata;
    Map <String , Map<String , String > >suborderdata;
    private DateRangeCalendarView calendar;
    String currentdate;



    public mycartitemadapter(@NonNull FirebaseRecyclerOptions<mycartgetdata> options, Context context, String userid) {
        super(options);
        this.context = context;
        this.userid = userid;
        getcartitemcount();
        orderdata = new HashMap<>();
        suborderdata = new HashMap<>();
        SharedPreferences sp = context.getSharedPreferences("currdateonl9", MODE_PRIVATE);
        this.currentdate = sp.getString("nowdate","");
    }


    @Override
    public mycartitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycartitemlayout, parent, false);

        return new mycartitemholder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull final mycartitemholder holder, int position, @NonNull final mycartgetdata model) {


        ScaleDrawable deleteicon = setdrawableicon(R.drawable.ic_delete);
        holder.deletebtn.setCompoundDrawables(deleteicon.getDrawable(), null, null, null);

        ScaleDrawable detailicon = setdrawableicon(R.drawable.ic_details);
        holder.detailbtn.setCompoundDrawables(detailicon.getDrawable(), null, null, null);

        if (position == 0)
        {
            orderdata.put("productname" , model.getName());
            orderdata.put("retailerid" , model.getRetailercode());

        }

        Map <String , String > productdatalist = new HashMap<>();
        productdatalist.put("productimgurl"  , model.getImgurl1());
        productdatalist.put("productname" , model.getName());
        productdatalist.put("productprice" , model.getPrice());
        productdatalist.put("producttagname"  , model.getTagname());
        productdatalist.put("producttypeandsize" , model.getSizedata());

        suborderdata.put( model.getName() , productdatalist);


        String orderdate = model.getDuration().substring(0 , model.getDuration().indexOf("t") - 1).trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = null, d2 = null;
        try {
             d1 = sdf.parse(currentdate);
             d2 = sdf.parse(orderdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (d2.before(d1) )
        {
           holder.setdurationcolor(0);
           holder.setduration(model.getDuration()+"\n * please change date *");
        }

        else
        {
            holder.setdurationcolor(1);
            holder.setduration(model.getDuration());
        }

        holder.setdata(context, model.getProduct_id(), model.getDress_code(), model.getDress_sizetitle(), model.getSizedata(), model.getSize_available(), model.getSizeselected(), model.getSizecattype());
        holder.setProductimage(model.getImgurl1());
        holder.setproductname(model.getName());
        holder.setProductprice(model.getPrice());
        holder.setProducttotalprice(model.getPrice(), model.getDressquant() , model.getProduct_id() , userid);
        holder.setProducttype(model.getProductparent_type().replace("#", ""));
        holder.setdressquant(model.getDressquant());
        holder.mycartitemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( holder.duration.getCurrentTextColor() != Color.RED)
                {
                    checkselecteditemcount(model.getDuration() , holder , model );

                }


            }
        });
        holder.detailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Productdetail.class)
                        .putExtra("productname", model.getName())
                        .putExtra("productdesc", model.getDescription())
                        .putExtra("productdresscode", model.getDress_code())
                        .putExtra("productsizetitle", model.getDress_sizetitle())
                        .putExtra("productid", model.getProduct_id())
                        .putExtra("productnoofviews", model.getNo_of_views())
                        .putExtra("productsizeavail", model.getSize_available())
                        .putExtra("productprice", model.getPrice())
                        .putExtra("Productparent_type", model.getProductparent_type())
                        .putExtra("producttotalratings", model.getTotal_ratings())
                        .putExtra("product_likes", model.getProduct_likes())
                        .putExtra("productimgurl1", model.getImgurl1())
                        .putExtra("productimgurl2", model.getImgurl2())
                        .putExtra("productimgurl3", model.getImgurl3())
                        .putExtra("retailerid"  , model.getRetailercode())
                        .putExtra("productimgurl4", model.getImgurl4())
                        .putExtra("retailerid" , model.getRetailercode())

                );
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
                deleteitemmsg.setTitle("Delete Item ");
                deleteitemmsg.setMessage("\nDo you want to Delete this Item ?");
                deleteitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletedata(model.getProduct_id(),holder.producttotalprice.getText().toString().replace("₹ ", "")  , model.getPrice(), model.getDressquant());
                    }
                });
                deleteitemmsg.setNegativeButton("No", null);
                deleteitemmsg.show();

            }
        });

        holder.sizedetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.opensizepannel();
            }
        });

        holder.durationandtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendurationselector(model.getName() , model.getPrice() , model.getSizeselected() ,  model.getSizedata() , model.getTagname() , model.getProduct_id());
            }
        });

        holder.quantspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (holder.quantspinner.getSelectedItem().equals("More")) {
                    final Dialog quantmoretpannel = new Dialog(context);
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
                            holder.quantspinner.setSelection(0);
                        }
                    });

                    final TextView quantinput = quantmoretpannel.findViewById(R.id.quantityinput);
                    Button getquantbtn = quantmoretpannel.findViewById(R.id.quantitysubmitbtn);

                    getquantbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String[] newquant = {"1", "2", "3", "4", "5", quantinput.getText().toString().trim(), "More"};
                            ArrayAdapter<CharSequence> newquantAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, newquant);
                            newquantAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            holder.quantspinner.setAdapter(newquantAdapter);
                            holder.quantspinner.setSelection(5);
                            quantmoretpannel.dismiss();
                            setquantdata(model.getProduct_id(), holder.quantspinner.getSelectedItem().toString());
                            getcarttotalprice(0 , holder.producttotalprice.getText().toString().replace("₹ ", "") ,model.getPrice(), holder.quantspinner.getSelectedItem().toString());
                            holder.setProducttotalprice(model.getPrice(), holder.quantspinner.getSelectedItem().toString() , model.getProduct_id() , userid);
                        }
                    });

                    quantmoretpannel.show();
                    Window window = quantmoretpannel.getWindow();
                    window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }

                else {
                    setquantdata(model.getProduct_id(), holder.quantspinner.getSelectedItem().toString());
                    getcarttotalprice(0 , holder.producttotalprice.getText().toString().replace("₹ ", "") ,model.getPrice(), holder.quantspinner.getSelectedItem().toString());
                    holder.setProducttotalprice(model.getPrice(), holder.quantspinner.getSelectedItem().toString() , model.getProduct_id() , userid );
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void setcarttotalprice(int opttype, String newprice , Long oldprice)
    {

        String itemtotalprice ;

        if (opttype == 0)
        {
            itemtotalprice = ""+(oldprice+Long.parseLong(newprice));
        }

        else
        {
            itemtotalprice = ""+(oldprice-Long.parseLong(newprice));
        }


        DatabaseReference itemtotalpriceref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycarttotalprice");
        itemtotalpriceref.setValue(itemtotalprice);

    }

    private void getcarttotalnewprice( int type , String newprice)
    {
        DatabaseReference itemtotalpriceref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycarttotalprice");
        itemtotalpriceref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long oldprice = Long.parseLong(dataSnapshot.getValue().toString());
                setcarttotalprice(type , newprice , oldprice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private ScaleDrawable setdrawableicon(int drawableicon) {

        Drawable drawable = ContextCompat.getDrawable(context, drawableicon);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.5),
                (int) (drawable.getIntrinsicHeight() * 0.5));
        return new ScaleDrawable(drawable, 0, 20, 20);

    }

    private void setquantdata(String ref, String quant) {
        DatabaseReference quantref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycart/" + ref + "/dressquant");
        quantref.setValue(quant);
    }

    private void deletedata(String ref, String previousprice , String price, String quant) {
        DatabaseReference quantref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycart/" + ref);
        quantref.setValue(null);
        setcartitemcount();
        getcarttotalprice( 1 , previousprice ,price , quant);
    }

    private void getcartitemcount() {
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycartitemcount");
        itemcountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mycartitem = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getselecteditemcount(int type)
    {
        DatabaseReference selecteditemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        selecteditemcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long itemcount = Long.parseLong(dataSnapshot.getValue().toString());
                setselecteditemcount(type , itemcount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void setselecteditemcount(int type , Long itemcountvalue)
    {
        if (type == 0)
        {
            itemcountvalue+=1;
        }

        else
        {
            itemcountvalue-=1;
        }

        DatabaseReference selecteditemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        selecteditemcountref.setValue(""+itemcountvalue);
    }


    private void setcartitemcount() {
        String itemcount = "" + (mycartitem - 1);
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycartitemcount");
        itemcountref.setValue(""+itemcount);
    }

    private void setcarttotalprice(String mycarttotalprice , String previousprice , String price, String quant) {
        String itemcount = ""+ ((Integer.parseInt(mycarttotalprice) + (Integer.parseInt(quant) * Integer.parseInt(price)) ) - Integer.parseInt(previousprice));
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycarttotalprice");
        itemcountref.setValue(""+itemcount);
    }



    private void getcarttotalprice(int typeno , String previousprice, String price , String quant) {
        DatabaseReference itemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycarttotalprice");
        itemcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (typeno == 0)
                setcarttotalprice(dataSnapshot.getValue().toString() , previousprice , quant , price) ;

                else
                    deleteprice(dataSnapshot.getValue().toString() , price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setcarttotalpriceupated(String newprice , Long oldprice)
    {
        String itemtotalprice = ""+(oldprice+Long.parseLong(newprice));
        DatabaseReference itemtotalpriceref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycarttotalprice");
        itemtotalpriceref.setValue(itemtotalprice);
    }

    private void deleteprice(String totalprice , String price)
    {
        String itemcount = ""+ (Long.parseLong(totalprice)  - Long.parseLong(price));
        DatabaseReference itemtotalpriceref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycarttotalprice");
        itemtotalpriceref.setValue(itemcount);
    }


    private void checkmycartitemselectionlist(String id)
    {
        DatabaseReference  checkidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditems");
        checkidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String str ;

                if (dataSnapshot.getValue().toString().equals("N/A"))
                {
                    str = id+";";
                }

               else
                {
                    str = dataSnapshot.getValue().toString();
                    str += id+";";
                }

                setycartitemselectionlist(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void removemycartitemselectionlist(String id) {
        DatabaseReference checkidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + userid + "/mycartselecteditems");
        checkidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String str = dataSnapshot.getValue().toString();
                str = str.replace(id+";" , "");
                if (str.length()<=0)
                {
                    str = "N/A";
                }
                setycartitemselectionlist(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


        private void setycartitemselectionlist(String id)
    {
        DatabaseReference  setidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditems");
        setidref.setValue(id);

    }


    private void setOrderdata(String idtxt , String datatxt)
    {
       orderdata.put(idtxt , datatxt);
    }


    private void opendurationselector(String productname , String price , String selectedsizetxt ,   String sizetxtdata , String tagnametxt , String pidtxt)
    {
        final Dialog orderpreviewdialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
        selectedsize.setText(selectedsizetxt);
        TextView productnametxt = orderpreviewdialog.findViewById(R.id.productnametxt);
        TextView productpricetxt = orderpreviewdialog.findViewById(R.id.dresspricetxt);
        final TextView sizedata = orderpreviewdialog.findViewById(R.id.dateselectorsizetxt);
        productnametxt.setText(productname);
        productpricetxt.setText("₹ "+price);
        sizedata.setText(Html.fromHtml(sizetxtdata));
        Button updatebtn = orderpreviewdialog.findViewById(R.id.proceedtopaymentbtn);
        updatebtn.setText("Update");
        final RelativeLayout rootlayout = orderpreviewdialog.findViewById(R.id.dateselectorroot);
        EditText tagname = orderpreviewdialog.findViewById(R.id.dresstagname);
        tagname.setText(tagnametxt);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar.getStartDate() == null) {
                    Snackbar snackbar = Snackbar
                            .make(rootlayout, "Please Select Duration from Calender", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(Color.RED);
                    TextView textView = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }

                else {

                    if (tagname.getText().length() == 0)
                    {
                        tagname.setError("is Empty");
                    }

                    else
                    {
                        Date startdate = calendar.getStartDate().getTime();
                        Date enddate = calendar.getEndDate().getTime();
                        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                        String dur = dateformat.format(startdate) + " to " + dateformat.format(enddate);
                        updateduration(dur , pidtxt , tagname.getText().toString());
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



    private void updateduration(String duration , String pid , String tagnametxt)
    {
        final DatabaseReference durationref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pid+"/duration");
        durationref.setValue(duration);

        updatetagname(tagnametxt , pid);
    }


    private void updatetagname(String tagnametxt ,String pid)
    {

        tagnametxt = "Tagname : "+tagnametxt.replace("Tagname : " , "");
        final DatabaseReference tagnameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pid+"/tagname");
        tagnameref.setValue(tagnametxt);
    }



    private void setmyorderdate(String orderdate)
    {
        final DatabaseReference orderdateref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteddate");
        orderdateref.setValue(orderdate);
    }


    private void  checkselecteditemcount(String orderdate , mycartitemholder holder , mycartgetdata model )
    {
        final DatabaseReference checkselecteditemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        checkselecteditemcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (Long.parseLong(dataSnapshot.getValue().toString()) > 0)
                {
                    checksamedate(dataSnapshot.getValue().toString() , orderdate , holder ,  model );
                }

                else
                {

                    setselecetitemdatas(Long.parseLong(dataSnapshot.getValue().toString()) , orderdate , holder , model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setselecetitemdatas( Long itemcount , String orderdate , mycartitemholder holder , mycartgetdata model)
    {
        if (holder.mycartitemcheckbox.isChecked() == true)
        {
            getcarttotalnewprice(1,holder.producttotalprice.getText().toString().replace("₹ " , ""));
            holder.mycartitemcheckbox.setChecked(false);
            holder.quantspinner.setEnabled(false);
            removemycartitemselectionlist(model.getProduct_id());
            getselecteditemcount(1);
            if (itemcount -1 == 0)
            {
                setmyorderdate("N/A");
            }

        }

        else
        {
            getcarttotalnewprice(0 ,holder.producttotalprice.getText().toString().replace("₹ " , ""));
            holder.mycartitemcheckbox.setChecked(true);
            holder.quantspinner.setEnabled(true);
            checkmycartitemselectionlist(model.getProduct_id());
            getselecteditemcount(0);
            setmyorderdate(orderdate);
        }
    }


    private void checksamedate( String orderitemcount , String selecteditemdate , mycartitemholder holder , mycartgetdata model )
    {
        final DatabaseReference checkdateref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteddate");
        checkdateref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue().toString().equals(selecteditemdate))
                {

                    setselecetitemdatas(Long.parseLong(orderitemcount) , selecteditemdate , holder , model);

                }

                else
                {
                    AlertDialog.Builder itemselectedsmsg = new AlertDialog.Builder(context);
                    itemselectedsmsg.setTitle("Message");
                    itemselectedsmsg.setMessage("\nPlease select the item of same duration.");
                    itemselectedsmsg.setPositiveButton("OK",null);
                    itemselectedsmsg.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

