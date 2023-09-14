package com.trendsetter.deckout_Delivery;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class orderdetailslistadapter  extends FirebaseRecyclerAdapter<orderdetailslistgetdata , orderdetailslistholder> {


    public orderdetailslistadapter(@NonNull FirebaseRecyclerOptions<orderdetailslistgetdata> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull orderdetailslistholder holder, int position, @NonNull orderdetailslistgetdata model) {

        holder.setproductimg(model.getProductimgurl());
        holder.productnametxt.setText(model.getProductname());
        holder.productsizeandtypetxt.setText(model.getProducttypeandsize());
        holder.producttagnametxt.setText(model.getProducttagname());

    }

    @NonNull
    @Override
    public orderdetailslistholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.orderlistitem, viewGroup, false);

        return  new orderdetailslistholder(itemView);
    }
}
