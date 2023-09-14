package com.trendsetter.deck_out.Orders;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class myorderitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg;
    CardView cardView;
    LinearLayout trackorderbtn , cancelorderbtn ;
    TextView ordernametxt , ordertypetxt , orderpricetxt , orderquantntypetxt , orderstatustxt , orderOrdernotxt ;
    RelativeLayout trackandcancedlayout , myorderdetaillayout;

    public myorderitemholder(View itemView) {
        super(itemView);


        cardView = itemView.findViewById(R.id.myorderscard);

        trackorderbtn = itemView.findViewById(R.id.trackorderbtn);
        cancelorderbtn = itemView.findViewById(R.id.cancelorderbtn);

        itemimg = itemView.findViewById(R.id.myorderimage);

        ordernametxt = itemView.findViewById(R.id.ordername);
        ordertypetxt = itemView.findViewById(R.id.ordertype);
        orderpricetxt = itemView.findViewById(R.id.orderprice);
        orderquantntypetxt = itemView.findViewById(R.id.orderquantntype);
        orderstatustxt = itemView.findViewById(R.id.orderstatus);
        orderOrdernotxt = itemView.findViewById(R.id.orderOrderno);

        trackandcancedlayout = itemView.findViewById(R.id.trackandcancedlayout);
        myorderdetaillayout = itemView.findViewById(R.id.myorderdetaillayout);


    }




    public void opentrackorder(String orderitemname , String ordertype , String totalprice
            , ArrayList<String> trackdates , String orderno , String orderdeladdrs , Context context)
    {
        context.startActivity(new Intent(context, Trackorders.class).putExtra("itemname" , orderitemname).putExtra("itemtype" , ordertype)
                .putExtra("itemtprice" , totalprice).putExtra("itemtrackdates" , trackdates)
                .putExtra("itemorderno" , orderno).putExtra("itemorderdeladdrs" , orderdeladdrs));
    }


    public   void  cancelorder(String orderidtxt ,String  userid)
    {
        DatabaseReference trackclientupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Client/"+orderidtxt+"/iscancelled");
        trackclientupdatemsgref.setValue("#T");

        DatabaseReference trackretailerupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/Retailer/"+orderidtxt+"/iscancelled");
        trackretailerupdatemsgref.setValue("#T");

        getmyorderpendingntotalcount(userid);
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
    }


    private void setmyorderpendingcount(String uid , String itemcounttxt)
    {
        String itemcount = ""+(Long.parseLong(itemcounttxt) - 1);
        DatabaseReference ordercountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/myorderpendingcount");
        ordercountref.setValue(itemcount);
    }


    public void openordersublist(Context context , String orderidtxt)
    {
        context.startActivity(new Intent(context,Myorderproductlist.class).putExtra("orderidcode" ,orderidtxt ));

    }

}