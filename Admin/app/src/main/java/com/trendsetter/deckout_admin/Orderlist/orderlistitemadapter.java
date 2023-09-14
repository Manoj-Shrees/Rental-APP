package com.trendsetter.deckout_admin.Orderlist;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

import java.util.ArrayList;


public class orderlistitemadapter extends FirebaseRecyclerAdapter<orderlistgetdata , Orderlistitemholder>
{

    Context context;

    public orderlistitemadapter(@NonNull FirebaseRecyclerOptions<orderlistgetdata> options , Context context) {
        super(options);
        this.context = context;
    }


    @Override
    public Orderlistitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlistitemlayout, parent, false);

        return  new Orderlistitemholder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Orderlistitemholder holder, int position, @NonNull orderlistgetdata model) {

        holder.ordernametxt.setText(model.getName());
        holder.ordertypetxt.setText(model.getOrdertype());
        holder.orderpricetxt.setText("₹ "+model.getTprice()+" for 5 days");
        holder.orderquantntypetxt.setText(model.getSizenitems());
        holder.orderOrdernotxt.setText("Order no. - "+model.getId());


        if (model.getIscancelled().equals("#T"))

        {
            holder.orderstatustxt.setText("Order has been Cancelled");
            holder.orderstatustxt.setTextColor(Color.RED);
            holder.genbillbtn.setVisibility(View.GONE);
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
                    ArrayList<String> trackdates = gettrackdates(model.getTrackdate());
                    holder.orderstatus(context, trackdates, model.getId(), model.getIsorderconfirm(), model.getCust_phoneno(), model.getCust_name(), model.getDuedate());
                }
            });

            holder.genbillbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.openbilldetails(context, model.getId(), model.getDuedate(), model.getInitdate(), model.getCust_name(), model.getCust_phoneno(), model.getCust_addrs(), model.getPaymenttype(), model.getTprice());
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
