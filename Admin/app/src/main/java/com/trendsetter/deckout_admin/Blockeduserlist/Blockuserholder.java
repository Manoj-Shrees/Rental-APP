package com.trendsetter.deckout_admin.Blockeduserlist;

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

public class Blockuserholder extends RecyclerView.ViewHolder {

    ImageView userprofile , unblockuser;
    TextView username , userphno , useridtxt ;


    public Blockuserholder(@NonNull View itemView) {
        super(itemView);

        userprofile = itemView.findViewById(R.id.userblocklistuserimg);
        unblockuser = itemView.findViewById(R.id.userunblockbtn);

        username = itemView.findViewById(R.id.userblocklistuser_name);
        userphno  = itemView.findViewById(R.id.userblocklistphonno);
        useridtxt = itemView.findViewById(R.id.userid);
        }


    public  void setuserprofile(String userpropicurl)
    {
        Picasso.get().load(userpropicurl).fit().into(userprofile);
    }


    public void setblockeduser(Context context ,  String emailtxt , String phnotxt , String idtxt , String typetxt)
    {

      /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuserref = database.getReference("blockedusers/users/"+typetxt);

        blockuserref.child(idtxt).setValue(null)*/;


        if (typetxt.equals("Client"))
        {
            setuserblockeduseremailid(emailtxt);
        }
        setuserblockeduserphno( phnotxt );
        setcheckblocked(idtxt , typetxt);

        setprocessedmsg(context);

        new taskupdater().settaskdata("Cat - "+typetxt+" Manage " , "#mnc" , "User Unblocked Sucessfully.");
    }



    private void setprocessedmsg(Context context)
    {
        AlertDialog.Builder deleteitemmsg = new AlertDialog.Builder(context);
        deleteitemmsg.setTitle("Message");
        deleteitemmsg.setMessage("\nUser Sucessfully Unblocked . ");
        deleteitemmsg.setPositiveButton("ok",null);
        deleteitemmsg.show();
    }

    private void setcheckblocked(String userid , String type)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference checkblockref = database.getReference("datarecords/deckoutusers/"+type+"/"+userid);
        checkblockref.child("checkblock").setValue("isfalse");
    }

    private void setuserblockeduseremailid(String emailid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuseremailidref = database.getReference("blockedusers/emailid");
        blockuseremailidref.child(emailid.replace("@" , "~").replace("." , "`")).setValue(null);
    }


    private void setuserblockeduserphno(String phno)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuserphnoref = database.getReference("blockedusers/phno");
        blockuserphnoref.child(phno).setValue(null);
    }

    }