package com.trendsetter.deckout_admin.Orderlist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.R;


public class orderproductlistholder extends RecyclerView.ViewHolder {

    TextView productname , productsizeandtype , producttagname , productprice  , productsizedatatxt;
    ImageView productimage;

    public orderproductlistholder(@NonNull View itemView) {
        super(itemView);

        productname = itemView.findViewById(R.id.orderproductlistname);
        productsizeandtype = itemView.findViewById(R.id.orderproductlistsizeandtype);
        producttagname = itemView.findViewById(R.id.orderproductlisttagname);
        productprice = itemView.findViewById(R.id.orderproductprice);
        productimage = itemView.findViewById(R.id.orderproductlistimg);
        productsizedatatxt = itemView.findViewById(R.id.orderproductsizedata);


    }

    public void setproductpic(String imgurl)
    {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).into(productimage);
    }
}
