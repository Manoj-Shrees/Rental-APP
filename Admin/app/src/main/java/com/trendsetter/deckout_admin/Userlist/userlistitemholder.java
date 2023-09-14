package com.trendsetter.deckout_admin.Userlist;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.trendsetter.deckout_admin.Extra.taskupdater;
import com.trendsetter.deckout_admin.R;

public class userlistitemholder extends RecyclerView.ViewHolder {

    ImageView userprofile , userblockbtn;
    TextView username , useremailid , useridtxt , usermobnotxt ;


    public userlistitemholder(@NonNull View itemView) {
        super(itemView);

        userprofile = itemView.findViewById(R.id.userlistuserimg);

        username = itemView.findViewById(R.id.userlistuser_name);
        useremailid  = itemView.findViewById(R.id.userlistemailid);
        useridtxt = itemView.findViewById(R.id.useriid);
        usermobnotxt = itemView.findViewById(R.id.userlistmobno);
        userblockbtn = itemView.findViewById(R.id.userlistblockbtn);

    }

    public  void setuserprofile(String userpropicurl)
    {
        Picasso.get().load(userpropicurl).fit().into(userprofile);
    }

    public void setblockeduser(Context context , String nametxt , String propicurl , String emailtxt , String phnotxt , String idtxt , String typetxt)
    {

      /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuserref = database.getReference("blockedusers/users/"+typetxt);

        Map<String, String> dressdata = new HashMap<String, String>();

        dressdata.put("name",nametxt);
        dressdata.put("propic",propicurl);
        dressdata.put("mobno", phnotxt);
        dressdata.put("id" ,idtxt);
        dressdata.put("type" ,typetxt);


        blockuserref.child(idtxt).setValue(dressdata);*/


        if (typetxt.equals("Client"))
        {
            setuserblockeduseremailid(idtxt, emailtxt);
        }
        setuserblockeduserphno(idtxt , phnotxt );
        setcheckblocked(idtxt , typetxt);

        setprocessedmsg(context);

        new taskupdater().settaskdata("Cat - "+typetxt+" Manage " , "#mnc" , "User Blocked Sucessfully.");

    }


    private void setprocessedmsg(Context context)
    {
        AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
        deleteitemmsg.setTitle("Message");
        deleteitemmsg.setMessage("\nUser Sucessfully Blocked . ");
        deleteitemmsg.setPositiveButton("ok",null);
        deleteitemmsg.show();
    }


    private void setcheckblocked(String userid , String type)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference checkblockref = database.getReference("datarecords/deckoutusers/"+type+"/"+userid);
        checkblockref.child("checkblock").setValue("istrue");
    }

    private void setuserblockeduseremailid(String userid , String emailid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuseremailidref = database.getReference("blockedusers/emailid");
        blockuseremailidref.child(emailid.replace("@" , "~").replace("." , "`")).setValue(userid);
    }


    private void setuserblockeduserphno(String userid , String phno)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuserphnoref = database.getReference("blockedusers/phno");
        blockuserphnoref.child(phno).setValue(userid);
    }
}
