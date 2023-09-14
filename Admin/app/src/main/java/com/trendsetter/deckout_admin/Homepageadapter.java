package com.trendsetter.deckout_admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trendsetter.deckout_admin.Dressmanage.Dressmange;
import com.trendsetter.deckout_admin.Orderlist.Orderslist;
import com.trendsetter.deckout_admin.Uploaddressdetails.Uploaddressdetials;
import com.trendsetter.deckout_admin.Userlist.Createuser;
import com.trendsetter.deckout_admin.Userlist.Userlist;
import com.trendsetter.deckout_admin.hflcz.hflczmain;

import java.util.ArrayList;

public class Homepageadapter extends RecyclerView.Adapter<Homepageadapter.Homepageviewholder> {

    ArrayList<String> itmtitletxt ;
    ArrayList<Integer> itmimg;
    Context context;
    Homepageadapter(Context context , ArrayList<String> itmtitletxt , ArrayList<Integer> itmimg)

    {
        this.context = context;
        this.itmtitletxt = itmtitletxt ;
        this.itmimg = itmimg ;
    }



    @NonNull
    @Override
    public Homepageviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hompageitemlayout, viewGroup, false);
        return new Homepageviewholder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull Homepageviewholder homepageviewholder, int i) {

        homepageviewholder.itmimg.setImageResource(itmimg.get(i));
        homepageviewholder.itmtitletxt.setText(itmtitletxt.get(i));

        homepageviewholder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0) {
                    context.startActivity(new Intent(context , Uploaddressdetials.class));
                }

                if (i==1)
                {
                    context.startActivity(new Intent(context , Dressmange.class));
                }

                if (i== 2)
                {
                    context.startActivity(new Intent(context , hflczmain.class));
                }


                if(i == 3 || i==4 || i == 5)
                {
                    final Dialog optionpannel = new Dialog(context);
                    optionpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    optionpannel.getWindow().setWindowAnimations(R.style.animateddialog);
                    optionpannel.setCanceledOnTouchOutside(false);
                    optionpannel.setCancelable(true);
                    optionpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    optionpannel.setContentView(R.layout.usersdialoglayout);

                    TextView createuserbtn = optionpannel.findViewById(R.id.createuser);
                    TextView userlistbtn = optionpannel.findViewById(R.id.userlist);

                    if(i == 3)
                    {
                        createuserbtn.setVisibility(View.GONE);
                    }
                    createuserbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context , Createuser.class));
                        }
                    });

                    userlistbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String usertypetxt = null;

                            if(i==3)
                            {
                                usertypetxt = "Client";
                            }

                            if(i==4)
                            {
                                usertypetxt = "Deliveryboy";
                            }

                            if(i==5)
                            {
                                usertypetxt = "Retailer";
                            }

                          context.startActivity(new Intent(context , Userlist.class).putExtra("usercattype" , usertypetxt));
                        }
                    });


                    ImageView changepannelclosebtn = optionpannel.findViewById(R.id.chooseoptiondialogclose);
                    changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            optionpannel.dismiss();
                        }
                    });
                    optionpannel.show();
                    Window window = optionpannel.getWindow();
                    window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }

                if (i==6)
                {
                    context.startActivity(new Intent(context , Orderslist.class).putExtra("orderfiltertype", "#F"));
                }

                if (i==7)
                {
                    context.startActivity(new Intent(context , Orderslist.class).putExtra("orderfiltertype", "#T"));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return itmimg.size();
    }

    public class Homepageviewholder extends  RecyclerView.ViewHolder
    {

        ImageView itmimg ;
        TextView itmtitletxt;
        CardView itemcard ;

        public Homepageviewholder(@NonNull View itemView) {
            super(itemView);
            itmimg = itemView.findViewById(R.id.hompageitemimg);
            itmtitletxt =  itemView.findViewById(R.id.hompageitemtxt);
            itemcard = itemView.findViewById(R.id.hompagecard);
        }



    }

}
