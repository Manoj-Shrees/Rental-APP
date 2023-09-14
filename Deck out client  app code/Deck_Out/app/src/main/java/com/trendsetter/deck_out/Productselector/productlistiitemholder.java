package com.trendsetter.deck_out.Productselector;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

import java.util.HashMap;
import java.util.Map;

public class productlistiitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg , addtowishlistbtn;
    CardView cardView;
    TextView itemname , itemprice;
    Long wishlistcount;

    public productlistiitemholder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.productimage);
        cardView = itemView.findViewById(R.id.productitemcard);
        itemname = itemView.findViewById(R.id.itemname);
        itemprice = itemView.findViewById(R.id.itemprice);
        addtowishlistbtn = itemView.findViewById(R.id.itemaddtowishlist);
        YoYo.with(Techniques.FadeInUp).playOn(itemimg);
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

    public  void addtowishlist(Context context , String userid , String productidtxt , String productnametxt , String productpricetxt ,String productdresscode , String sizetypetitle , String productnoofviewstxt
            , String productsizeavailtxt ,String productdesctxt , String producttotalratingstxt, String productlikes , String productimgurl1txt , String productimgurl2txt , String productimgurl3txt , String productimgurl4txt
            , String retailerid , String Productparent_typetxt)
    {
        final DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlist");
        wishlistref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(productidtxt))
                {

                    AlertDialog.Builder message = new AlertDialog.Builder(context);
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("Item Already Added to Wishlist");
                    message.setCancelable(true);

                    message.setNegativeButton("Ok",null);
                    alert = message.create();
                    alert.show();
                }

                else
                {
                    Map<String, String> productData = new HashMap<String, String>();

                    productData.put("product_id",productidtxt);
                    productData.put("name", productnametxt);
                    productData.put("price", productpricetxt);
                    productData.put("dress_code" , productdresscode);
                    productData.put("dress_sizetitle" , sizetypetitle);
                    productData.put("no_of_views" , productnoofviewstxt);
                    productData.put("size_available" , productsizeavailtxt);
                    productData.put("description" , productdesctxt);
                    productData.put("total_ratings" , producttotalratingstxt);
                    productData.put("product_likes" , productlikes);
                    productData.put("imgurl1" , productimgurl1txt);
                    productData.put("imgurl2" , productimgurl2txt);
                    productData.put("imgurl3" , productimgurl3txt);
                    productData.put("imgurl4" , productimgurl4txt);
                    productData.put("Productparent_type" , Productparent_typetxt);
                    productData.put("retailercode" , retailerid);


                    wishlistref.child(productidtxt).setValue(productData);
                    Toast.makeText(context , "Sucessfully Added to Wishlist" , Toast.LENGTH_SHORT).show();
                    setwishlistcount(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void getwishlistcount(String userid)
    {
        DatabaseReference getwishlistcountref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        getwishlistcountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wishlistcount = Long.valueOf(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setwishlistcount(String userid)
    {
        DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        wishlistref.setValue(wishlistcount+1);
    }

}