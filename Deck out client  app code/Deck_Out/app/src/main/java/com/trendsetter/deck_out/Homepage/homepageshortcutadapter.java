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
import com.trendsetter.deck_out.R;

public class homepageshortcutadapter  extends FirebaseRecyclerAdapter<Topshortcutlistgetdata, homepageshortcutViewHolder> {

  Context context;
    public homepageshortcutadapter(@NonNull FirebaseRecyclerOptions<Topshortcutlistgetdata> options , Context context) {
        super(options);
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull final homepageshortcutViewHolder holder, int position, @NonNull Topshortcutlistgetdata model) {
        holder.setName(model.getShortcutname());

        holder.setImage(model.getShortcuturl());

        holder.itemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context ,ExploreOption.class ).putExtra("searchitemtxt" , holder.name.getText().toString().trim()));
            }
        });
    }

    @NonNull
    @Override
    public homepageshortcutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepageshortcutitem, parent, false);
        return new homepageshortcutViewHolder(view);
    }
}
