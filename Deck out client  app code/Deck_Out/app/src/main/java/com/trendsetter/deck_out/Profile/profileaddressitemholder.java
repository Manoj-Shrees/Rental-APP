package com.trendsetter.deck_out.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

public class profileaddressitemholder extends RecyclerView.ViewHolder
{

    private  TextView address , deliveryto;
    public ImageView addresseditbtn , addressdeletebtn;
    private  int addresscount;
    String userid;


    public profileaddressitemholder(View itemView) {
        super(itemView);
        address = itemView.findViewById(R.id.profileaddresstxt);
        deliveryto = itemView.findViewById(R.id.profiledeliveryname);
        addresseditbtn = itemView.findViewById(R.id.profileaddresseditbtn);
        addressdeletebtn = itemView.findViewById(R.id.profileaddressdeletebtn);
    }


    public void setAddress(String addresstxt)
    {
        address.setText(Html.fromHtml(addresstxt.replace(";","")));

    }

    public void setuid(String userid)
    {
        this.userid = userid;
        getaddresscount(userid);
    }

    public  void setdeliveryname(String name)
    {
        deliveryto.setText(name);
    }

    public void openeditpannel(final Context context , String name , final String address , final int position)
    {
        final Dialog editaddresspannel = new Dialog(context);
        editaddresspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editaddresspannel.getWindow().setWindowAnimations(R.style.animateddialog);
        editaddresspannel.setCanceledOnTouchOutside(false);
        editaddresspannel.setCancelable(true);
        editaddresspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editaddresspannel.setContentView(R.layout.updatedeliveryaddress);
        TextView headertxt =  editaddresspannel.findViewById(R.id.sizehedingtxt);
        headertxt.setText("Update Address Details");
        final EditText username = editaddresspannel.findViewById(R.id.deckoutusername);
        final EditText deliverymobno = editaddresspannel.findViewById(R.id.deckoutmobno);
        final EditText pincode = editaddresspannel.findViewById(R.id.deckoutpincode);
        final EditText houseno = editaddresspannel.findViewById(R.id.deckouthouseno);
        final EditText loc = editaddresspannel.findViewById(R.id.deckoutloc);
        final EditText landmark = editaddresspannel.findViewById(R.id.deckoutlandmark);
        final EditText town = editaddresspannel.findViewById(R.id.deckouttown);
        final EditText state = editaddresspannel.findViewById(R.id.deckoutstate);

        Button editaddressbtn = editaddresspannel.findViewById(R.id.deckoutsetaddressbtn);

        editaddressbtn.setText("Update");

        username.setText(name);

        String [] addressdata = address.replaceAll(",","").replaceAll("-","").replaceAll("<br>","").split(";");

        houseno.setText(addressdata[0]);
        loc.setText(addressdata[1]);
        landmark.setText(addressdata[2]);
        town.setText(addressdata[3]);
        state.setText(addressdata[4]);
        pincode.setText(addressdata[5]);
        deliverymobno.setText(addressdata[6]);
        final AlertDialog.Builder updateaddressmsg = new AlertDialog.Builder(context);

        editaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateaddressmsg.setTitle("Update Address");
                updateaddressmsg.setMessage("Do you want to Update this Adress ?");
                updateaddressmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String addresstxt = houseno.getText().toString().trim()+" , ;"+loc.getText().toString().trim()+" , ;"+landmark.getText().toString().trim()+", ;"+town.getText().toString().trim()+"<br><br>;"+state.getText().toString().trim()+" - ;"+pincode.getText().toString().trim()+" <br><br>Delivery Phone No. - ;"+deliverymobno.getText().toString().trim();
                        updatedeliveryname(username.getText().toString().trim() , position , userid);
                        updateaddress(addresstxt , position,userid);
                        editaddresspannel.dismiss();
                    }
                });
                updateaddressmsg.setNegativeButton("No",null);
                updateaddressmsg.show();


            }
        });


        ImageView changepannelclosebtn = editaddresspannel.findViewById(R.id.newaddressdialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editaddresspannel.dismiss();
            }
        });

        editaddresspannel.show();
        Window window = editaddresspannel.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public  void deleteaddress(int position , String userid)
    {
        final DatabaseReference deliveryaddressref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(position+1));
        deliveryaddressref.setValue(null);
        setaddresscounter((addresscount-1) , userid);
    }

    private void updateaddress(String address ,int pos , String userid)
    {
        final DatabaseReference deliveryaddressref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(pos+1));
        deliveryaddressref.child("addresstxt").setValue(address);
    }

    private void updatedeliveryname(String name , int pos , String userid)
    { final DatabaseReference deliverynameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(pos+1));
        deliverynameref.child("deliveryname").setValue(name);

    }


    private void setaddresscounter(int count , String userid)
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/addresscount");
        addresscount.setValue(count);
    }

    private void getaddresscount(String userid)
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/addresscount");
        addresscount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setAddresscount(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setAddresscount(String count)
    {
        addresscount = Integer.parseInt(count);
    }

}