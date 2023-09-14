package com.trendsetter.deck_out.Homepage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.trendsetter.deck_out.Exploreoptions.ExploreOption;
import com.trendsetter.deck_out.Extra.Arrowanimation;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;

public class Expandablelistadapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> optionname;
    ArrayList<String> expandlistdata;

    public Expandablelistadapter(Context context,ArrayList<String> optionname,ArrayList<String> expandlistdata) {
         this.context = context;
         this.optionname = optionname;
         this.expandlistdata = expandlistdata;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return expandlistdata.get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandlistsubmenulayout, null);
        }
        final TextView txt = convertView.findViewById(R.id.optionlisttxt);
        txt.setText(expandlistdata.get(childPosition));

       RelativeLayout opionselct = convertView.findViewById(R.id.exploreoption);
        opionselct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context ,ExploreOption.class ).putExtra("dresstype" , txt.getText().toString().trim()));
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  expandlistdata.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return optionname.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return optionname.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandlistmenutextlayout, null);

        }

         ImageView explisttypeicon = convertView.findViewById(R.id.expoptionclickicon);
            if (isExpanded) {
                explisttypeicon.setAnimation(new Arrowanimation().animateExpand());
            }

            else {
                explisttypeicon.setAnimation(new Arrowanimation().animateCollapse());
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