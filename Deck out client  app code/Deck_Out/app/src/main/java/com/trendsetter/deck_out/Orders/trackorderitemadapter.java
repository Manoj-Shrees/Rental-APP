package com.trendsetter.deck_out.Orders;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trendsetter.deck_out.R;

import java.util.ArrayList;

public class trackorderitemadapter extends RecyclerView.Adapter<trackorederHolder>
{

    ArrayList<String> trackdates;

    trackorderitemadapter(ArrayList<String> trackdates)
    {
        this.trackdates = trackdates;

    }

    @Override
    public trackorederHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trackorderitem, parent, false);

        return  new trackorederHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(trackorederHolder holder, int position) {

        if(position==0)
        {
            holder.straightline.setBackgroundColor(Color.parseColor("#06d1c0"));
            holder.boxbg.setBackgroundResource(R.drawable.checkedbox);
            holder.trackdate.setText(trackdates.get(position));
        }

        if(position==1)
        {
            holder.tracktile.setText("Shipped");

            if (trackdates.get(position).equals(" ") )
            {
                holder.trackdate.setVisibility(View.GONE);
            }

            else
            {
                holder.trackdate.setText(trackdates.get(position));
                holder.straightline.setBackgroundColor(Color.parseColor("#06d1c0"));
                holder.boxbg.setBackgroundResource(R.drawable.checkedbox);
            }

        }

        if(position == 2)
        {
            holder.lastlistlayout.setVisibility(View.VISIBLE);
            holder.tracktile.setText("Out for delivery");

            if (trackdates.get(position).equals(" "))
            {
                holder.trackdate.setVisibility(View.GONE);
            }

            else
            {
                holder.trackdate.setText(trackdates.get(position));
                holder.straightline.setBackgroundColor(Color.parseColor("#06d1c0"));
                holder.boxbg.setBackgroundResource(R.drawable.checkedbox);
            }

            if (trackdates.get(position+1).contains("Arriving"))
            {
                holder.tracklbottomtitle.setText(trackdates.get(position+1));
            }

            else
            {
                holder.straightline.setBackgroundColor(Color.parseColor("#06d1c0"));
                holder.bottomboxbg.setBackgroundResource(R.drawable.checkedbox);
                holder.tracklbottomtitle.setText(trackdates.get(position+1));
            }
        }




    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}