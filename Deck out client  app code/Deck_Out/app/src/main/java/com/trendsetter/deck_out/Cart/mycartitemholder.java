package com.trendsetter.deck_out.Cart;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Extra.Sizerangegetadapter;
import com.trendsetter.deck_out.R;

import java.lang.reflect.Field;

public class mycartitemholder extends RecyclerView.ViewHolder{

        Button deletebtn , detailbtn , sizedetailbtn;
        private Button sizexsbtn , sizesbtn ,sizembtn ,sizelbtn , sizexlbtn , sizexxlbtn , sizerangesbtn;
        Spinner quantspinner;
        LinearLayout mycartitemlayout;
        ImageView productimage ;
        TextView productname , producttype ,productprice  , duration;
        CheckBox mycartitemcheckbox;
        public  TextView producttotalprice ;
        Sizerangegetadapter getadapter = new Sizerangegetadapter();
        ImageView durationandtag;

    private TextView   sizepanttitle , sizetshirttitle , sizeshirttitle , sizefrocktitle , sizejumpsuittitle ;
    private  Context context;
    private Dialog sizedetailpannel;
    private Boolean checksizeopt = false;


    private Spinner pantlengthspinner , pantwaistspinner ,
            tshirtlengthspinner , tshirtchestspinner , tshirtshoulderspinner ,
            shirtlengthspinner , shirtchestspinner , shirtsleevespinner ,
            frocklengthspinner , frockwaistspinner , frockchestspinner , frockhipspinner ,
            jumpsuitlengthspinner , jumpsuitwaistspinner , jumpsuitchestspinner , jumpsuithipspinner ;

    private  LinearLayout pantrootlayout , tshirtrootlayout , shirtrootlayout , frockrootlayout , jumpsuitrootlayout ;

    private RelativeLayout tshirtchestlayout , tshirtshoulderlayout ,  shirtchestlayout , shirtsleevelayout , frockchestlayout ,frockhiplayout ;

    private  String kidspantsmall , kidspantmedium , kidspantlarge , kidspantxlarge ,
            kidsjumpsuitsmall , kidsjumpsuitmedium , kidsjumpsuitlarge , kidsjumpsuitxlarge ,
            kidsfrocksmall , kidsfrockmedium , kidsfrocklarge , kidsfrockxlarge ,
            kidstshirtsmall , kidstshirtmedium , kidstshirtlarge , kidstshirtxlarge ,
            kidsshirtsmall , kidsshirtmedium , kidsshirtlarge , kidsshirtxlarge ,

    menspantsmall , menspantmedium , menspantlarge , menspantxlarge , menspantxxlarge ,
            menstshirtsmall , menstshirtmedium , menstshirtlarge , menstshirtxlarge , menstshirtxxlarge ,
            mensshirtsmall , mensshirtmedium , mensshirtlarge , mensshirtxlarge , mensshirtxxlarge ,

    womenpantxsmall , womenpantsmall , womenpantmedium , womenpantlarge , womenpantxlarge ,  womenpantxxlarge ,
            womenjumpsuitxsmall , womenjumpsuitsmall , womenjumpsuitmedium , womenjumpsuitlarge , womenjumpsuitxlarge , womenjumpsuitxxlarge ,
            womenfrockxsmall ,  womenfrocksmall , womenfrockmedium , womenfrocklarge , womenfrockxlarge , womenfrockxxlarge ,
            womentshirtxsmall , womentshirtsmall , womentshirtmedium , womentshirtlarge , womentshirtxlarge ,womentshirtxxlarge ,
            womenshirtxsmall , womenshirtsmall , womenshirtmedium , womenshirtlarge , womenshirtxlarge  , womenshirtxxlarge
            , productdresscode , sizetypetitle,  sizeheadertxt,  sizecattype , sizedata , productid;



        public mycartitemholder(View itemView) {
            super(itemView);

            quantspinner = (Spinner) itemView.findViewById(R.id.mycartquantity);
            deletebtn = itemView.findViewById(R.id.mycartcancleorder);
            detailbtn = itemView.findViewById(R.id.mycartdetails);
            sizedetailbtn = itemView.findViewById(R.id.mycartsizebutton);
            productimage = itemView.findViewById(R.id.mycartproductimg);
            productname = itemView.findViewById(R.id.mycartproductname);
            producttype = itemView.findViewById(R.id.mycartproducttype);
            productprice = itemView.findViewById(R.id.mycartprice);
            producttotalprice = itemView.findViewById(R.id.mycarttotalprice);
            duration = itemView.findViewById(R.id.mycartduration);
            durationandtag = itemView.findViewById(R.id.setdurationandtag);
            mycartitemcheckbox = itemView.findViewById(R.id.mycartitemcheck);
            mycartitemlayout = itemView.findViewById(R.id.mycartitemlayout);

            quantspinner.setEnabled(false);


        }

        public  void setdata(Context context ,String productid , String productdresscode ,String sizetypetitle  , String sizedata , String sizeaval , String sizeheadertxt , String sizecattype)
        {
            this.context = context;
            this.productdresscode = productdresscode;
            this.sizetypetitle = sizetypetitle;
            this.sizeheadertxt = sizeheadertxt;
            this.sizedata = sizedata;
            this.sizecattype = sizecattype;
            this.productid = productid;
            if(!sizeaval.equals("#Ascr;")) {
                inistilizedata();
                setsizedetails(sizeaval);
                setdresscodes(sizecattype);
            }

            else
            {
                if (sizeaval.equals("#Ascr;"))
                sizedetailbtn.setVisibility(View.GONE);
            }



        }

        public void setproductname(String name)
        {
             productname.setText(name);
        }

        public void setProducttype(String type)
        {
            producttype.setText(type);
        }

        public void setProductimage(String url)
        {
            Picasso.get().load(url).placeholder(R.drawable.ic_loadingthumb).fit().into(productimage);
        }

        public void setdressquant(final String quant)
        {
            final String[] quantval;

            if(Long.valueOf(quant) > 5)
            {
                quantval = new String[]{"1", "2", "3", "4", "5",quant , "More"};
                quantspinner.setSelection(5);

            }

            else
            {
                quantval = new String[]{"1", "2", "3", "4", "5", "More"};


            }

            ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, quantval);
            langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
            quantspinner.setAdapter(langAdapter);

            for(int pos = 0 ; pos<quantval.length; pos++)
            {
                if(quantval[pos].equals(quant))
                {
                    quantspinner.setSelection(pos);
                }
            }
        }

        public void setmycartitemcheck(String id , String userid)
        {
            DatabaseReference  checkidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycartselecteditems");
            checkidref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue().toString().contains(id+";"))
                    {
                        mycartitemcheckbox.setChecked(true);
                        quantspinner.setEnabled(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public void setduration(String dur)
        {
            duration.setText(dur);
        }

        public  void setProductprice(String price)
        {
           productprice.setText("₹ "+price);
        }

        public void setProducttotalprice(String price , String quant , String id , String userid)
        {
            price = "₹ "+ (Long.parseLong(quant) * Long.parseLong(price));
            producttotalprice.setText(price);
            setmycartitemcheck(id , userid);

        }



    public void setdurationcolor(int pos)
    {
        if (pos == 0)
        {
            duration.setTextColor(Color.RED);
        }

        else
        {
            duration.setTextColor(Color.BLUE);
        }
    }



    public  void opensizepannel()
        {
            if (checksizeopt == false)
            {
                 new getsizecatdata().execute();
            }

            else
            {
                AlertDialog.Builder message = new AlertDialog.Builder(context);
                message.setTitle("Message");
                AlertDialog alert;
                message.setMessage("The Selected Product Does not contains any size");
                message.setCancelable(true);

                message.setNegativeButton("Ok",null);
                alert = message.create();
                alert.show();
            }

        }

        private void opensizedetails(String code ,String typetitle , String headtxt , String type)
        {
            sizepanttitle = sizedetailpannel.findViewById(R.id.panttitle);
            sizetshirttitle = sizedetailpannel.findViewById(R.id.tshirttitle);
            sizeshirttitle = sizedetailpannel.findViewById(R.id.shirttitle);
            sizefrocktitle = sizedetailpannel.findViewById(R.id.frocktitle);
            sizejumpsuittitle = sizedetailpannel.findViewById(R.id.jumpsuittitle);

            tshirtchestlayout = sizedetailpannel.findViewById(R.id.tshirtchestlayout);
            tshirtshoulderlayout = sizedetailpannel.findViewById(R.id.tshirtshoulderlayout);
            shirtchestlayout = sizedetailpannel.findViewById(R.id.shirtchestlayout);
            shirtsleevelayout = sizedetailpannel.findViewById(R.id.shirtsleevelayout);
            frockchestlayout = sizedetailpannel.findViewById(R.id.frockchestlayout);
            frockhiplayout = sizedetailpannel.findViewById(R.id.frockhiplayout);


            pantlengthspinner =  sizedetailpannel.findViewById(R.id.pantlengthselector);
            pantwaistspinner =  sizedetailpannel.findViewById(R.id.pantwaistselector);

            tshirtlengthspinner =  sizedetailpannel.findViewById(R.id.tshirtlengthselector);
            tshirtchestspinner =  sizedetailpannel.findViewById(R.id.tshirtchestselector);
            tshirtshoulderspinner =  sizedetailpannel.findViewById(R.id.tshirtshoulderselector);

            shirtlengthspinner = sizedetailpannel.findViewById(R.id.shirtlengthselector);
            shirtchestspinner = sizedetailpannel.findViewById(R.id.shirtchestselector);
            shirtsleevespinner = sizedetailpannel.findViewById(R.id.shirtsleeveselector);

            frocklengthspinner = sizedetailpannel.findViewById(R.id.frocklengthselector);
            frockwaistspinner = sizedetailpannel.findViewById(R.id.frockwaistselector);
            frockchestspinner = sizedetailpannel.findViewById(R.id.frockchestselector);
            frockhipspinner = sizedetailpannel.findViewById(R.id.frockhipselector);

            jumpsuitlengthspinner = sizedetailpannel.findViewById(R.id.jumpsuitlengthselector);
            jumpsuitwaistspinner = sizedetailpannel.findViewById(R.id.jumpsuitwaistselector);
            jumpsuitchestspinner = sizedetailpannel.findViewById(R.id.jumpsuitchestselector);
            jumpsuithipspinner = sizedetailpannel.findViewById(R.id.jumpsuithipselector);

            final ImageView pantlengtherror = sizedetailpannel.findViewById(R.id.pantlengtherrorimg);
            final ImageView pantwaisterror = sizedetailpannel.findViewById(R.id.pantwaisterrorimg);

            final ImageView tshirtlengtherror = sizedetailpannel.findViewById(R.id.tshirtlengtherrorimg);
            final ImageView tshirtchesterror = sizedetailpannel.findViewById(R.id.tshirtchesterrorimg);
            final ImageView tshirtshouldererror = sizedetailpannel.findViewById(R.id.tshirtshouldererrorimg);

            final ImageView shirtlengtherror = sizedetailpannel.findViewById(R.id.shirtlengtherrorimg);
            final ImageView shirtchesterror = sizedetailpannel.findViewById(R.id.shirtchesterrorimg);
            final ImageView shirtsleeveerror = sizedetailpannel.findViewById(R.id.shirtsleeveerrorimg);

            final ImageView frocklengtherror = sizedetailpannel.findViewById(R.id.frocklengtherrorimg);
            final ImageView frockwaisterror = sizedetailpannel.findViewById(R.id.frockwaisterrorimg);
            final ImageView frockchesterror = sizedetailpannel.findViewById(R.id.frockchesterrorimg);
            final ImageView frockhiperror = sizedetailpannel.findViewById(R.id.frockhiperrorimg);

            final ImageView jumpsuitlengtherror = sizedetailpannel.findViewById(R.id.jumpsuitlengtherrorimg);
            final ImageView jumpsuitwaisterror = sizedetailpannel.findViewById(R.id.jumpsuitwaisterrorimg);
            final ImageView jumpsuitchesterror = sizedetailpannel.findViewById(R.id.jumpsuitchesterrorimg);
            final ImageView jumpsuithipeerror = sizedetailpannel.findViewById(R.id.jumpsuithiperrorimg);


            sizerangesbtn = sizedetailpannel.findViewById(R.id.productsizedatabtn);
            TextView sizetype = sizedetailpannel.findViewById(R.id.sizetypetxt);
            TextView sizedetails = sizedetailpannel.findViewById(R.id.sizedetailstxt);

            sizetype.setText(sizeheadertxt);
            sizedetails.setText(Html.fromHtml(sizedata));

            sizerangesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String str ="" ;
                    int checkdata = 0 , errorcount=0;


                    if (pantrootlayout.getVisibility() ==View.VISIBLE)
                    {
                        if(pantlengthspinner.getSelectedItemPosition() == 0)
                        {
                            pantlengtherror.setVisibility(View.VISIBLE);
                            checkdata+=1;
                            errorcount+=1;
                        }

                        else
                        {
                            pantlengtherror.setVisibility(View.GONE);
                        }

                        if(pantwaistspinner.getSelectedItemPosition() == 0)
                        {
                            pantwaisterror.setVisibility(View.VISIBLE);
                            checkdata+=1;
                            errorcount+=1;
                        }

                        else
                        {
                            pantwaisterror.setVisibility(View.GONE);
                        }

                        if(checkdata == 0)
                        {
                            str += "<br><u>" + sizepanttitle.getText().toString().trim() + "</u><br><br>L = " + pantlengthspinner.getSelectedItem().toString() + "  |  W = " + pantwaistspinner.getSelectedItem().toString().trim();
                        }

                        str+="<br>";

                        checkdata=0;
                    }

                    if (frockrootlayout.getVisibility() ==View.VISIBLE) {

                        if (frocklengthspinner.getSelectedItemPosition() == 0) {
                            frocklengtherror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            frocklengtherror.setVisibility(View.GONE);
                        }


                        if (frockwaistspinner.getSelectedItemPosition() == 0) {
                            frockwaisterror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            frockwaisterror.setVisibility(View.GONE);
                        }


                        if (checkdata == 0) {

                            str += "<br><u>" + sizefrocktitle.getText().toString().trim() + "</u><br><br>L = " + frocklengthspinner.getSelectedItem().toString().trim() +
                                    "  |  W = " + frockwaistspinner.getSelectedItem().toString().trim();
                        }

                        if (frockchestlayout.getVisibility() == View.VISIBLE) {
                            if (frockchestspinner.getSelectedItemPosition() == 0) {
                                frockchesterror.setVisibility(View.VISIBLE);
                                errorcount+=1;
                            }

                            else
                            {
                                frockchesterror.setVisibility(View.GONE);
                                str += " |  C = " + frockchestspinner.getSelectedItem().toString().trim();
                            }
                        }

                        if (frockhiplayout.getVisibility() == View.VISIBLE) {
                            if (frockhipspinner.getSelectedItemPosition() == 0) {
                                frockhiperror.setVisibility(View.VISIBLE);
                                errorcount+=1;
                            }

                            else
                            {
                                frockhiperror.setVisibility(View.GONE);
                                str += "| H = " + frockhipspinner.getSelectedItem().toString().trim();
                            }

                        }

                        str+="<br>";
                        checkdata = 0;

                    }

                    if (tshirtrootlayout.getVisibility() ==View.VISIBLE) {

                        if (tshirtlengthspinner.getSelectedItemPosition() == 0) {
                            tshirtlengtherror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            tshirtlengtherror.setVisibility(View.GONE);
                        }

                        if (checkdata == 0)
                        {
                            str += "<br><u>" + sizetshirttitle.getText().toString().trim() + "</u><br><br>L = " + tshirtlengthspinner.getSelectedItem().toString().trim();
                        }

                        if(tshirtchestlayout.getVisibility() == View.VISIBLE)
                        {
                            if (tshirtchestspinner.getSelectedItemPosition() == 0)
                            {
                                tshirtchesterror.setVisibility(View.VISIBLE);
                                errorcount+=1;
                            }

                            else
                            {
                                tshirtchesterror.setVisibility(View.GONE);
                                str += "  |  C = " + tshirtchestspinner.getSelectedItem().toString().trim();
                            }
                        }

                        if(tshirtshoulderlayout.getVisibility() == View.VISIBLE)
                        {
                            if(tshirtshoulderspinner.getSelectedItemPosition() == 0)
                            {
                                tshirtshouldererror.setVisibility(View.VISIBLE);
                                errorcount+=1;
                            }

                            else
                            {
                                tshirtshouldererror.setVisibility(View.GONE);
                                str += "  |  S = " + tshirtshoulderspinner.getSelectedItem().toString().trim();
                            }
                        }


                        str+="<br>";

                        checkdata = 0;


                    }

                    if (shirtrootlayout.getVisibility() ==View.VISIBLE)
                    {

                        if (shirtlengthspinner.getSelectedItemPosition() == 0) {
                            shirtlengtherror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            shirtlengtherror.setVisibility(View.GONE);
                        }

                        if (checkdata == 0) {

                            str += "<br><u>" + sizeshirttitle.getText().toString().trim() + "</u><br><br>L = " + shirtlengthspinner.getSelectedItem().toString().trim();
                        }

                        if(shirtchestlayout.getVisibility() == View.VISIBLE)
                        {
                            if (shirtchestspinner.getSelectedItemPosition() == 0)
                            {
                                shirtchesterror.setVisibility(View.VISIBLE);
                                errorcount+=1;
                            }

                            else {
                                shirtchesterror.setVisibility(View.GONE);
                                str += "  |  C = " + shirtchestspinner.getSelectedItem().toString().trim();
                            }
                        }

                        if(shirtsleevelayout.getVisibility() == View.VISIBLE)
                        {
                            if(shirtsleevespinner.getSelectedItemPosition() == 0)
                            {
                                shirtsleeveerror.setVisibility(View.VISIBLE);
                                errorcount+=1;
                            }

                            else
                            {
                                shirtsleeveerror.setVisibility(View.GONE);
                                str+="  |  Sl  =  "+shirtsleevespinner.getSelectedItem().toString().trim();
                            }

                        }

                        str+="<br>";

                        checkdata = 0;
                    }

                    if (jumpsuitrootlayout.getVisibility() ==View.VISIBLE)
                    {

                        if (jumpsuitlengthspinner.getSelectedItemPosition() == 0) {
                            jumpsuitlengtherror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            jumpsuitlengtherror.setVisibility(View.GONE);
                        }

                        if (jumpsuitchestspinner.getSelectedItemPosition() == 0) {
                            jumpsuitchesterror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            jumpsuitchesterror.setVisibility(View.GONE);
                        }


                        if (jumpsuitwaistspinner.getSelectedItemPosition() == 0) {
                            jumpsuitwaisterror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            jumpsuitwaisterror.setVisibility(View.GONE);
                        }

                        if (jumpsuithipspinner.getSelectedItemPosition() == 0) {
                            jumpsuithipeerror.setVisibility(View.VISIBLE);
                            checkdata += 1;
                            errorcount+=1;
                        }

                        else
                        {
                            jumpsuithipeerror.setVisibility(View.GONE);
                        }


                        if (checkdata == 0) {

                            str += "<br><u>" + sizejumpsuittitle.getText().toString().trim() +
                                    "</u><br><br>L = " + jumpsuitlengthspinner.getSelectedItem().toString().trim() +
                                    "  |  W = " + jumpsuitwaistspinner.getSelectedItem().toString().trim() +
                                    "  |  C = " + jumpsuitchestspinner.getSelectedItem().toString().trim() +
                                    "  |  H = " + jumpsuithipspinner.getSelectedItem().toString().trim();
                        }

                        str+="<br>";
                        checkdata = 0;

                    }


                    if(errorcount == 0)
                    {
                        setsizedata(str);
                        sizedetailpannel.dismiss();
                    }


                    errorcount = 0;



                }
            });

            sizexsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sizexsbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                    sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                    sizexsbtn.setTextColor(Color.WHITE);
                    sizesbtn.setTextColor(Color.BLACK);
                    sizembtn.setTextColor(Color.BLACK);
                    sizelbtn.setTextColor(Color.BLACK);
                    sizexlbtn.setTextColor(Color.BLACK);
                    sizexxlbtn.setTextColor(Color.BLACK);

                    sizeheadertxt = "Selected Size : Extra Small";
                    sizecattype = "XSmall";
              new getsizecatdata().execute();
                }
            });

            sizesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizesbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                    sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                    sizexsbtn.setTextColor(Color.BLACK);
                    sizesbtn.setTextColor(Color.WHITE);
                    sizembtn.setTextColor(Color.BLACK);
                    sizelbtn.setTextColor(Color.BLACK);
                    sizexlbtn.setTextColor(Color.BLACK);
                    sizexxlbtn.setTextColor(Color.BLACK);


                    sizeheadertxt = "Selected Size : Small";
                    sizecattype = "Small";
              new getsizecatdata().execute();

                }
            });


            sizembtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizembtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                    sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                    sizexsbtn.setTextColor(Color.BLACK);
                    sizesbtn.setTextColor(Color.BLACK);
                    sizembtn.setTextColor(Color.WHITE);
                    sizelbtn.setTextColor(Color.BLACK);
                    sizexlbtn.setTextColor(Color.BLACK);
                    sizexxlbtn.setTextColor(Color.BLACK);


                    sizeheadertxt = "Selected Size : Medium";
                    sizecattype = "Medium";
              new getsizecatdata().execute();
                }
            });

            sizelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizelbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                    sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                    sizexsbtn.setTextColor(Color.BLACK);
                    sizesbtn.setTextColor(Color.BLACK);
                    sizembtn.setTextColor(Color.BLACK);
                    sizelbtn.setTextColor(Color.WHITE);
                    sizexlbtn.setTextColor(Color.BLACK);
                    sizexxlbtn.setTextColor(Color.BLACK);

                    sizeheadertxt = "Selected Size : Large" ;
                    sizecattype = "Large";
              new getsizecatdata().execute();
                }
            });


            sizexlbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexlbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
                    sizexxlbtn.setBackgroundResource(R.drawable.colorincirlcle);

                    sizexsbtn.setTextColor(Color.BLACK);
                    sizesbtn.setTextColor(Color.BLACK);
                    sizembtn.setTextColor(Color.BLACK);
                    sizelbtn.setTextColor(Color.BLACK);
                    sizexlbtn.setTextColor(Color.WHITE);
                    sizexxlbtn.setTextColor(Color.BLACK);


                    sizeheadertxt = "Selected Size : Extra Large";
                    sizecattype = "XLarge";
              new getsizecatdata().execute();
                }
            });


            sizexxlbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sizexsbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizesbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizembtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizelbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexlbtn.setBackgroundResource(R.drawable.colorincirlcle);
                    sizexxlbtn.setBackgroundResource(R.drawable.selectedcolorincircle);

                    sizexsbtn.setTextColor(Color.BLACK);
                    sizesbtn.setTextColor(Color.BLACK);
                    sizembtn.setTextColor(Color.BLACK);
                    sizelbtn.setTextColor(Color.BLACK);
                    sizexlbtn.setTextColor(Color.BLACK);
                    sizexxlbtn.setTextColor(Color.WHITE);


                    sizeheadertxt = "Selected Size : Extra Extra Large" ;
                    sizecattype = "XXLarge";
              new getsizecatdata().execute();
                }
            });




            ImageView changepannelclosebtn = sizedetailpannel.findViewById(R.id.mycartsizedetaildialogclose);
            changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sizedetailpannel.dismiss();
                }
            });


            setadaptertype(code ,typetitle , type);

        }


    private void  setdresscodes(String selectedsize)
    {
        if(selectedsize.equals("XSmall"))
        {
            sizexsbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
            sizexsbtn.setTextColor(Color.WHITE);
        }

        if(selectedsize.equals("Small"))
        {
            sizesbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
            sizesbtn.setTextColor(Color.WHITE);
        }

        if(selectedsize.equals("Medium"))
        {
            sizembtn.setBackgroundResource(R.drawable.selectedcolorincircle);
            sizembtn.setTextColor(Color.WHITE);
        }

        if(selectedsize.equals("Large"))
        {
            sizelbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
            sizelbtn.setTextColor(Color.WHITE);
        }

        if(selectedsize.equals("XLarge"))
        {
            sizexlbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
            sizexlbtn.setTextColor(Color.WHITE);
        }

        if(selectedsize.equals("XXLarge"))
        {
            sizexxlbtn.setBackgroundResource(R.drawable.selectedcolorincircle);
            sizexxlbtn.setTextColor(Color.WHITE);
        }
    }

    private void  setsizedetails(String sizeaviable)
    {
        String sizedata [] = sizeaviable.split(";");

        for (int pos = 0 ; pos < sizedata.length ; pos++)
        {
            switch (sizedata[pos])
            {
                case "#XS" :
                    sizexsbtn.setVisibility(View.VISIBLE);
                    break;

                case "#S" :
                    sizesbtn.setVisibility(View.VISIBLE);
                    break;

                case "#M" :
                    sizembtn.setVisibility(View.VISIBLE);
                    break;

                case "#L" :
                    sizelbtn.setVisibility(View.VISIBLE);
                    break;

                case "#XL" :
                    sizexlbtn.setVisibility(View.VISIBLE);
                    break;

                case "#XXL" :
                    sizexxlbtn.setVisibility(View.VISIBLE);
                    break;

                case "#Ascr" :
                    checksizeopt = true;
                    break;

            }
        }
    }

    private void setPantadap(String code)
    {

        if(code.trim().equals(kidspantsmall.trim()))
        {
            getadapter.setdata(context ,kidspantsmall, context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizesmalldapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizesmalldapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(kidspantmedium.trim()))
        {

            getadapter.setdata(context ,kidspantmedium, context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizemediumadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizemediumadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(kidspantlarge.trim()))
        {

            getadapter.setdata(context ,kidspantlarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizelargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizelargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(kidspantxlarge.trim()))
        {

            getadapter.setdata(context ,kidspantxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidspantlengthsizexlargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidspantwaistsizexlargeladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(menspantsmall.trim()))
        {

            getadapter.setdata(context ,menspantsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizesmalladapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizesmalladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantmedium.trim()))
        {

            getadapter.setdata(context ,menspantsmall , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizemediumadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizemediumadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantlarge.trim()))
        {

            getadapter.setdata(context ,menspantlarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizelargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizelargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantxlarge.trim()))
        {

            getadapter.setdata(context ,menspantxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizeextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizeextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(menspantxxlarge.trim()))
        {

            getadapter.setdata(context ,menspantxxlarge , context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenspantlengthsizeextraextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setMenspantwaistsizeextraextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantxsmall.trim()))
        {

            getadapter.setdata(context ,womenpantxsmall , context.getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizeextrasmalladapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizeextrasmalladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantsmall.trim()))
        {

            getadapter.setdata(context ,womenpantsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizesmalladapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizesmalladapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


        if(code.trim().equals(womenpantmedium.trim()))
        {

            getadapter.setdata(context ,womenpantmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizemediumadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizemediumadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantlarge.trim()))
        {

            getadapter.setdata(context ,womenpantlarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizelargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizelargeadaopter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantxlarge.trim()))
        {

            getadapter.setdata(context ,womenpantxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizeextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizeextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }

        if(code.trim().equals(womenpantxxlarge.trim()))
        {

            getadapter.setdata(context ,womenpantxxlarge , context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenpantlengthsizeextraextralargeadapter();
            pantlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenpantwaistsizeextraextralargeadapter();
            pantwaistspinner.setAdapter(waistadapter);

        }


    }

    private void setFrockadap(String code)
    {

        if(code.trim().equals(kidsfrocksmall.trim()))
        {

            getadapter.setdata(context ,kidsfrocksmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizesmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizesmalladapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizesmalladapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsfrockmedium.trim()))
        {

            getadapter.setdata(context ,kidsfrockmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizemediumadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizemediumadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizemediumadapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsfrocklarge.trim()))
        {

            getadapter.setdata(context ,kidsfrocklarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizelargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizelargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizelargeadapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(kidsfrockxlarge.trim()))
        {

            getadapter.setdata(context ,kidsfrockxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsfrocklengthsizeextralargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsfrockwaistsizeextralargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsfrockchestsizeextralargeadapter();
            frockchestspinner.setAdapter(chestadapter);


            frockhiplayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womenfrockxsmall.trim()))
        {

            getadapter.setdata(context ,womenfrockxsmall , context.getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizeextrasmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizeextrasmalladapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizeextrasmalladapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womenfrocksmall.trim()))
        {

            getadapter.setdata(context ,womenfrocksmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizesmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizesmalladapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizesmalladapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womenfrockmedium.trim()))
        {

            getadapter.setdata(context ,womenfrockmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizemediumadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizemediumadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizemediumadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womenfrocklarge.trim()))
        {

            getadapter.setdata(context ,womenfrocklarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizelargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizelargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizelargeadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womenfrockxlarge.trim()))
        {

            getadapter.setdata(context ,womenfrockxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizeextralargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizeextralargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizeextralargeadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womenfrockxxlarge.trim()))
        {

            getadapter.setdata(context ,womenfrockxxlarge , context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenfrocklengthsizeextraextralargeadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenfrockwaistsizeextraextralargeadapter();
            frockwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenfrockhipsizeextraextralargeadapter();
            frockhipspinner.setAdapter(hipadapter);


            frockchestlayout.setVisibility(View.GONE);

        }

    }

    private void setTshirtadap(String code)
    {
        if(code.trim().equals(kidstshirtsmall.trim()))
        {

            getadapter.setdata(context ,kidstshirtsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizesmalladapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizesmalladapter();
            frockwaistspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidstshirtmedium.trim()))
        {

            getadapter.setdata(context ,kidstshirtmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizemediumadapter();
            frocklengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizemediumadapter();
            frockwaistspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(kidstshirtlarge.trim()))
        {

            getadapter.setdata(context ,kidstshirtlarge, context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizelargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizelargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidstshirtxlarge.trim()))
        {

            getadapter.setdata(context ,kidstshirtxlarge, context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizeextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidstshirtxlarge.trim()))
        {

            getadapter.setdata(context ,kidstshirtxlarge, context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidstshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidstshirtchestsizeextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);


            tshirtshoulderlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(menstshirtsmall.trim()))
        {

            getadapter.setdata(context ,menstshirtsmall, context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizesmalladapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizesmalladaper();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizesmalladapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtmedium.trim()))
        {

            getadapter.setdata(context ,menstshirtmedium, context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizemediumadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizemediumadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizemediumadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtlarge.trim()))
        {

            getadapter.setdata(context ,menstshirtlarge, context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizelargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizelargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizelargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtxlarge.trim()))
        {

            getadapter.setdata(context ,menstshirtxlarge, context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizeextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizeeextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }

        if(code.trim().equals(menstshirtxxlarge.trim()))
        {

            getadapter.setdata(context ,menstshirtxxlarge, context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMenstshirtlengthsizeextraextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMenstshirtchestsizeextraextralargeadapter();
            tshirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setMenstshirtshouldersizeextraextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

        }


        if(code.trim().equals(womentshirtxsmall.trim()))
        {

            getadapter.setdata(context ,womentshirtxsmall, context.getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizeextrasmalladapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizeextrasmalladapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womentshirtsmall.trim()))
        {

            getadapter.setdata(context ,womentshirtsmall, context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizesmalladapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizesmalladapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }


        if(code.trim().equals(womentshirtmedium.trim()))
        {

            getadapter.setdata(context ,womentshirtmedium, context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizemediumadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizemediumadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womentshirtlarge.trim()))
        {

            getadapter.setdata(context ,womentshirtlarge, context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizelargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizelargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(womentshirtxlarge.trim()))
        {

            getadapter.setdata(context ,womentshirtxlarge, context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizeextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizeextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womentshirtxxlarge.trim()))
        {

            getadapter.setdata(context ,womentshirtxxlarge, context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomentshirtlengthsizeextraextralargeadapter();
            tshirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> shoulderadapter = getadapter.setWomentshirtshouldersizeextraextralargeadapter();
            tshirtshoulderspinner.setAdapter(shoulderadapter);

            tshirtchestlayout.setVisibility(View.GONE);
        }


    }


    private void setShirtadap(String code)
    {

        if(code.trim().equals(kidsshirtsmall.trim()))
        {

            getadapter.setdata(context ,kidsshirtsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizesmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizesmalladapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsshirtmedium.trim()))
        {

            getadapter.setdata(context ,kidsshirtmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizemediumadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizemediumadapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsshirtlarge.trim()))
        {

            getadapter.setdata(context ,kidsshirtlarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizelargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizelargeadapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(kidsshirtxlarge.trim()))
        {

            getadapter.setdata(context ,kidsshirtxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsshirtlengthsizeextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsshirtchestsizeextralargeadapter();
            shirtchestspinner.setAdapter(chestadapter);


            shirtsleevelayout.setVisibility(View.GONE);

        }

        if(code.trim().equals(mensshirtsmall.trim()))
        {

            getadapter.setdata(context ,mensshirtsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizesmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizesmalladapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizesmalladapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }


        if(code.trim().equals(mensshirtmedium.trim()))
        {

            getadapter.setdata(context ,mensshirtmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizemediumadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizemediumadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizemediumadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(mensshirtlarge.trim()))
        {

            getadapter.setdata(context ,mensshirtlarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizelargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizelargeadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizelargeadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(mensshirtxlarge.trim()))
        {

            getadapter.setdata(context ,mensshirtxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizeextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizeextralargeadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizeextraextralargeadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(mensshirtxxlarge.trim()))
        {

            getadapter.setdata(context ,mensshirtxxlarge , context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setMensshirtlengthsizeextraextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setMensshirtchestsizeextralargeadapter();
            shirtchestspinner.setAdapter(chestadapter);

            ArrayAdapter<CharSequence> sleevesadapter = getadapter.setMensshirtsleevesizeextraextralargeadapter();
            shirtsleevespinner.setAdapter(sleevesadapter);

        }

        if(code.trim().equals(womenshirtxsmall.trim()))
        {

            getadapter.setdata(context ,womenshirtxsmall , context.getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizeextrasmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtsmall.trim()))
        {

            getadapter.setdata(context ,womenshirtsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizesmalladapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtmedium.trim()))
        {

            getadapter.setdata(context ,womenshirtmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizemediumadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtlarge.trim()))
        {

            getadapter.setdata(context ,womenshirtlarge , context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizelargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }


        if(code.trim().equals(womenshirtxlarge.trim()))
        {

            getadapter.setdata(context ,womenshirtxlarge , context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizeextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

        if(code.trim().equals(womenshirtxxlarge.trim()))
        {

            getadapter.setdata(context ,womenshirtxxlarge , context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenshirtlengthsizeextraextralargeadapter();
            shirtlengthspinner.setAdapter(lengthadapter);

            shirtsleevelayout.setVisibility(View.GONE);
            shirtchestlayout.setVisibility(View.GONE);
        }

    }

    private void setJumpsuitadap(String code)
    {

        if(code.trim().equals(kidsjumpsuitsmall.trim()))
        {

            getadapter.setdata(context ,kidsjumpsuitsmall , context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizesmalladapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizesmalladapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizesmalladapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizesmalladapter();
            jumpsuithipspinner.setAdapter(hipadapter);

        }

        if(code.trim().equals(kidsjumpsuitmedium.trim()))
        {

            getadapter.setdata(context ,kidsjumpsuitmedium , context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizemediumadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizemediumadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizemediumadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizemediumadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(kidsjumpsuitlarge.trim()))
        {

            getadapter.setdata(context ,kidsjumpsuitlarge, context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizelargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizelargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizelargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizelargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(kidsjumpsuitxlarge.trim()))
        {

            getadapter.setdata(context ,kidsjumpsuitxlarge, context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setkidsjumpsuitlengthsizeextralargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setkidsjumpsuitwaistsizeextralargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setkidsjumpsuitchestsizeextralargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setkidsjumpsuithipsizeextralargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitxsmall.trim()))
        {

            getadapter.setdata(context ,womenjumpsuitxsmall, context.getResources().getString(R.string.sizexsmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizeextrasmalladapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizeextrasmalladapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizeextrasmalladapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizeextrasmalladapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }


        if(code.trim().equals(womenjumpsuitsmall.trim()))
        {

            getadapter.setdata(context ,womenjumpsuitsmall, context.getResources().getString(R.string.sizesmall));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizesmalladapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizesmalladapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizesmalladapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizesmalladapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitmedium.trim()))
        {

            getadapter.setdata(context ,womenjumpsuitmedium, context.getResources().getString(R.string.sizemedium));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizemediumadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizemediumadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizemediumadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizemediumadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitlarge.trim()))
        {

            getadapter.setdata(context ,womenjumpsuitlarge, context.getResources().getString(R.string.sizelarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizelargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizelargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizelargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizelargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitxlarge.trim()))
        {

            getadapter.setdata(context ,womenjumpsuitxlarge, context.getResources().getString(R.string.sizexlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizeextralargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizeextralargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizeextralargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizeextralargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }

        if(code.trim().equals(womenjumpsuitxxlarge.trim()))
        {

            getadapter.setdata(context ,womenjumpsuitxxlarge, context.getResources().getString(R.string.sizexxlarge));
            ArrayAdapter<CharSequence> lengthadapter = getadapter.setWomenjumpsuitlengthsizeextralargeadapter();
            jumpsuitlengthspinner.setAdapter(lengthadapter);

            ArrayAdapter<CharSequence> waistadapter = getadapter.setWomenjumpsuitwaistsizeextraextralargeadapter();
            jumpsuitwaistspinner.setAdapter(waistadapter);

            ArrayAdapter<CharSequence> chestadapter = getadapter.setWomenjumpsuitchestsizeextraextralargeadapter();
            jumpsuitchestspinner.setAdapter(chestadapter);


            ArrayAdapter<CharSequence> hipadapter = getadapter.setWomenjumpsuithipsizeextraextralargeadapter();
            jumpsuithipspinner.setAdapter(hipadapter);
        }


    }


    private void setsizedata(String str)
    {
        DatabaseReference sizedataref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/HDHSHS/mycart/"+productid+"/sizedata");
        sizedataref.setValue(str);

        DatabaseReference sizeselectedref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/HDHSHS/mycart/"+productid+"/sizeselected");
        sizeselectedref.setValue(sizeheadertxt);

        DatabaseReference sizecatref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/HDHSHS/mycart/"+productid+"/sizecattype");
        sizecatref.setValue(sizecattype);


    }

    private void setquant(String quant)
    {
        DatabaseReference dressquantref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/HDHSHS/mycart/"+productid+"/dressquant");
        dressquantref.setValue(sizecattype);
    }


    private void inistilizedata()
    {
        kidspantsmall = context.getResources().getString(R.string.kps);
        kidspantmedium = context.getResources().getString(R.string.kpm);
        kidspantlarge = context.getResources().getString(R.string.kpl);
        kidspantxlarge = context.getResources().getString(R.string.kpxl);

        kidsjumpsuitsmall = context.getResources().getString(R.string.kjss);
        kidsjumpsuitmedium = context.getResources().getString(R.string.kjsm);
        kidsjumpsuitlarge = context.getResources().getString(R.string.kjsl);
        kidsjumpsuitxlarge = context.getResources().getString(R.string.kjsxl);

        kidsfrocksmall = context.getResources().getString(R.string.kfs);
        kidsfrockmedium = context.getResources().getString(R.string.kfm);
        kidsfrocklarge = context.getResources().getString(R.string.kfl);
        kidsfrockxlarge = context.getResources().getString(R.string.kfxl);

        kidstshirtsmall = context.getResources().getString(R.string.ktss);
        kidstshirtmedium = context.getResources().getString(R.string.ktsm);
        kidstshirtlarge = context.getResources().getString(R.string.ktsl);
        kidstshirtxlarge = context.getResources().getString(R.string.ktsxl);

        kidsshirtsmall = context.getResources().getString(R.string.kss);
        kidsshirtmedium = context.getResources().getString(R.string.ksm);
        kidsshirtlarge = context.getResources().getString(R.string.ksl);
        kidsshirtxlarge = context.getResources().getString(R.string.ksxl);


        menspantsmall = context.getResources().getString(R.string.mps);
        menspantmedium = context.getResources().getString(R.string.mpm);
        menspantlarge= context.getResources().getString(R.string.mpl);
        menspantxlarge = context.getResources().getString(R.string.mpxl);
        menspantxxlarge = context.getResources().getString(R.string.mpxxl);

        menstshirtsmall = context.getResources().getString(R.string.mtss);
        menstshirtmedium = context.getResources().getString(R.string.mtsm);
        menstshirtlarge= context.getResources().getString(R.string.mtsl);
        menstshirtxlarge = context.getResources().getString(R.string.mtsxl);
        menstshirtxxlarge = context.getResources().getString(R.string.mtsxxl);

        mensshirtsmall = context.getResources().getString(R.string.mss);
        mensshirtmedium = context.getResources().getString(R.string.msm);
        mensshirtlarge= context.getResources().getString(R.string.msl);
        mensshirtxlarge = context.getResources().getString(R.string.msxl);
        mensshirtxxlarge = context.getResources().getString(R.string.msxxl);

        womenpantxsmall =  context.getResources().getString(R.string.wpxs);
        womenpantsmall =  context.getResources().getString(R.string.wps);
        womenpantmedium =  context.getResources().getString(R.string.wpm);
        womenpantlarge =  context.getResources().getString(R.string.wpl);
        womenpantxlarge =  context.getResources().getString(R.string.wpxl);
        womenpantxxlarge =  context.getResources().getString(R.string.wpxxl);

        womenjumpsuitxsmall =  context.getResources().getString(R.string.wjsxs);
        womenjumpsuitsmall =  context.getResources().getString(R.string.wjss);
        womenjumpsuitmedium =  context.getResources().getString(R.string.wjsm);
        womenjumpsuitlarge =  context.getResources().getString(R.string.wjsl);
        womenjumpsuitxlarge =  context.getResources().getString(R.string.wjsxl);
        womenjumpsuitxxlarge =  context.getResources().getString(R.string.wjsxxl);


        womenfrockxsmall =  context.getResources().getString(R.string.wfxs);
        womenfrocksmall =  context.getResources().getString(R.string.wfs);
        womenfrockmedium =  context.getResources().getString(R.string.wfm);
        womenfrocklarge =  context.getResources().getString(R.string.wfl);
        womenfrockxlarge =  context.getResources().getString(R.string.wfxl);
        womenfrockxxlarge =  context.getResources().getString(R.string.wfxxl);


        womentshirtxsmall =  context.getResources().getString(R.string.wtsxs);
        womentshirtsmall =  context.getResources().getString(R.string.wtss);
        womentshirtmedium =  context.getResources().getString(R.string.wtsm);
        womentshirtlarge =  context.getResources().getString(R.string.wtsl);
        womentshirtxlarge =  context.getResources().getString(R.string.wtsxl);
        womentshirtxxlarge =  context.getResources().getString(R.string.wtsxxl);

        womenshirtxsmall =  context.getResources().getString(R.string.wsxs);
        womenshirtsmall =  context.getResources().getString(R.string.wss);
        womenshirtmedium =  context.getResources().getString(R.string.wsm);
        womenshirtlarge =  context.getResources().getString(R.string.wsl);
        womenshirtxlarge =  context.getResources().getString(R.string.wsxl);
        womenshirtxxlarge =  context.getResources().getString(R.string.wsxxl);

        sizedetailpannel = new Dialog(context);
        sizedetailpannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sizedetailpannel.getWindow().setWindowAnimations(R.style.animateddialog);
        sizedetailpannel.setCanceledOnTouchOutside(false);
        sizedetailpannel.setCancelable(true);
        sizedetailpannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sizedetailpannel.setContentView(R.layout.mycartsizeselector);
        pantrootlayout = sizedetailpannel.findViewById(R.id.pantrootlayout);
        tshirtrootlayout = sizedetailpannel.findViewById(R.id.tshirtrootlayout);
        shirtrootlayout = sizedetailpannel.findViewById(R.id.shirtrootlayout);
        frockrootlayout = sizedetailpannel.findViewById(R.id.frockrootlayout);
        jumpsuitrootlayout = sizedetailpannel.findViewById(R.id.jumpsuitrootlayout);

        sizexsbtn = sizedetailpannel.findViewById(R.id.xsbtn);
        sizesbtn = sizedetailpannel.findViewById(R.id.sbtn);
        sizembtn = sizedetailpannel.findViewById(R.id.mbtn);
        sizelbtn = sizedetailpannel.findViewById(R.id.lbtn);
        sizexlbtn = sizedetailpannel.findViewById(R.id.xlbtn);
        sizexxlbtn = sizedetailpannel.findViewById(R.id.xxlbtn);
    }



    private void setadaptertype(String code ,String typetitle , String type )
    {
        if(code.substring(2).equals(context.getResources().getString(R.string.p)))
        {
            pantrootlayout.setVisibility(View.VISIBLE);
            sizepanttitle.setText(typetitle);
            setspinnerscrolling(pantlengthspinner);
            setspinnerscrolling(pantwaistspinner);
            setPantadap(code+type);
        }

        if(code.substring(2).equals(context.getResources().getString(R.string.f)))
        {
            frockrootlayout.setVisibility(View.VISIBLE);
            sizefrocktitle.setText(typetitle);
            setspinnerscrolling(frocklengthspinner);
            setspinnerscrolling(frockwaistspinner);
            setspinnerscrolling(frockchestspinner);
            setspinnerscrolling(frockhipspinner);
            setFrockadap(code+type);
        }

        if(code.substring(2).equals(context.getResources().getString(R.string.js)))
        {
            jumpsuitrootlayout.setVisibility(View.VISIBLE);
            sizejumpsuittitle.setText(typetitle);
            setspinnerscrolling(jumpsuitlengthspinner);
            setspinnerscrolling(jumpsuitwaistspinner);
            setspinnerscrolling(jumpsuitchestspinner);
            setspinnerscrolling(jumpsuithipspinner);
            setJumpsuitadap(code+type);
        }

        if(code.substring(2).equals(context.getResources().getString(R.string.ts)))
        {
            tshirtrootlayout.setVisibility(View.VISIBLE);
            sizetshirttitle.setText(typetitle);
            setspinnerscrolling(tshirtlengthspinner);
            setspinnerscrolling(tshirtchestspinner);
            setspinnerscrolling(tshirtshoulderspinner);
            setTshirtadap(code+type);
        }

        if(code.substring(2).equals(context.getResources().getString(R.string.s)))
        {
            shirtrootlayout.setVisibility(View.VISIBLE);
            sizeshirttitle.setText(typetitle);
            setspinnerscrolling(shirtlengthspinner);
            setspinnerscrolling(shirtchestspinner);
            setspinnerscrolling(shirtsleevespinner);
            setShirtadap(code+type);
        }
    }


    private void setspinnerscrolling(Spinner spinner)
    {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(shirtchestspinner);

            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
    }


    private void getadaptertype(String code ,String typetitle , String headertxt ,String cattype)
    {

        String adaptypedata [] = code.split(";");
        String typeTitledata [] = typetitle.split(";");

        for (int pos = 0 ; pos < adaptypedata.length ; pos++)
        {
            opensizedetails(adaptypedata[pos],typeTitledata[pos], headertxt , cattype);
        }

    }


    public class getsizecatdata extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            getadaptertype(productdresscode , sizetypetitle,sizeheadertxt,sizecattype);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            sizedetailpannel.show();

            Window window = sizedetailpannel.getWindow();
            window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }


}
