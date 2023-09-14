package com.trendsetter.deck_out.FAQ;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.HashMap;

public class faqexplistadapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> menu;
    private HashMap<String, String> listDataansChild;

    public faqexplistadapter(Context context, HashMap<String, String> listDataansChild, ArrayList<String> menu) {
        this.context=context;
        this.menu = menu;
        this.listDataansChild = listDataansChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataansChild.get(menu.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String datatxt = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faqexpsubmenu, null);
        }



        TextView anstxtview = (TextView) convertView
                .findViewById(R.id.faqanstxt);
        anstxtview.setText(datatxt);



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
            convertView = infalInflater.inflate(R.layout.faqexphead, null);
        }

        TextView questxtview = (TextView) convertView
                .findViewById(R.id.faqtopictext);
        questxtview.setText(headerTitle);


        ImageView arrowimg = convertView.findViewById(R.id.faqquesonclickicon);

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
