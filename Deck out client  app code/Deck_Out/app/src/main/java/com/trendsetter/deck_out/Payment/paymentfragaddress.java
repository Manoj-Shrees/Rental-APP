package com.trendsetter.deck_out.Payment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trendsetter.deck_out.Profile.Profiledetail;
import com.trendsetter.deck_out.R;

import static android.content.Context.MODE_PRIVATE;

public class paymentfragaddress extends Fragment {

    RecyclerView addresslist;
    FirebaseRecyclerOptions <paymentaddressgetdata> options;
    paymentaddressitemadapter addressitemadapter;
    RelativeLayout addnewaddressbtn;
    private  int addresscount=0;
    String useridtxt = "";
    SharedPreferences sp;
    private Dialog addnewaddresspannel;
    private EditText pincode;
    private int addadresscount = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sp= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        useridtxt =sp.getString("userid","");
        View view = inflater.inflate(R.layout.paymentaddressfraglayout, container, false);
        addresslist = view.findViewById(R.id.paymentaddresslist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        addresslist.setLayoutManager(layoutManager);
        addnewaddressbtn = view.findViewById(R.id.paymentaddaddressbtn);


        addnewaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addnewaddressbtn.setEnabled(false);

                addnewaddresspannel = new Dialog(getContext());
                addnewaddresspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addnewaddresspannel.getWindow().setWindowAnimations(R.style.animateddialog);
                addnewaddresspannel.setCanceledOnTouchOutside(false);
                addnewaddresspannel.setCancelable(true);
                addnewaddresspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addnewaddresspannel.setContentView(R.layout.updatedeliveryaddress);
                final EditText username = addnewaddresspannel.findViewById(R.id.deckoutusername);
                final EditText deliverymobno = addnewaddresspannel.findViewById(R.id.deckoutmobno);
                pincode = addnewaddresspannel.findViewById(R.id.deckoutpincode);
                final EditText houseno = addnewaddresspannel.findViewById(R.id.deckouthouseno);
                final EditText loc = addnewaddresspannel.findViewById(R.id.deckoutloc);
                final EditText landmark = addnewaddresspannel.findViewById(R.id.deckoutlandmark);
                final EditText town = addnewaddresspannel.findViewById(R.id.deckouttown);
                final EditText state = addnewaddresspannel.findViewById(R.id.deckoutstate);

                Button addnewaddress = addnewaddresspannel.findViewById(R.id.deckoutsetaddressbtn);

                addnewaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (username.getText().toString().length() == 0 )
                        {
                            username.setError("is empty");
                            addadresscount+=1;
                        }

                        if (loc.getText().toString().length() == 0 )
                        {
                            loc.setError("is empty");
                            addadresscount+=1;
                        }

                        if (houseno.getText().toString().length() == 0)
                        {
                            houseno.setError("is empty");
                            addadresscount+=1;
                        }

                        if (landmark.getText().toString().length() == 0 )
                        {
                            landmark.setError("is empty");
                            addadresscount+=1;
                        }

                        if (town.getText().toString().length() == 0 )
                        {
                            town.setError("is empty");
                            addadresscount+=1;
                        }

                        if (state.getText().toString().length() == 0 )
                        {
                            state.setError("is empty");
                            addadresscount+=1;
                        }

                        if (pincode.getText().toString().length() == 0 )
                        {
                            pincode.setError("is empty");
                            addadresscount+=1;
                        }

                        if (deliverymobno.getText().toString().length() == 0 )
                        {
                            deliverymobno.setError("is empty");
                            addadresscount+=1;
                        }

                        if (addadresscount == 0)
                        {
                            checkzipcode(username.getText().toString() , houseno.getText().toString() , loc.getText().toString() , landmark.getText().toString() , town.getText().toString() ,
                                    state.getText().toString() , pincode.getText().toString(), deliverymobno.getText().toString());

                        }

                        addadresscount = 0;

                    }
                });


                ImageView changepannelclosebtn = addnewaddresspannel.findViewById(R.id.newaddressdialogclose);
                changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addnewaddresspannel.dismiss();
                        addnewaddressbtn.setEnabled(true);
                    }
                });
                addnewaddresspannel.show();

                Window window = addnewaddresspannel.getWindow();
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                window.setGravity(Gravity.CENTER);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }
        });

        getaddresscount();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("datarecords/deckoutusers/Client/"+useridtxt+"/Addresses");
        Query query=ref;
        options = new FirebaseRecyclerOptions.Builder<paymentaddressgetdata>()
                .setQuery(query, paymentaddressgetdata.class)
                .build();

        addressitemadapter = new paymentaddressitemadapter(options , getActivity() , useridtxt);
        addressitemadapter.startListening();
        addresslist.setAdapter(addressitemadapter);
        return  view;
    }


    private void  checkzipcode(String usernametxt , String housenotxt , String loctxt , String landmarktxt, String towntxt ,
                               String statetxt ,String pincodetxt , String deliverymobnotxt )
    {

        String zipcodetxt = pincodetxt;

        DatabaseReference deliverynameref = FirebaseDatabase.getInstance().getReference("datarecords/Avialablezipcodelist");
        deliverynameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(zipcodetxt))
                {
                    String addresstxt =  housenotxt+" , ;"+loctxt+" , ;"+landmarktxt+", ;"+towntxt+"<br><br>;"+statetxt+" - ;"+pincodetxt+" <br><br>Delivery Phone No. - ;"+deliverymobnotxt;
                    setnewaddress(addresstxt);
                    setdeliveryname(usernametxt);
                    setaddresscounter(addresscount+1);
                    addnewaddresspannel.dismiss();
                    addnewaddressbtn.setEnabled(true);
                }

                else
                {
                    pincode.setError("Not Avialable");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getaddresscount()
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+useridtxt+"/addresscount");
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


    private void setnewaddress(String address)
    {
        final DatabaseReference deliveryaddressref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+useridtxt+"/Addresses/address"+(addresscount+1));
        deliveryaddressref.child("addresstxt").setValue(address);
    }

    private void setdeliveryname(String name)
    { final DatabaseReference deliverynameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+useridtxt+"/Addresses/address"+(addresscount+1));
        deliverynameref.child("deliveryname").setValue(name);

    }

    private void setaddresscounter(int count)
    {
        final DatabaseReference addresscount = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+useridtxt+"/addresscount");
        addresscount.setValue(count);
    }


}
