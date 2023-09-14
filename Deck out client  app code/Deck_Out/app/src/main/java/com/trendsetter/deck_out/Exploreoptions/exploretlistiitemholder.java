package com.trendsetter.deck_out.Exploreoptions;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class exploretlistiitemholder extends RecyclerView.ViewHolder {
    ImageView itemimg;
    CardView cardView;
    TextView itemname , itemprice;

    public exploretlistiitemholder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.exlorelistproductimage);
        cardView = itemView.findViewById(R.id.exploreproductitemcard);
        itemname = itemView.findViewById(R.id.exlorelistItemname);
        itemprice = itemView.findViewById(R.id.exlorelistitemprice);
        YoYo.with(Techniques.FadeInUp).playOn(itemimg);
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