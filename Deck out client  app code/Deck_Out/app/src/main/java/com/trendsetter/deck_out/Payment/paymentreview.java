package com.trendsetter.deck_out.Payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

import static android.content.Context.MODE_PRIVATE;

public class paymentreview extends Fragment {

    TextView reviewaddresstxt , addresseditbtn , myorderdatedurtxt , myordersizequanttxt ,
            myorderpricetxt , myordernametxt , myordertypetxt , mycartpricedetailsnoofitemstxt
    , mycartpricedetailpricetxt ,  mycartpricedetailsecuritypluspricetxt  ;
    String userid;
    SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paymentreviewlayout, container, false);
        reviewaddresstxt = view.findViewById(R.id.reviewaddresstxt);
        addresseditbtn = view.findViewById(R.id.addresseditbtn);
        myorderdatedurtxt = view.findViewById(R.id.myorderdatedur);
        myordersizequanttxt = view.findViewById(R.id.myordersizequant);
        myordernametxt = view.findViewById(R.id.myordername);
        myorderpricetxt = view.findViewById(R.id.myorderprice);
        myordertypetxt = view.findViewById(R.id.myordertype);
        mycartpricedetailsnoofitemstxt = view.findViewById(R.id.mycartpricedetailsnoofitems);
        mycartpricedetailpricetxt = view.findViewById(R.id.mycartpricedetailprice);
        mycartpricedetailsecuritypluspricetxt = view.findViewById(R.id.mycartpricedetailsecurityplusprice);

        sp=getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
        addresseditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                /*transaction.setCustomAnimations(R.anim.slide_right_enter, R.anim.fade_out);*/
                transaction.replace(R.id.paymentfragcontainer, new paymentfragaddress());
                transaction.addToBackStack(null).commit();
                getActivity().setTitle("Confrim");
            }
        });

        setselectedadress();
        setproductduration();
        setproductprice();
        getproductid();

        return view;
    }



    private void setselectedadress()
    {
        SharedPreferences addressrefrence = getActivity().getSharedPreferences("deckoutselectedaddrs", MODE_PRIVATE);
        String addrstxt =(addressrefrence.getString("addrs", ""));

        reviewaddresstxt.setText(Html.fromHtml(addrstxt));

    }


    private void setproductduration()
    {
        DatabaseReference selecteddate = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteddate");
        selecteddate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myorderdatedurtxt.setText("Rent from "+dataSnapshot.getValue().toString().replace("to" , "till"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void setproductsize( String pidtxt , String quanttxt)
    {
        DatabaseReference selecteditemtprice = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pidtxt+"/sizeselected");
        selecteditemtprice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                String[] arrOfStr = str.split(":");
                myordersizequanttxt.setText("Size : "+arrOfStr[1].trim()+"   |    Quanty : "+quanttxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setproductquant(String pidtxt)
    {
        DatabaseReference selecteditemtprice = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        selecteditemtprice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setproductsize(pidtxt , dataSnapshot.getValue().toString());
                mycartpricedetailsnoofitemstxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void setproductprice()
    {
        DatabaseReference selecteditemtprice = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycarttotalprice");
        selecteditemtprice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myorderpricetxt.setText("₹ "+dataSnapshot.getValue().toString()+"  for 5 days ");
                mycartpricedetailpricetxt.setText("₹ "+dataSnapshot.getValue().toString());
                mycartpricedetailsecuritypluspricetxt.setText("₹ "+(Long.parseLong(dataSnapshot.getValue().toString())+2000));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setproductname(String pidtxt)
    {
        DatabaseReference selecteditemtname = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pidtxt+"/name");
        selecteditemtname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myordernametxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setproducttype(String pidtxt)
    {
        DatabaseReference selecteditemtprice = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pidtxt+"/Productparent_type");
        selecteditemtprice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myordertypetxt.setText(dataSnapshot.getValue().toString().replace("#" , ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getproductid()
    {
        DatabaseReference selecteditemtprice = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditems");
        selecteditemtprice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                String[] arrOfStr = str.split(";");

                setproducttype(arrOfStr[0]);
                setproductname(arrOfStr[0]);
                setproductquant(arrOfStr[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
