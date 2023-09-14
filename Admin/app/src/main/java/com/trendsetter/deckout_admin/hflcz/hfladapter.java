package com.trendsetter.deckout_admin.hflcz;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

public class hfladapter extends FirebaseRecyclerAdapter<hflgetdata , hflholder> {

 String type ;
 Context context;

    public hfladapter(@NonNull FirebaseRecyclerOptions<hflgetdata> options , String type , Context context) {
        super(options);
        this.type = type;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull hflholder holder, int position, @NonNull hflgetdata model) {

        holder.setItemques(model.getId());
        holder.setitemans(model.getAnswer());
        if (type.equals("Help"))
        {
            holder.setItemimg(model.getImgurl());
        }

        else
        {
            holder.qnaimgview.setVisibility(View.GONE);
        }


        holder.qnadelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder message = new AlertDialog.Builder(context);
                message.setTitle("Delete Questioniers");
                AlertDialog alert = null;

                message.setMessage("\n\n Do you want to delete this item ?");
                message.setCancelable(false);
                AlertDialog finalAlert = alert;
                message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.deteletquesdata(model.getImgurl() , model.getImgurl() , type , context );
                    }
                });
                message.setNegativeButton("No" , null);
                alert = message.create();
                alert.show();



            }
        });




    }

    @NonNull
    @Override
    public hflholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hflitemlayout, viewGroup, false);

        return  new hflholder(itemView);
    }
}
