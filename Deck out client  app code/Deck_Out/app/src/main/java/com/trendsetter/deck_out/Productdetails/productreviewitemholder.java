package com.trendsetter.deck_out.Productdetails;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class productreviewitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg;
    TextView itemusername , itemmessage;
    RatingBar itemrating;


    public productreviewitemholder(View itemView) {
        super(itemView);

        itemimg = itemView.findViewById(R.id.productreviewuserimg);
        itemusername = itemView.findViewById(R.id.productreviewuser_name);
        itemmessage = itemView.findViewById(R.id.productreviewmessagetxt);
        itemrating = itemView.findViewById(R.id.productreviewrating);

    }


    public  void setitemimg(String imgurl)
    {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_avatar).fit().into(itemimg);
    }

    public void setItemusername(String name)
    {
        itemusername.setText(name);
    }

    public  void setItemmessage(String message)
    {
        itemmessage.setText(message);
    }

    public  void setrating(String rating)
    {
        itemrating.setRating(Float.parseFloat(rating));
    }


}
