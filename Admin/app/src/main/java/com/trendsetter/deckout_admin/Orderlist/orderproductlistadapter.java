package com.trendsetter.deckout_admin.Orderlist;

import android.content.Context;
import androidx.annotation.NonNull;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

public class orderproductlistadapter extends FirebaseRecyclerAdapter<orderproductlistgetdata , orderproductlistholder> {

    Context context;

    public orderproductlistadapter(@NonNull FirebaseRecyclerOptions<orderproductlistgetdata> options , Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull orderproductlistholder holder, int position, @NonNull orderproductlistgetdata model) {

        holder.setproductpic(model.getProductimgurl());
        holder.productname.setText(model.getProductname());
        holder.producttagname.setText(model.getProducttagname());
        holder.productsizeandtype.setText(model.getProducttypeandsize());
        holder.productprice.setText("₹ "+model.getProductprice());
        holder.productsizedatatxt.setText(Html.fromHtml(model.getProductsizedata().replace(" " , "&nbsp;")));

    }

    @NonNull
    @Override
    public orderproductlistholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.orderproductlistitemlayout, viewGroup, false);

        return  new orderproductlistholder(itemView);
    }
}
