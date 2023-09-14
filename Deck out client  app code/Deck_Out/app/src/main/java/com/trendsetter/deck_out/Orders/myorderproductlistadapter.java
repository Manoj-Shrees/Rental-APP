package com.trendsetter.deck_out.Orders;

import android.content.Context;
import androidx.annotation.NonNull;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.R;

public class myorderproductlistadapter extends FirebaseRecyclerAdapter<myorderproductlistgetdata , myorderproductlistholder> {

    Context context;

    public myorderproductlistadapter(@NonNull FirebaseRecyclerOptions<myorderproductlistgetdata> options , Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull myorderproductlistholder holder, int position, @NonNull myorderproductlistgetdata model) {

        holder.setproductpic(model.getProductimgurl());
        holder.productname.setText(model.getProductname());
        holder.producttagname.setText(model.getProducttagname());
        holder.productsizeandtype.setText(model.getProducttypeandsize());
        holder.productprice.setText("₹ "+model.getProductprice());
        holder.productsizedatatxt.setText(Html.fromHtml(model.getProductsizedata().replace(" " , "&nbsp;")));

    }

    @NonNull
    @Override
    public myorderproductlistholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.myorderproductlistitemlayout, viewGroup, false);

        return  new myorderproductlistholder(itemView);
    }
}