package com.trendsetter.deckout_admin.Uploaddressdetails;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.R;

public class uploaddressuserlistitemholder extends RecyclerView.ViewHolder {

    ImageView userprofile , blockuser;
    TextView username , useremailid , useridtxt , usermobnotxt ;
    CheckBox userselection;
    RelativeLayout retailerselectionitem;


    public uploaddressuserlistitemholder(@NonNull View itemView) {
        super(itemView);

        userprofile = itemView.findViewById(R.id.userlistuserimg);

        username = itemView.findViewById(R.id.username);
        useridtxt = itemView.findViewById(R.id.useriid);
        usermobnotxt = itemView.findViewById(R.id.userlistmobno);
        userselection = itemView.findViewById(R.id.userselectbtn);
        retailerselectionitem = itemView.findViewById(R.id.retailerselectionitem);

    }



    public  void setuserprofile(String userpropicurl)
    {
        Picasso.get().load(userpropicurl).fit().into(userprofile);
    }
}
