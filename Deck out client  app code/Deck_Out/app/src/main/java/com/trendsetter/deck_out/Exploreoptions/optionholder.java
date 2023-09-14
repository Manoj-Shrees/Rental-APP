package com.trendsetter.deck_out.Exploreoptions;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.trendsetter.deck_out.R;

public class optionholder extends RecyclerView.ViewHolder {
    ImageView itemimg;
    RelativeLayout optioncontainer;
    TextView itemname;

    public optionholder(View itemView) {
        super(itemView);
        itemimg = itemView.findViewById(R.id.optionselectimage);
        itemname = itemView.findViewById(R.id.optionselecttxt);
        optioncontainer = itemView.findViewById(R.id.optioncontainer);
        YoYo.with(Techniques.Landing).playOn(optioncontainer);
    }

}