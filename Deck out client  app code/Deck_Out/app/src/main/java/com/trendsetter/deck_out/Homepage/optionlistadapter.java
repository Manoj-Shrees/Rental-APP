package com.trendsetter.deck_out.Homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.trendsetter.deck_out.R;

import java.util.ArrayList;

public class optionlistadapter extends BaseAdapter {
Context context;
ArrayList<String>  optionlistdata;
ArrayList<Integer>  optionimagelist;

    optionlistadapter(Context context, ArrayList<String> optionlistdata, ArrayList<Integer> optionimagelist)
    {
        this.context = context;
        this.optionlistdata = optionlistdata;
        this.optionimagelist = optionimagelist;
    }

    @Override
    public int getCount() {
        return optionlistdata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.nav_option_list_layout, parent, false);
        TextView optionname = convertView.findViewById(R.id.iconlisttxt);
        optionname.setText(optionlistdata.get(position));
        ImageView optionicon = convertView.findViewById(R.id.optionlisticon);
        optionicon.setImageResource(optionimagelist.get(position));
        return convertView;
    }
}
