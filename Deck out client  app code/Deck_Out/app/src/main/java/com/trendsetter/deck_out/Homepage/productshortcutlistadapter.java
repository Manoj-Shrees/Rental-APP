package com.trendsetter.deck_out.Homepage;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deck_out.Exploreoptions.ExploreOption;
import com.trendsetter.deck_out.Productselector.ProductselectorList;
import com.trendsetter.deck_out.R;

public class productshortcutlistadapter  extends FirebaseRecyclerAdapter<productshortcutlistgetdata ,productshortcutviewholder> {


    Context context;

    public productshortcutlistadapter(@NonNull FirebaseRecyclerOptions<productshortcutlistgetdata> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
        public productshortcutviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.homepageshortcutproductitemlayout, parent, false);

            return  new productshortcutviewholder(itemView);
        }


    @Override
    protected void onBindViewHolder(@NonNull final productshortcutviewholder holder, int position, @NonNull productshortcutlistgetdata model) {

        holder.itemimgtxt.setText(model.getName());


        holder.set1Image(model.getImg1());
        holder.set2Image(model.getImg2());
        holder.set3Image(model.getImg3());
        holder.set4Image(model.getImg4());
        holder.set5Image(model.getImg5());
        holder.set6Image(model.getImg6());
        holder.set7Image(model.getImg7());
        holder.set8Image(model.getImg8());
        holder.set9Image(model.getImg9());


        holder.setads1Image(model.getAds1());
        holder.setads2Image(model.getAds2());

        holder.itemimgbanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexploreproduct(holder);
            }
        });

        holder.itemimgbanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexploreproduct(holder);
            }
        });


        holder.itemimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });



        holder.itemimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });

        holder.itemimg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openproductdetaillist(model.getName());
            }
        });
    }

    private void openproductdetaillist(String dresstype)
        {
            context.startActivity(new Intent(context , ProductselectorList.class).putExtra("dresstype" , dresstype));
        }

        private void openexploreproduct( productshortcutviewholder holder)
        {
            context.startActivity(new Intent(context , ExploreOption.class).putExtra("dresstype" , holder.itemimgtxt.getText().toString().trim().toUpperCase()));
        }




}
