package com.trendsetter.deckout_admin.Taskhistory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trendsetter.deckout_admin.R;

public class taskhistoryadapter  extends FirebaseRecyclerAdapter<taskhistorygetdata, taskhistroyholder> {

    Context context;

    public taskhistoryadapter(@NonNull FirebaseRecyclerOptions<taskhistorygetdata> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull taskhistroyholder holder, int position, @NonNull taskhistorygetdata model) {

      holder.taskitemtopictxt.setText(model.getCategory());
      holder.taskcattypetxt.setText(model.getType());
      holder.taskdatetxt.setText(model.getDate());

      switch (model.getImagecode())
      {
          case "#dru" :
              holder.taskitemimg.setImageResource(R.drawable.ic_dress_upload);
              break;

          case "#mdrs" :
              holder.taskitemimg.setImageResource(R.drawable.ic_dress_manage);
              break;

          case "#hflc" :
              holder.taskitemimg.setImageResource(R.drawable.ic_helpfaqandothers);
              break;

          case "#mnc" :
              holder.taskitemimg.setImageResource(R.drawable.ic_client_manage);
              break;
      }

      holder.taskdeletebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
          {

              AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
              deleteitemmsg.setTitle("Message");
              deleteitemmsg.setMessage("\nDo you want to Delete this item ?");
              deleteitemmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                      deletetask(model.getId());

                  }
              });
              deleteitemmsg.setNegativeButton("No", null);
              deleteitemmsg.show();


          }
      });


    }


    private void deletetask(String id)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference taskref = database.getReference("deckoutadmin/taskhistory");
        taskref.child(id).setValue(null);

    }


    @NonNull
    @Override
    public taskhistroyholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.taskhistorylistitem, viewGroup, false);

        return  new taskhistroyholder(itemView);
    }
}
