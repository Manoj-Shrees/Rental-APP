package com.trendsetter.deckout_admin.hflcz;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.Extra.taskupdater;
import com.trendsetter.deckout_admin.R;


public class hflholder extends RecyclerView.ViewHolder
{
   public ImageView qnaimgview , qnadelbtn;
    TextView qnaquestxt ;
    ExpandableTextView qnaanstxt;



    public hflholder(View itemView ) {
        super(itemView);

        qnaquestxt = itemView.findViewById(R.id.hlfitemques);
        qnaanstxt = itemView.findViewById(R.id.hlfitemans);
        qnaimgview = itemView.findViewById(R.id.hlfqnaimg);
        qnadelbtn = itemView.findViewById(R.id.hlzdelbtn);
    }

    public void setItemimg(String imgurl)
    {
        Picasso.get().load(imgurl).fit().placeholder(R.drawable.ic_loadingthumb).into(qnaimgview);
    }


    public  void setItemques(String questxt)
    {
        qnaquestxt.setText(questxt);
    }

    public  void setitemans(String anstxt)
    {
        qnaanstxt.setText(anstxt);
    }



    public  void  deteletquesdata(String id , String picurl , String type , Context context)
    {
        if (type.equals("Help"))
        {
            deletefile(picurl , context);
        }
        DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("dataextra");
        wishlistref.child(id).setValue(null);
    }


    private void deletefile(String picurl , Context context)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(picurl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText( context, "Questioneries Deleted Sucessfully",Toast.LENGTH_SHORT).show();
                new taskupdater().settaskdata("Cat - Dress Gallery" , "#mdrs" , " Deleted Sucessfully.");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( context, "Error to delete File Questioneries",Toast.LENGTH_SHORT).show();
            }
        });
    }



}