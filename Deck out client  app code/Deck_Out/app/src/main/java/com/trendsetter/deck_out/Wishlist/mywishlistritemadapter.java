package com.trendsetter.deck_out.Wishlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.Productdetails.Productdetail;
import com.trendsetter.deck_out.R;

public class mywishlistritemadapter extends FirebaseRecyclerAdapter<mywishlistgetdata , mywishlistitemholder>
{

    Context context;
    String userid;

    public mywishlistritemadapter(@NonNull FirebaseRecyclerOptions<mywishlistgetdata> options , Context context ,String userid) {
        super(options);
        this.context = context;
        this.userid = userid;
    }

    @Override
    public mywishlistitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mywishlistitemlayout, parent, false);

        return  new mywishlistitemholder(itemView , userid);
    }

    @Override
    protected void onBindViewHolder(@NonNull final mywishlistitemholder holder, int position, @NonNull final mywishlistgetdata model) {
        holder.setItemimg(model.getImgurl1());
        holder.setItemname(model.getName());
        holder.setItemprice(model.getPrice());
        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
                deleteitemmsg.setTitle("Delete Item ");
                deleteitemmsg.setMessage("Do you want to Delete this Item ?");
                deleteitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.deteletwishlist(model.getProduct_id());
                    }
                });
                deleteitemmsg.setNegativeButton("No",null);
                deleteitemmsg.show();

            }
        });
        holder.movetocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(">code" , " -  "+ model.getRetailercode());

                context.startActivity(new Intent(context , Productdetail.class)
                        .putExtra("productname",model.getName())
                        .putExtra("productdesc" , model.getDescription())
                        .putExtra("productdresscode" , model.getDress_code())
                        .putExtra("productsizetitle" , model.getDress_sizetitle())
                        .putExtra("productid" , model.getProduct_id())
                        .putExtra("productnoofviews" , model.getNo_of_views())
                        .putExtra("productsizeavail" , model.getSize_available())
                        .putExtra("productprice" , model.getPrice())
                        .putExtra("Productparent_type" , model.getProductparent_type())
                        .putExtra("producttotalratings" , model.getTotal_ratings())
                        .putExtra("product_likes" , model.getProduct_likes())
                        .putExtra("productimgurl1" , model.getImgurl1())
                        .putExtra("productimgurl2" , model.getImgurl2())
                        .putExtra("productimgurl3" , model.getImgurl3())
                        .putExtra("productimgurl4" , model.getImgurl4())
                        .putExtra("retailerid" , model.getRetailercode())

                );
            }
        });

    }


}