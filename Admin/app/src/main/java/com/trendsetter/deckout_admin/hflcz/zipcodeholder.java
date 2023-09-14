package com.trendsetter.deckout_admin.hflcz;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trendsetter.deckout_admin.R;

public class zipcodeholder  extends RecyclerView.ViewHolder {

    TextView zipcodetxt , zipcoderegionandlandtxt ;
    ImageView delzipcodebtn;

    public zipcodeholder(@NonNull View itemView) {
        super(itemView);

        zipcodetxt = itemView.findViewById(R.id.zipcode);
        zipcoderegionandlandtxt = itemView.findViewById(R.id.regionandlandmark);
        delzipcodebtn = itemView.findViewById(R.id.zipcodedelbtn);

    }

    public void setzipcodetxt (String zipcode)
    {
        zipcodetxt.setText(zipcode);
    }

    public void setregionandlandmark (String regionnlandmark)
    {
        zipcoderegionandlandtxt.setText(regionnlandmark);
    }


    public void deletezipcode(String id )
    {
        DatabaseReference  zipcoderef = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist");
        zipcoderef.child(id).setValue(null);
    }
}
