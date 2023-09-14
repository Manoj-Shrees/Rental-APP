package com.trendsetter.deckout_Delivery;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class orderdetailslistholder extends RecyclerView.ViewHolder {

    TextView productnametxt , productsizeandtypetxt , producttagnametxt ;
    ImageView productlistimgview;
    RelativeLayout orderlistlayout;
    CheckBox orderitemcheck;

    public orderdetailslistholder(@NonNull View itemView) {
        super(itemView);
        productnametxt = itemView.findViewById(R.id.productname);
        productlistimgview = itemView.findViewById(R.id.productlistimg);
        productsizeandtypetxt = itemView.findViewById(R.id.productsizeandtype);
        producttagnametxt = itemView.findViewById(R.id.producttagname);
        orderlistlayout = itemView.findViewById(R.id.orderlistlayout);
        orderitemcheck = itemView.findViewById(R.id.orderitemcheck);

        orderlistlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderitemcheck.isChecked()== true)
                {
                    orderitemcheck.setChecked(false);
                }

                else
                {
                    orderitemcheck.setChecked(true);
                }
            }
        });

    }

    public  void setproductimg(String imgurl)
    {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).into(productlistimgview);
    }
}
