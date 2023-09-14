package com.trendsetter.deckout_admin.Userlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

public class Userlistadapter  extends FirebaseRecyclerAdapter<userlistdata ,userlistitemholder> {

 Context context;
 String usertype;

    public Userlistadapter(@NonNull FirebaseRecyclerOptions<userlistdata> options , Context context , String usertype) {
        super(options);
        this.context = context;
        this.usertype = usertype;
    }

    @Override
    protected void onBindViewHolder(@NonNull userlistitemholder holder, int position, @NonNull userlistdata model) {

        holder.username.setText(model.getName());

        if(usertype == "Client")
        {
            holder.useremailid.setText(model.getEmailid());
            holder.useremailid.setVisibility(View.VISIBLE);
            holder.usermobnotxt.setVisibility(View.GONE);
        }

        else
        {
            holder.usermobnotxt.setText(model.getMobno());
            holder.useremailid.setVisibility(View.GONE);
            holder.usermobnotxt.setVisibility(View.VISIBLE);
        }

        holder.setuserprofile(model.getProfilepic());
        holder.useridtxt.setText(model.getUserid());
        holder.userblockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model.getCheckblock().equals("istrue"))
                {
                    AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
                    deleteitemmsg.setTitle("Message");
                    deleteitemmsg.setMessage("\nuser alreay blocked . ");
                    deleteitemmsg.setPositiveButton("Ok", null);
                    deleteitemmsg.show();
                }

                else {

                    AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
                    deleteitemmsg.setTitle("Message");
                    deleteitemmsg.setMessage("Do you want to Block this user ?");
                    deleteitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            holder.setblockeduser(context, model.getName(), model.getProfilepic(), model.getEmailid(), model.getMobno(), model.getUserid(), usertype);

                        }
                    });
                    deleteitemmsg.setNegativeButton("No", null);
                    deleteitemmsg.show();

                }
            }
        });


    }

    @NonNull
    @Override
    public userlistitemholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.useritemlayout, viewGroup, false);

        return  new userlistitemholder(itemView);
    }
}
