package com.trendsetter.deck_out.Payment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

import static android.content.Context.MODE_PRIVATE;


public class paymentproceed extends Fragment {


    TextView   proceedaddresstxt , netpayableamounttxt;
    RelativeLayout onlinepaymentbtn , codpaymentbtn;
    String userid , tpricetxt ,  durationtxt ,  phonetxt ,  usernametxt , useridtxt ,  useraddrstxt ,  emailidtxt , paymenttypetxt,
    selecteditemcodestxt ,  qauntstxt , paymnt_stattxt;
    SharedPreferences sp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paymentproceedlayout, container, false);
        sp=getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
        proceedaddresstxt = view.findViewById(R.id.proceedaddresstxt);
        netpayableamounttxt = view.findViewById(R.id.netpayableamount);
        codpaymentbtn = view.findViewById(R.id.paymentproceedcodbtn);
        codpaymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder codselectsmsg = new AlertDialog.Builder(getContext());
                codselectsmsg.setTitle("Message");
                codselectsmsg.setMessage("\nDo you want to select payment as Cash on Delivery ?");
                codselectsmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        paymenttypetxt = "Postpaid (COD)";
                        paymnt_stattxt = "Due Payment - ₹ "+tpricetxt;
                        startpayment();
                    }
                });

                codselectsmsg.setNegativeButton("NO", null);

                codselectsmsg.show();
            }
        });

        onlinepaymentbtn = view.findViewById(R.id.paymentproceedonlinepayment);
        onlinepaymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymenttypetxt = "Prepaid (online)";
                paymnt_stattxt = "Prepaid - ₹ "+tpricetxt;
                startpayment();
            }
        });


        setselectedadress();
        setproductprice();
        getpaymenttopoceeddata();

        return view;
    }



    private void startpayment()
    {

        senddatatopaymentpocess(tpricetxt , durationtxt , phonetxt , usernametxt , useridtxt , useraddrstxt , emailidtxt , paymenttypetxt
                                , selecteditemcodestxt , qauntstxt ,  paymnt_stattxt);

    }

    private void senddatatopaymentpocess(String tpricetxt , String durationtxt , String phonetxt , String usernametxt ,
                                         String useridtxt , String useraddrstxt , String emailidtxt , String paymenttypetxt,
                                         String selecteditemcodestxt , String  qauntstxt , String paymnt_stattxt)
    {
        startActivity(new Intent(getContext() , Redirecttopayment.class).putExtra("redirecttopayamount" , tpricetxt)
                     .putExtra("redirecttopayduration" , durationtxt).putExtra("redirecttopayphone" , phonetxt)
                     .putExtra("redirecttopayusername" , usernametxt).putExtra("redirecttopayuserid" , useridtxt)
                     .putExtra("redirecttopayuseraddrs" , useraddrstxt).putExtra("redirecttopayuseremail" ,emailidtxt)
                     .putExtra("redirecttopaypaymenttypetxt" , paymenttypetxt).putExtra("redirecttopayitemcodes" ,selecteditemcodestxt )
                     .putExtra("redirecttopayqaunts" , qauntstxt).putExtra("redirecttopaypaymnt_stattxt" , paymnt_stattxt));
    }

    private void getpaymenttopoceeddata()
    {
        DatabaseReference mycartdurationref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteddate");
        mycartdurationref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                durationtxt = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mycartphoneref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Mobno");
        mycartphoneref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phonetxt = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mycartusernameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Name");
        mycartusernameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usernametxt = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mycartuseridref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/userid");
        mycartuseridref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                useridtxt = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mycartemailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Emailid");
        mycartemailidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emailidtxt = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mycartselecteditemsref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditems");
        mycartselecteditemsref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                selecteditemcodestxt = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference mycartselecteditemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditemcount");
        mycartselecteditemcountref .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                qauntstxt = dataSnapshot.getValue().toString();
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
                netpayableamounttxt.setText("₹ "+(Long.parseLong(dataSnapshot.getValue().toString())+2000)+"  for 4 days incl security ");
                tpricetxt = " "+(Long.parseLong(dataSnapshot.getValue().toString())+2000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setselectedadress()
    {
        SharedPreferences addressrefrence = getActivity().getSharedPreferences("deckoutselectedaddrs", MODE_PRIVATE);
        String addrstxt =(addressrefrence.getString("addrs", ""));

        proceedaddresstxt.setText(Html.fromHtml(addrstxt));
        useraddrstxt = addrstxt;

    }
}
