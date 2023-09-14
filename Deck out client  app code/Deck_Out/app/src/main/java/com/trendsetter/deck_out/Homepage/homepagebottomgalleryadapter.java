package com.trendsetter.deck_out.Homepage;


import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.Extra.ImageViewer;
import com.trendsetter.deck_out.R;


public class homepagebottomgalleryadapter extends FirebaseRecyclerAdapter<hompagebottomgallerygetdata , homepagebottomgalleryitemholder> {

    Context context;


    public homepagebottomgalleryadapter(@NonNull FirebaseRecyclerOptions<hompagebottomgallerygetdata> options , Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public homepagebottomgalleryitemholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homepageimgcontaineritemlayout, parent, false);

        return  new homepagebottomgalleryitemholder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull homepagebottomgalleryitemholder holder, int position, @NonNull final hompagebottomgallerygetdata model) {
        holder.setimg(model.getImgurl());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,ImageViewer.class).putExtra("productimgurl",model.getImgurl()).putExtra("productname",model.getName()));

            }
        });
    }



}

