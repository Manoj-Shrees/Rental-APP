package com.trendsetter.deckout_admin.Blockeduserlist;

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

public class Blockeduserlistadapter extends FirebaseRecyclerAdapter<blockeduserdata ,Blockuserholder> {

    Context context;
    String usertype;


    public Blockeduserlistadapter(@NonNull FirebaseRecyclerOptions<blockeduserdata> options , Context context , String usertype) {
        super(options);
        this.context = context;
        this.usertype = usertype;
    }



    @NonNull
    @Override
    public Blockuserholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.userblockitemlayout, viewGroup, false);

        return  new Blockuserholder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Blockuserholder holder, int position, @NonNull blockeduserdata model) {
        holder.username.setText(model.getName());
        holder.userphno.setText(model.getMobno());
        holder.setuserprofile(model.getProfilepic());
        holder.useridtxt.setText(model.getUserid());

        holder.unblockuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model.getCheckblock().equals("isfalse"))
                {
                    AlertDialog.Builder unblockitemmsg = new AlertDialog.Builder(context);
                    unblockitemmsg.setTitle("Message");
                    unblockitemmsg.setMessage("\nUser alreay Unblocked . ");
                    unblockitemmsg.setPositiveButton("Ok", null);
                    unblockitemmsg.show();
                }

                else {
                    AlertDialog.Builder unblockitemmsg = new AlertDialog.Builder(context);
                    unblockitemmsg.setTitle("Message");
                    unblockitemmsg.setMessage("Do you want to Unblock this user ?");
                    unblockitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            holder.setblockeduser(context, model.getEmailid(), model.getMobno(), model.getUserid(), usertype);

                        }
                    });
                    unblockitemmsg.setNegativeButton("No", null);
                    unblockitemmsg.show();

                }
            }
        });
    }
}