package com.trendsetter.deck_out.Homepage;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class homepagebottomgalleryitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg;
    CardView cardView;
    LinearLayout layout;
    public homepagebottomgalleryitemholder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.homepageshortcutlistimg);
        cardView = itemView.findViewById(R.id.homepageshortcutlistcard);
        YoYo.with(Techniques.FadeInUp).playOn(cardView);
    }

    public   void setimg(String imgurl)
    {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).into(itemimg);
    }

}
