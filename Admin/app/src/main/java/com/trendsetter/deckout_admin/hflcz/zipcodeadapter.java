package com.trendsetter.deckout_admin.hflcz;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

public class zipcodeadapter extends FirebaseRecyclerAdapter<zipcodegetdata , zipcodeholder> {

 Context context;

    public zipcodeadapter(@NonNull FirebaseRecyclerOptions<zipcodegetdata> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull zipcodeholder holder, int position, @NonNull zipcodegetdata model) {
        holder.setzipcodetxt(model.getZipcode());
        holder.setregionandlandmark(model.getLandmark()+" , "+model.getRegion());
        holder.delzipcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder message = new AlertDialog.Builder(context);
                message.setTitle("Delete Zipcode");
                AlertDialog alert;
                message.setMessage("\n\nDo you want to delete this item ?");
                message.setCancelable(false);
                message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.deletezipcode(model.getZipcode());
                        Toast.makeText(context , "Sucessfully Deleted ." , Toast.LENGTH_SHORT).show();
                    }
                });
                message.setNegativeButton("No" , null);
                alert = message.create();
                alert.show();

            }
        });
    }

    @NonNull
    @Override
    public zipcodeholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.zipcodeitemlayout, viewGroup, false);

        return  new zipcodeholder(itemView);
    }
}
