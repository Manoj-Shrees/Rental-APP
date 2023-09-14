package com.trendsetter.deck_out.Productselector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.Login_Signup.MainActivity;
import com.trendsetter.deck_out.Productdetails.Productdetail;
import com.trendsetter.deck_out.R;

import static android.content.Context.MODE_PRIVATE;

public  class productlistitemadapter extends FirebaseRecyclerAdapter<productlistitemgetdata , productlistiitemholder>
{
    SharedPreferences sp;
    private Context context;
    String userid , usertype;

    public productlistitemadapter(@NonNull FirebaseRecyclerOptions<productlistitemgetdata> options , Context context) {
        super(options);
        this.context = context;
        sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
        usertype = sp.getString("usertype","");
    }

    @Override
    public productlistiitemholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productlistitemlayout, parent, false);

        return  new productlistiitemholder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull productlistiitemholder holder, int position, @NonNull final productlistitemgetdata model) {
        if (!usertype.equals("#guest")) {
            holder.getwishlistcount(userid);
        }
        holder.setimage(model.getImgurl1());
        holder.setname(model.getName());
        holder.setprice(model.getPrice());
        holder.addtowishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!usertype.equals("#guest")) {

                    AlertDialog.Builder message = new AlertDialog.Builder(context);
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("\nDo you want to Add this item to Wishlist ?");
                    message.setCancelable(true);

                    message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.addtowishlist(context, userid, model.getProduct_id(), model.getName(), model.getPrice(), model.getDress_code(), model.getDress_sizetitle(), model.getNo_of_views()
                                    , model.getSize_available(), model.getDescription(), model.getTotal_ratings(), model.getProduct_likes(), model.getImgurl1(), model.getImgurl2(), model.getImgurl3()
                                    , model.getImgurl4(), model.getRetailercode(), model.getProductparent_type());
                        }
                    });
                    message.setNegativeButton("No", null);
                    alert = message.create();
                    alert.show();
                }

                else
                {
                    openguestusermsg();
                }

            }
        });
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


    private void openguestusermsg()
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Message");
        builder1.setMessage("\nplease register or login to acess this service.\n\nDo you want to register or Login ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}