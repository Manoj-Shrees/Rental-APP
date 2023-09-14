package com.trendsetter.deck_out.Exploreoptions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trendsetter.deck_out.Productselector.ProductselectorList;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class exploreexplistadapter  extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> menu;
    private HashMap<String,List<String>> listsubmenu;
    public exploreexplistadapter(Context context, ArrayList<String> menu, HashMap<String, List<String>> listsubmenu) {
        this.context=context;
        this.menu=menu;
        this.listsubmenu=listsubmenu;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listsubmenu.get(menu.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String picres = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exploreoptionselectorsubmenu, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.optionselectorsubmenutxt);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(picres);

        RelativeLayout opionselct = convertView.findViewById(R.id.explorelistchildlayout);
        opionselct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context ,ProductselectorList.class ).putExtra("Product_type" , lblListHeader.getText().toString()));
            }
        });



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listsubmenu.get(menu.get(groupPosition)).size();
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
            convertView = infalInflater.inflate(R.layout.exploreoptionselectormenu, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.optionselectormenutxt);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        ImageView arrowimg = convertView.findViewById(R.id.exploreoptionselectorarrowimg);

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
