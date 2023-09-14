package com.trendsetter.deck_out.Help;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Extra.ImageViewer;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class helpexplistadapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> menu;
    private HashMap<String, List<String>> listDataansChild;
    private HashMap<String, List<String>> listDataimgChild;

    public helpexplistadapter(Context context, HashMap<String, List<String>> listDataansChild,HashMap<String, List<String>> listDataimgChild, ArrayList<String> menu) {
        this.context=context;
        this.menu = menu;
        this.listDataansChild = listDataansChild;
        this.listDataimgChild = listDataimgChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataansChild.get(menu.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.helpexpsubmenu, null);
        }



        TextView anstxtview = (TextView) convertView
                .findViewById(R.id.helpanstxt);
        anstxtview.setText(listDataansChild.get(menu.get(groupPosition)).get(childPosition));

        ImageView ansimgview = convertView.findViewById(R.id.helpansimg);
        Picasso.get().load(listDataimgChild.get(menu.get(groupPosition)).get(childPosition)).placeholder(R.drawable.ic_loadingthumb).into(ansimgview);

        ansimgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,ImageViewer.class).putExtra("productimgurl",listDataimgChild.get(menu.get(groupPosition)).get(childPosition)).putExtra("productname",menu.get(groupPosition)));
            }
        });


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataansChild.get(menu.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return menu.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return menu.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.helpexphead, null);
        }

        TextView questxtview = (TextView) convertView
                .findViewById(R.id.helptopictext);
        questxtview.setTypeface(null, Typeface.NORMAL);
        questxtview.setText(headerTitle);


        ImageView arrowimg = convertView.findViewById(R.id.helpquesonclickicon);

        if (isExpanded) {
            arrowimg.setImageResource(R.drawable.ic_blackuparrow);
        }

        else {
            arrowimg.setImageResource(R.drawable.ic_blackdownarrow);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}