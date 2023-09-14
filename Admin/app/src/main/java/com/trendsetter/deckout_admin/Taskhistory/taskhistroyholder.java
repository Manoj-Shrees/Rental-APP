package com.trendsetter.deckout_admin.Taskhistory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trendsetter.deckout_admin.R;

public class taskhistroyholder extends RecyclerView.ViewHolder  {

    ImageView taskitemimg , taskdeletebtn ;
    TextView taskitemtopictxt , taskcattypetxt , taskdatetxt ;

    public taskhistroyholder(@NonNull View itemView) {
        super(itemView);

        taskitemtopictxt = itemView.findViewById(R.id.taskitemtopictxt);
        taskcattypetxt = itemView.findViewById(R.id.taskcattype);
        taskdatetxt = itemView.findViewById(R.id.taskdate);
        taskitemimg = itemView.findViewById(R.id.taskitemimg);
        taskdeletebtn = itemView.findViewById(R.id.taskdeletebtn);

    }
}
