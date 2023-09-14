package com.trendsetter.deck_out.Orders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class myorderitemadapter extends FirebaseRecyclerAdapter<myorderlistgetdata , myorderitemholder>
{

    Context context;
    SharedPreferences sp;
    String userid;

    public myorderitemadapter(@NonNull FirebaseRecyclerOptions<myorderlistgetdata> options , Context context) {
        super(options);
        this.context = context;
        sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
    }


    @Override
    public myorderitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myordersitemlayout, parent, false);

        return  new myorderitemholder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull myorderitemholder holder, int position, @NonNull myorderlistgetdata model) {

        holder.ordernametxt.setText(model.getName());
        holder.ordertypetxt.setText(model.getOrdertype());
        holder.orderpricetxt.setText("₹ "+model.getTprice()+" for 7 days");
        holder.orderquantntypetxt.setText(model.getSizenitems());
        holder.orderOrdernotxt.setText("Order no. - "+model.getId());

        holder.myorderdetaillayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.openordersublist(context , model.getId());
            }
        });

        if (model.getIscancelled().equals("#T"))

        {
            holder.orderstatustxt.setText("Order has been Cancelled");
            holder.orderstatustxt.setTextColor(Color.RED);
            holder.trackandcancedlayout.setVisibility(View.GONE);

        }

        else {

            holder.orderstatustxt.setText("Estimated till - " + model.getDuedate());

            holder.trackorderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> trackdates = gettrackdates(model.getTrackdate());
                    holder.opentrackorder(model.getName(), model.getOrdertype(), model.getTprice(), trackdates, model.getId(), model.getCust_addrs(), context);
                }
            });

            holder.cancelorderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (model.getIsorderconfirm().equals("#F"))

                    {
                        AlertDialog.Builder cancelitemmsg = new AlertDialog.Builder(context);
                        cancelitemmsg.setTitle("Delete Item ");
                        cancelitemmsg.setMessage("\nDo you want to Delete this Item ?");
                        cancelitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.cancelorder(model.getId() , userid);
                            }
                        });
                        cancelitemmsg.setNegativeButton("No", null);
                        cancelitemmsg.show();
                    }


                    else
                    {
                        AlertDialog.Builder cancelitemmsg = new AlertDialog.Builder(context);
                        cancelitemmsg.setTitle("Message ");
                        cancelitemmsg.setMessage("\n\nYou can't Cancel order After Confirmation \n\nNote : Contact Deck Out for Further Quieries (Legal >  Contact us ).");
                        cancelitemmsg.setPositiveButton("ok", null);
                        cancelitemmsg.show();
                    }

           }
            });


        }


    }



    private  ArrayList<String> gettrackdates(String trackdatestxt)
    {
        ArrayList<String> dates = new ArrayList<>();
        dates.clear();
        String datesdata [] = trackdatestxt.split(";");
        for (int pos = 0 ; pos < datesdata.length ; pos++)
        {
            dates.add(datesdata[pos]);
        }

        return dates;
    }
}
