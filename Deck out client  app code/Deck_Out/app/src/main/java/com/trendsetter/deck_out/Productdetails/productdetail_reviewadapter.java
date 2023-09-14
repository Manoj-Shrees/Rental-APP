package com.trendsetter.deck_out.Productdetails;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.R;

public class productdetail_reviewadapter extends FirebaseRecyclerAdapter<productreviewgetdata , productreviewitemholder> {

    Context context;

    public productdetail_reviewadapter(@NonNull FirebaseRecyclerOptions<productreviewgetdata> options , Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public productreviewitemholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productreviewitemlayout, parent, false);
        return  new productreviewitemholder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull productreviewitemholder holder, int position, @NonNull productreviewgetdata model) {

        holder.setitemimg(model.getImageurl());
        holder.setItemusername(model.getUsername());
        holder.setrating(model.getRating());
        holder.setItemmessage(model.getMessage());
    }

}




