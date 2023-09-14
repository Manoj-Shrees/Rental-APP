package com.trendsetter.deck_out.Exploreoptions;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.Productdetails.Productdetail;
import com.trendsetter.deck_out.R;

public  class exploreitemadapter extends FirebaseRecyclerAdapter< exploreoptionprofuctgetdata , exploretlistiitemholder>
{

    private Context context;

    public exploreitemadapter(@NonNull FirebaseRecyclerOptions<exploreoptionprofuctgetdata> options , Context context) {
        super(options);
        this.context = context;
    }


    @Override
    public exploretlistiitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exploresuggestlistitem, parent, false);

        return  new exploretlistiitemholder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull exploretlistiitemholder holder, int position, @NonNull final exploreoptionprofuctgetdata model) {

        holder.setimage(model.getImgurl1());
        holder.setname(model.getName());
        holder.setprice(model.getPrice());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
