package com.trendsetter.deckout_admin.Dressmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;


public class dressgalleryadapter extends FirebaseRecyclerAdapter<dressgallerygetdata, dressgalleryholder>
{

    Context context;
    String userid;

    public dressgalleryadapter(@NonNull FirebaseRecyclerOptions<dressgallerygetdata> options , Context context) {
        super(options);
        this.context = context;
        this.userid = userid;
    }

    public dressgalleryadapter(FirebaseRecyclerOptions<dressgallerygetdata> option) {
        super(option);
    }

    @Override
    public dressgalleryholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dressmanagedressgalleryitem, parent, false);

        return  new dressgalleryholder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final dressgalleryholder holder, int position, @NonNull final dressgallerygetdata model) {
        holder.setItemimg(model.getImgurl());
        holder.setItemname(model.getName());

        holder.dressgallerydelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
                deleteitemmsg.setTitle("Delete Item ");
                deleteitemmsg.setMessage("Do you want to Delete this Item ?");
                deleteitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.deteletdressgallerydata(model.getId() , model.getImgurl() , context);
                    }
                });
                deleteitemmsg.setNegativeButton("No",null);
                deleteitemmsg.show();
            }
        });


    }


}