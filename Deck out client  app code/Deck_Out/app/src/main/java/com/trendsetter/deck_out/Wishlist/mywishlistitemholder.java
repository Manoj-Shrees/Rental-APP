package com.trendsetter.deck_out.Wishlist;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class mywishlistitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg , deleteitem;
    CardView cardView;
    TextView itemname , itemprice;
    Button movetocartbtn;
    Long wishlistcount;
    String userid;

    public mywishlistitemholder(View itemView , String userid) {
        super(itemView);

        itemimg = itemView.findViewById(R.id.mywishlistimg);
        deleteitem = itemView.findViewById(R.id.mywishlistdeletebtn);
        cardView = itemView.findViewById(R.id.mywishlistcard);
        movetocartbtn = itemView.findViewById(R.id.mywishlistmovetocartbtn);
        itemname = itemView.findViewById(R.id.mywishlisttopic);
        itemprice = itemView.findViewById(R.id.mywishlistprice);
        this.userid = userid;
        getwishlistcount();
    }

    public void setItemimg(String imgurl)
    {
        Picasso.get().load(imgurl).fit().placeholder(R.drawable.ic_loadingthumb).into(itemimg);
    }


    public  void setItemname(String nametxt)
    {
        itemname.setText(nametxt);
    }

    public  void setItemprice(String pricetxt)
    {
        itemprice.setText("Rental ₹ "+pricetxt+" for 4 days");
    }


    public  void  deteletwishlist(String id)
    {
        DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlist");
        wishlistref.child(id).setValue(null);
        setwishlistcount();
    }

    private void getwishlistcount()
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

    private void setwishlistcount()
    {
        DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mywishlistcount");
        wishlistref.setValue(wishlistcount-1);
    }


}