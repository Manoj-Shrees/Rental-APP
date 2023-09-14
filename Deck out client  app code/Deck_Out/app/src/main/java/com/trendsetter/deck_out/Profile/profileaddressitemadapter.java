package com.trendsetter.deck_out.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.R;

import static android.content.Context.MODE_PRIVATE;

public class profileaddressitemadapter extends FirebaseRecyclerAdapter<profileaddressgetdata ,profileaddressitemholder>
{

    Context context;
    SharedPreferences sp;
    String userid;

    public profileaddressitemadapter(@NonNull FirebaseRecyclerOptions<profileaddressgetdata> options , Context context) {
        super(options);
        this.context = context;
        sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
    }

    @Override
    public profileaddressitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profileaddresslayout, parent, false);

        return  new profileaddressitemholder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull final profileaddressitemholder holder, final int position, @NonNull final profileaddressgetdata model) {
        holder.setuid(userid);
        holder.setAddress(model.getAddresstxt());
        holder.setdeliveryname(model.getDeliveryname());
        holder.addresseditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.openeditpannel(context , model.getDeliveryname() , model.getAddresstxt() , position);
            }
        });

        holder.addressdeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               AlertDialog.Builder deleteaddressmsg = new AlertDialog.Builder(context);
                deleteaddressmsg.setTitle("Delete Address");
                deleteaddressmsg.setMessage("Do you want to Delete this Adress ?");
                deleteaddressmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.deleteaddress(position , userid);
                    }
                });
                deleteaddressmsg.setNegativeButton("No",null);
                deleteaddressmsg.show();

            }
        });
    }

}