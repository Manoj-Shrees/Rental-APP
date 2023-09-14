package com.trendsetter.deck_out.Productdetails;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class similarproductlistiitemholder extends RecyclerView.ViewHolder {


    ImageView itemimg , addtowishlistbtn;
    CardView cardView;
    TextView itemname , itemprice;
    public similarproductlistiitemholder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.productimage);
        cardView = itemView.findViewById(R.id.productitemcard);
        itemname = itemView.findViewById(R.id.itemname);
        itemprice = itemView.findViewById(R.id.itemprice);
        addtowishlistbtn = itemView.findViewById(R.id.itemaddtowishlist);
        addtowishlistbtn.setVisibility(View.GONE);
        YoYo.with(Techniques.FadeInUp).playOn(cardView);
    }

    public  void setname(String nametxt)
    {
        itemname.setText(nametxt);
    }

    public void setimage(String imgurl) {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).into(itemimg);
    }

    public void setprice (String pricetxt)
    {
        itemprice.setText("Rental ₹ "+pricetxt+" for 4 days");
    }


}