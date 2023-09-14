package com.trendsetter.deck_out.Homepage;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;


public class homepageshortcutViewHolder extends RecyclerView.ViewHolder{

    ImageView image;
    TextView name;
    RelativeLayout itemlayout;

    public homepageshortcutViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.homepageshortcutimg);
        name = itemView.findViewById(R.id.homepageshortcutnametxt);
        itemlayout = itemView.findViewById(R.id.homepageshortlayout);
    }


    public void setImage(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(image);
    }


    public  void setName(String nametxt)
    {
        name.setText(nametxt);
    }
}
