package com.trendsetter.deckout_admin.Dressmanage;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.R;


public class productlistiitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg ;
    CardView cardView;
    TextView itemname , itemprice , itemid;
    Button editbtn  , addtohomepagebtn;
    Long wishlistcount;

    public productlistiitemholder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.productimage);
        cardView = itemView.findViewById(R.id.productitemcard);
        itemname = itemView.findViewById(R.id.itemname);
        itemprice = itemView.findViewById(R.id.itemprice);
        itemid = itemView.findViewById(R.id.Productid);
        editbtn = itemView.findViewById(R.id.editbutton);
        addtohomepagebtn = itemView.findViewById(R.id.itemsettohomepageposbtn);
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

    public void setid(String id)
    {
        itemid.setText("PID : "+id);
    }


}