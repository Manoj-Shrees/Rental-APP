package com.trendsetter.deckout_admin.Feedback;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.trendsetter.deckout_admin.R;

public class feedbackadapter  extends FirebaseRecyclerAdapter<feedbackgetdata , feedbackholder> {

    Context context;

    public feedbackadapter(@NonNull FirebaseRecyclerOptions<feedbackgetdata> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull feedbackholder holder, int position, @NonNull feedbackgetdata model) {

        holder.setfeedbackprofile(model.getPropic());
        holder.username.setText(model.getName());
        holder.userid.setText(model.getId());
        holder.feedbacktxt.setText(model.getFeedbacktxt());
    }


    @NonNull
    @Override
    public feedbackholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedbackitemlayout, parent, false);

        return  new feedbackholder(itemView);
    }
}
