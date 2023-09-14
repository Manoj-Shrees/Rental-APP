package com.trendsetter.deck_out.Orders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trendsetter.deck_out.R;


public class trackorederHolder extends RecyclerView.ViewHolder {

    LinearLayout lastlistlayout;
    View straightline;
    ImageView boxbg , bottomboxbg;
    TextView trackdate , tracktile , tracklbottomtitle;

    public trackorederHolder(View itemView, int viewType) {
        super(itemView);

        lastlistlayout = itemView.findViewById(R.id.bottom_delivered);
        straightline = itemView.findViewById(R.id.Straightline);
        boxbg = itemView.findViewById(R.id.boxbg);
        trackdate = itemView.findViewById(R.id.text_timeline_date);
        tracktile = itemView.findViewById(R.id.text_timeline_title);
        tracklbottomtitle = itemView.findViewById(R.id.text_timeline_title1);
        bottomboxbg = itemView.findViewById(R.id.trackorderbottombox);
    }
}