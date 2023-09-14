package com.trendsetter.deck_out.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

import static android.content.Context.MODE_PRIVATE;

public class Profilepicbackground extends Fragment {

    ImageView profilebackgroundimageview ;
    SharedPreferences sp;
    String userid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilepicbg, container, false);

        sp=getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
        profilebackgroundimageview = view.findViewById(R.id.profilebackgroundimageview);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setprofileimagebg();
    }

    private void setprofileimagebg()
    {
        DatabaseReference profilepicrefrence = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Profilepic");
        profilepicrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_avatar).fit().into(profilebackgroundimageview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
