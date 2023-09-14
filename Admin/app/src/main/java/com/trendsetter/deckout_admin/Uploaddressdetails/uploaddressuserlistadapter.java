package com.trendsetter.deckout_admin.Uploaddressdetails;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

public class uploaddressuserlistadapter extends FirebaseRecyclerAdapter<uploaddressuserlistdata, uploaddressuserlistitemholder> {

 Context context;
 String userttype;
 private int lastCheckedPosition = -1;

    public uploaddressuserlistadapter(@NonNull FirebaseRecyclerOptions<uploaddressuserlistdata> options , Context context ) {
        super(options);
        this.context = context;
        this.userttype = userttype;
    }

    @Override
    protected void onBindViewHolder(@NonNull uploaddressuserlistitemholder holder, int position, @NonNull uploaddressuserlistdata model) {

        holder.username.setText(model.getName());

        holder.usermobnotxt.setText(model.getMobno());

        holder.setuserprofile(model.getProfilepic());
        holder.useridtxt.setText(model.getUserid());
        holder.userselection.setChecked(position == lastCheckedPosition);
        holder.userselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                setretailerdata(model.getUserid() , model.getName());
            }
        });

        holder.retailerselectionitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                setretailerdata(model.getUserid() , model.getName());
            }
        });


    }

    @NonNull
    @Override
    public uploaddressuserlistitemholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.dressuploadetaileritem, viewGroup, false);

        return  new uploaddressuserlistitemholder(itemView);
    }


    private void setretailerdata(String id , String name)
    {
                SharedPreferences  sharedpreferences = context.getSharedPreferences("deckoutretailerid", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString("selectedid",id);
                editor.putString("selectedname" , name);
                editor.commit();

    }


}
