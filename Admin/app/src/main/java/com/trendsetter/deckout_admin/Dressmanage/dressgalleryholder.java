package com.trendsetter.deckout_admin.Dressmanage;

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
import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.Extra.taskupdater;
import com.trendsetter.deckout_admin.R;


public class dressgalleryholder extends RecyclerView.ViewHolder
{
   public ImageView dressgalleryimgview , dressgallerydelbtn;
    TextView dressgallerypratortopictxt;



    public dressgalleryholder(View itemView ) {
        super(itemView);

        dressgalleryimgview = itemView.findViewById(R.id.dressgalleryimg);
        dressgallerypratortopictxt = itemView.findViewById(R.id.dressgallerypratortopic);
        dressgallerydelbtn = itemView.findViewById(R.id.dressgallerdelbtn);
    }

    public void setItemimg(String imgurl)
    {
        Picasso.get().load(imgurl).fit().placeholder(R.drawable.ic_loadingthumb).into(dressgalleryimgview);
    }


    public  void setItemname(String nametxt)
    {
        dressgallerypratortopictxt.setText(nametxt);
    }



    public  void  deteletdressgallerydata(String id , String picurl , Context context)
    {
        deletefile(picurl , context);
        DatabaseReference wishlistref = FirebaseDatabase.getInstance().getReference("datarecords/Homepagegallery");
        wishlistref.child(id).setValue(null);
    }


    public  void deletefile(String picurl , Context context)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(picurl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText( context, "Coverpic Deleted Sucessfully",Toast.LENGTH_SHORT).show();
                new taskupdater().settaskdata("Cat - Dress Gallery" , "#mdrs" , " Deleted Sucessfully.");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( context, "Error to delete File coverpic",Toast.LENGTH_SHORT).show();
            }
        });
    }



}