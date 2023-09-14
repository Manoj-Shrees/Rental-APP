package com.trendsetter.deckout_admin.Feedback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.R;

public class feedbackholder  extends RecyclerView.ViewHolder{

TextView userid , username ;
ImageView userimg;
ExpandableTextView feedbacktxt;

    public feedbackholder(@NonNull View itemView) {
        super(itemView);

        userid = itemView.findViewById(R.id.feedbackuserid);
        username = itemView.findViewById(R.id.feedbackuser_name);
        userimg = itemView.findViewById(R.id.feedbackuserimg);
        feedbacktxt = itemView.findViewById(R.id.feedbacktxt);

    }


    public void setfeedbackprofile(String imgurl)
    {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).into(userimg);
    }
}
