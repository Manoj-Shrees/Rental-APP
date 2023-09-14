package com.trendsetter.deckout_admin.Extra;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class taskupdater {

    public void settaskdata(String cattypetxt , String imgcodetxt , String typetxt)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference blockuserref = database.getReference("deckoutadmin/taskhistory");

        Map<String, String> dressdata = new HashMap<String, String>();

        String idtxt = blockuserref.push().getKey();

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm: a");
        String datetxt = format.format(today);

        dressdata.put("category",cattypetxt);
        dressdata.put("date","- on "+datetxt);
        dressdata.put("imagecode", imgcodetxt);
        dressdata.put("type" ,typetxt);
        dressdata.put("id", idtxt);


        blockuserref.child(idtxt).setValue(dressdata);
    }

}
