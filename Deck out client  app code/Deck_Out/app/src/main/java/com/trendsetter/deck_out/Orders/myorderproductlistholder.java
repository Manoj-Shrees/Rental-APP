package com.trendsetter.deck_out.Orders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class myorderproductlistholder extends RecyclerView.ViewHolder {

    TextView productname , productsizeandtype , producttagname , productprice , productsizedatatxt ;
    ImageView productimage;

    public myorderproductlistholder(@NonNull View itemView) {
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
