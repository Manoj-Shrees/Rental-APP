package com.trendsetter.deck_out.Payment;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Extra.FCMMessageSender;

import java.util.HashMap;
import java.util.Map;

public class orderconfirmsetdata {

    int itemcounter = 0;
    String [] pcodes;


    public void setorderdate( String keytypetxt , String cust_addrstxt , String cust_idtxt , String cust_nametxt , String cust_phonenotxt ,
                              String  delivered_statustxt , String initdatetxt ,  String duedatetxt , String durationtxt  , String nametxt,
                              String paymenttypetxt , String oderidtxt , String ordertypetxt , String paymnt_stattxt , String retaileidtxt ,
                              String sizenitemstxt , String tpricetxt , String trackdatetxt, Map<String , HashMap<String , String>> sublistdata)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference orderdataref = database.getReference("orderdetaillist");
        Map<String, String> oderdata = new HashMap<String, String>();

        oderdata.put("cust_id",cust_idtxt);
        oderdata.put("cust_addrs" , cust_addrstxt);
        oderdata.put("cust_name" , cust_nametxt);
        oderdata.put("cust_phoneno",cust_phonenotxt);
        oderdata.put("name" , nametxt);
        oderdata.put("delivered_status", delivered_statustxt);
        oderdata.put("duedate" , duedatetxt);
        oderdata.put("duration" , durationtxt);
        oderdata.put("paymenttype" , paymenttypetxt);
        oderdata.put("isdelivered" , "#F");
        oderdata.put("isorderconfirm" , "#F");
        oderdata.put("iscancelled" , "#F");
        oderdata.put("initdate" , initdatetxt);
        oderdata.put("ordertype" , ordertypetxt);
        oderdata.put("paymnt_stat" , paymnt_stattxt);
        oderdata.put("retailerid" , retaileidtxt);
        oderdata.put("sizenitems" , sizenitemstxt);
        oderdata.put("tprice" , tpricetxt.trim());
        oderdata.put("trackdate" , trackdatetxt);
        oderdata.put("id" ,"DOORD"+oderidtxt);


        orderdataref.child(keytypetxt).child("DOORD"+oderidtxt).setValue(oderdata);

        orderdataref.child(keytypetxt).child("DOORD"+oderidtxt).child("ordersublist").setValue(sublistdata);

        setretailerdata(retaileidtxt ,  nametxt , initdatetxt , "DOORD"+oderidtxt , "Order "+"DOORD"+oderidtxt+"Initaited on "+initdatetxt+" by "+cust_nametxt+" ID "+cust_idtxt);
        getmyorderpendingntotalcount(cust_idtxt);
        setordercount(oderidtxt);
        setoderindex("DOORD"+oderidtxt , cust_idtxt);

    }


    private void setretailerdata(String retailercodelist , String ordernametxt , String initdatetxt , String orderitxt , String notifictxt)
    {
       String [] codelist = retailercodelist.split(" ");

       for (int  i =0; i<codelist.length ; i++)
       {
           setpushnotification(codelist[i] , ordernametxt);
           setretailerrnotification(initdatetxt , codelist[i] , orderitxt , notifictxt);
           getnotifictaioncount(codelist[i]);
       }


    }

    private void getnotifictaioncount(String retaileruseridtxt)
    {
        DatabaseReference retailernoticountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Retailer/"+retaileruseridtxt+"/notifictaioncount");
        retailernoticountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setnotificount(retaileruseridtxt  , dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setnotificount(String retaileruseridtxt , String count)
    {
        DatabaseReference retailernoticountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Retailer/"+retaileruseridtxt+"/notifictaioncount");
        retailernoticountref.setValue(""+(Integer.parseInt(count)+1));
    }

    private void setpushnotification(String retaileridtxt , String ordernametxt)
    {
       new FCMMessageSender().sendNotification(retaileridtxt , ordernametxt);
    }


    private void setretailerrnotification( String initdate , String retaileruseridtxt , String orderid ,  String notifictxt)
    {

        DatabaseReference retailernotiref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Retailer/"+retaileruseridtxt+"/ordernotifictaion");
        retailernotiref.child(orderid).child("notifictxt").setValue(notifictxt);
        retailernotiref.child(orderid).child("date").setValue(initdate);
    }


    public void deletefromcart(String pcodeslist , String uid  )
    {
        pcodes = pcodeslist.split(";");

        deletedata(uid);

    }

    private void deletedata(String uid )
    {
        String pid = pcodes[itemcounter];
        getmycartitemcount(uid , pid);

    }

    private void setoderindex(String oderidtxt , String custidtxt)
    {
        DatabaseReference orderindexref = FirebaseDatabase.getInstance().getReference("dataindex/orderindex");
        orderindexref.child(oderidtxt).setValue(custidtxt);
    }


    private void getmycartitemcount(String uid , String pid)
    {

        DatabaseReference mycartitemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/mycartitemcount");
        mycartitemcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmycartitemcount(dataSnapshot.getValue().toString() , uid , pid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setmycartitemcount(String itemcounttxt , String uid , String pid)
    {
        String itemcount = ""+(Long.parseLong(itemcounttxt) - 1);
        DatabaseReference mycartitemcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/mycartitemcount");
        mycartitemcountref.setValue(itemcount);

        DatabaseReference deletefroimmycartref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/mycart/"+pid);
        deletefroimmycartref.setValue(null);

        itemcounter+=1;

        if (itemcounter < pcodes.length)
        {
            deletedata(uid);
        }

    }


    private void setordercount (String orderid)
    {
        String itemcount = ""+(Long.parseLong(orderid) + 1);
        DatabaseReference ordercountref = FirebaseDatabase.getInstance().getReference("deckoutadmin/ordecount");
        ordercountref.setValue(itemcount);
    }



    private void getmyorderpendingntotalcount(String uid)
    {
        DatabaseReference pendingordercountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/myorderpendingcount");
        pendingordercountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmyorderpendingcount(uid , dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference totalordercountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/myordertotalcount");
        totalordercountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setmyordertotalcount(uid , dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setmyorderpendingcount(String uid , String itemcounttxt)
    {
        String itemcount = ""+(Long.parseLong(itemcounttxt) + 1);
        DatabaseReference ordercountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/myorderpendingcount");
        ordercountref.setValue(itemcount);
    }

    private void setmyordertotalcount(String uid , String itemcounttxt)
    {
        String itemcount = ""+(Long.parseLong(itemcounttxt) + 1);
        DatabaseReference ordercountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/myordertotalcount");
        ordercountref.setValue(itemcount);
    }


}
