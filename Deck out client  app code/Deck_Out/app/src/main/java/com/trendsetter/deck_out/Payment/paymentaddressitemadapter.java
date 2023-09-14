package com.trendsetter.deck_out.Payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.R;

public class paymentaddressitemadapter extends FirebaseRecyclerAdapter<paymentaddressgetdata , paymentaddressitemadapter.paymentaddressitemholder>
{

    Context context;
    private  int addresscount;
    private int lastSelectedPosition = 0;
    private String userid ;


    public paymentaddressitemadapter(@NonNull FirebaseRecyclerOptions<paymentaddressgetdata> options , Context context , String userid) {
        super(options);
        this.context = context;
        this.userid = userid;
    }

    @Override
    protected void onBindViewHolder(@NonNull final paymentaddressitemholder holder, final int position, @NonNull final paymentaddressgetdata model) {
      holder.setAddress(model.getAddresstxt());
      holder.setDeliveryname(model.getDeliveryname());
      holder.selectionState.setChecked(lastSelectedPosition == position);
      if (lastSelectedPosition == position)
        {
            setselectedaddrs(model.getAddresstxt());
        }

        holder.selectionState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setlastselected(model.getAddresstxt());
            }
        });

        holder.addressitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setlastselected(model.getAddresstxt());
            }
        });

        holder.addresseditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      openeditpannel(context , model.getDeliveryname() , model.getAddresstxt() , position);
            }
        });

        holder.addressdeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteaddressmsg = new AlertDialog.Builder(context);
                deleteaddressmsg.setTitle("Delete Address");
                deleteaddressmsg.setMessage("Do you want to Delete this Adress ?");
                deleteaddressmsg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteaddress(position);
                    }
                });
                deleteaddressmsg.setNegativeButton("No",null);
                deleteaddressmsg.show();

            }
        });
    }

    @NonNull
    @Override
    public paymentaddressitemholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.paymentaddressitemlayout, viewGroup, false);

        return  new paymentaddressitemholder(itemView);
    }


    public class paymentaddressitemholder extends RecyclerView.ViewHolder
    {

        private TextView address , deliveryto;
        private AppCompatRadioButton selectionState;
        public ImageView addresseditbtn , addressdeletebtn;
        LinearLayout addressitem;


        public paymentaddressitemholder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.addresstxt);
            deliveryto = itemView.findViewById(R.id.paymentdeliveryname);
            addresseditbtn = itemView.findViewById(R.id.paymentaddresseditbtn);
            addressdeletebtn = itemView.findViewById(R.id.paymentaddressdeletebtn);
            selectionState = itemView.findViewById(R.id.selectaddressitem);
            addressitem = itemView.findViewById(R.id.paymentaddressitemrootlayout);
            getaddresscount();

        }

        private void setlastselected(String addrstxt)
        {
            lastSelectedPosition = getAdapterPosition();
            notifyDataSetChanged();
            setselectedaddrs(addrstxt);
        }



        public void setAddress(String addresstxt)
        {
            address.setText(Html.fromHtml(addresstxt.replace(";","")));

        }

        public void setDeliveryname(String name)
        {
            deliveryto.setText(name);
        }


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
                        updatedeliveryname(username.getText().toString().trim() , position);
                        updateaddress(addresstxt , position);
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

    public  void deleteaddress(int position)
    {
        final DatabaseReference deliveryaddressref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(position+1));
        deliveryaddressref.setValue(null);
        setaddresscounter((addresscount-1));
    }

    private void updateaddress(String address ,int pos)
    {
        final DatabaseReference deliveryaddressref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(pos+1));
        deliveryaddressref.child("addresstxt").setValue(address);
    }

    private void updatedeliveryname(String name , int pos)
    { final DatabaseReference deliverynameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Addresses/address"+(pos+1));
        deliverynameref.child("deliveryname").setValue(name);

    }


    private void setaddresscounter(int count)
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/addresscount");
        addresscount.setValue(count);
    }

    private void getaddresscount()
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



    private void setselectedaddrs(String addrstxt)
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences("deckoutselectedaddrs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("addrs",addrstxt);
        editor.commit();

    }


    private void setAddresscount(String count)
    {
        addresscount = Integer.parseInt(count);
    }
}