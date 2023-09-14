package com.trendsetter.deckout_admin.Orderlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.trendsetter.deckout_admin.Extra.linktosmsserver;
import com.trendsetter.deckout_admin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Orderlistitemholder extends RecyclerView.ViewHolder
{
    ImageView itemimg;
    CardView cardView;
    LinearLayout trackorderbtn , cancelorderbtn , genbillbtn;
    Button updatetrackstatsbtn;
    Spinner orderstatsspinner;
    TextView ordernametxt , ordertypetxt , orderpricetxt , orderquantntypetxt , orderstatustxt , orderOrdernotxt;
    RelativeLayout  trackandcancedlayout;
    ArrayList<String> trackdates ;
    Dialog orderstatuspannel;

    public Orderlistitemholder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.myorderscard);

        trackorderbtn = itemView.findViewById(R.id.trackorderbtn);
        cancelorderbtn = itemView.findViewById(R.id.cancelorderbtn);
        genbillbtn = itemView.findViewById(R.id.genbillbtnlayout);

        itemimg = itemView.findViewById(R.id.myorderimage);

        ordernametxt = itemView.findViewById(R.id.ordername);
        ordertypetxt = itemView.findViewById(R.id.ordertype);
        orderpricetxt = itemView.findViewById(R.id.orderprice);
        orderquantntypetxt = itemView.findViewById(R.id.orderquantntype);
        orderstatustxt = itemView.findViewById(R.id.orderstatus);
        orderOrdernotxt = itemView.findViewById(R.id.orderOrderno);

        trackandcancedlayout = itemView.findViewById(R.id.trackandcancedlayout);


    }


    public void openbilldetails(Context context , String orderidtxt , String orderduedate , String orderinitdate , String custname ,
                                String custmobno , String custdelhaddrs , String paymenttype , String totalamount)
    {
        context.startActivity(new Intent(context, orderproductlist.class).putExtra("orderidcode" , orderidtxt)
                .putExtra("orderduedate" , orderduedate).putExtra("orderinitdate" , orderinitdate)
                .putExtra("custname" , custname).putExtra("custmobno" , custmobno)
                .putExtra("custdelhaddrs" , custdelhaddrs).putExtra("paymenttype" , paymenttype)
                .putExtra("totalamount" , totalamount)
         );

    }


    public void opentrackorder(String orderitemname , String ordertype , String totalprice
            , ArrayList<String> trackdates , String orderno , String orderdeladdrs , Context context)
    {
        context.startActivity(new Intent(context, Trackorders.class).putExtra("itemname" , orderitemname).putExtra("itemtype" , ordertype)
        .putExtra("itemtprice" , totalprice).putExtra("itemtrackdates" , trackdates)
        .putExtra("itemorderno" , orderno).putExtra("itemorderdeladdrs" , orderdeladdrs));
    }


    public void orderstatus(Context context , ArrayList<String> trackdates , String orderidtxt , String isorderconfirm , String custphno , String custname , String duedate)
    {
        orderstatuspannel = new Dialog(context);
        orderstatuspannel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        orderstatuspannel.getWindow().setWindowAnimations(R.style.animateddialog);
        orderstatuspannel.setCanceledOnTouchOutside(false);
        orderstatuspannel.setCancelable(true);
        orderstatuspannel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        orderstatuspannel.setContentView(R.layout.orderstatuslayout);
        this.trackdates = trackdates;

        orderstatsspinner = orderstatuspannel.findViewById(R.id.orderstatsselector);
        ArrayAdapter<CharSequence> dresstypeparentadapter = new ArrayAdapter<CharSequence>(context, R.layout.spinnertxtlayout,context.getResources().getStringArray(R.array.orderstatslist));
        orderstatsspinner.setAdapter(dresstypeparentadapter);


        ImageView changepannelclosebtn = orderstatuspannel.findViewById(R.id.orderstatusdialogclose);
        changepannelclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderstatuspannel.dismiss();
            }
        });

        updatetrackstatsbtn = orderstatuspannel.findViewById(R.id.trackstatusupdatebtn);

        updatetrackstatsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (orderstatsspinner.getSelectedItemPosition())
                {
                    case  0:
                       setstatuspannel(orderidtxt ,  isorderconfirm , orderstatsspinner.getSelectedItemPosition() , (Activity) context, custphno , custname , duedate );
                        break;

                    case 1:
                        setstatuspannel(orderidtxt ,  isorderconfirm , orderstatsspinner.getSelectedItemPosition() , (Activity) context, custphno , custname , duedate );
                        break;

                    case 2:
                        setstatuspannel(orderidtxt , isorderconfirm , orderstatsspinner.getSelectedItemPosition() , (Activity) context, custphno , custname , duedate );
                        break;

                    case 3:
                        setstatuspannel(orderidtxt , isorderconfirm , orderstatsspinner.getSelectedItemPosition() , (Activity) context, custphno , custname , duedate );
                        break;
                }
            }
        });


                orderstatuspannel.show();
        Window window = orderstatuspannel.getWindow();
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    public String getcurrdate()
    {
        String date = "";


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        date = sdf.format(new Date());

        return date;
    }


    public   void  setstatuspannel(String orderidtxt , String isorderconfirm ,  int pos , Activity context ,  String cust_phno ,  String cust_name , String deldate )
    {
        String message = "" ;

           switch (pos)
           {
               case 0 :
                   if (isorderconfirm.equals("#T"))
                   {
                       AlertDialog.Builder messagedialog = new AlertDialog.Builder(context);
                       messagedialog.setTitle("Message");
                       AlertDialog alert;
                       messagedialog.setMessage("\n\nOrdered has been Already Confirmed");
                       messagedialog.setCancelable(false);
                       messagedialog.setNegativeButton("OK" ,null);
                       alert = messagedialog.create();
                       alert.show();
                   }

                   else
                   {
                       new linktosmsserver().sendsms(context , cust_phno , cust_name , pos , deldate);
                       confirmorder(orderidtxt);
                       orderstatuspannel.dismiss();
                       Toast.makeText(context , "Task Sucessfully done" ,Toast.LENGTH_SHORT).show();
                   }

                   break;

               case 1:
                   if (trackdates.get(1).equals(" "))
                   {

                       if (!isorderconfirm.equals("#T"))
                       {
                           AlertDialog.Builder messagedialog = new AlertDialog.Builder(context);
                           messagedialog.setTitle("Message");
                           AlertDialog alert;
                           messagedialog.setMessage("\n\nOrdered has not Confirmed yet");
                           messagedialog.setCancelable(false);
                           messagedialog.setNegativeButton("OK" ,null);
                           alert = messagedialog.create();
                           alert.show();
                       }

                       else {
                           message = trackdates.get(0) + ";" + getcurrdate() + ";" + trackdates.get(2) + ";" + trackdates.get(3) + ";";
                           updatestatuspannel(context, true , message, orderidtxt, cust_phno, cust_name, pos, deldate);
                       }
                   }

                   else
                   {
                      resettrackdatedialog(context ,"Shipped" ,orderidtxt , cust_phno , cust_name , pos , deldate );
                   }
                   break;

               case 2:
                   if (trackdates.get(2).equals(" "))
                   {
                       if (!trackdates.get(1).equals(" ")) {
                           message = trackdates.get(0) + ";" + trackdates.get(1) + ";" + getcurrdate() + ";" + trackdates.get(3) + ";";
                           updatestatuspannel(context, true , message, orderidtxt, cust_phno, cust_name, pos, deldate);
                       }

                       else {
                           AlertDialog.Builder messagedialog = new AlertDialog.Builder(context);
                           messagedialog.setTitle("Message");
                           AlertDialog alert;
                           messagedialog.setMessage("\n\nOrdered has not shipped yet");
                           messagedialog.setCancelable(false);
                           messagedialog.setNegativeButton("OK" ,null);
                           alert = messagedialog.create();
                           alert.show();
                       }
                   }

                   else
                   {
                       resettrackdatedialog(context ,"out for delivery" ,orderidtxt , cust_phno , cust_name , pos , deldate );
                   }
                   break;

               case 3:


                   AlertDialog.Builder messagedialog = new AlertDialog.Builder(context);
                   messagedialog.setTitle("Message");
                   AlertDialog alert;
                   messagedialog.setMessage("\n\nDo you want to Cancel This Order ?");
                   messagedialog.setCancelable(false);
                   messagedialog.setNegativeButton("No" ,null);
                   messagedialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           cancelorder(orderidtxt);
                           orderstatuspannel.dismiss();
                       }
                   });
                   alert = messagedialog.create();
                   alert.show();
                   break;

           }


    }


    private  void  confirmorder(String orderidtxt)
    {
        DatabaseReference trackupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/"+orderidtxt+"/isorderconfirm");
        trackupdatemsgref.setValue("#T");
    }

    private  void  cancelorder(String orderidtxt)
    {
        DatabaseReference trackupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/"+orderidtxt+"/iscancelled");
        trackupdatemsgref.setValue("#T");
    }


    private void resettrackdatedialog(Context context , String promptmsg  , String orderidtxt , String cust_phno , String cust_name , int pos , String deldate ) {

        AlertDialog.Builder processedmessage = new AlertDialog.Builder(context);
        processedmessage.setTitle("Message");
        AlertDialog alert;
        processedmessage.setMessage("\n\nOrdered has been Already set as * "+promptmsg+" *"+"\n\nNote - Click Reset to redo ");
        processedmessage.setCancelable(false);
        processedmessage.setNegativeButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String message = null;
                switch (pos)
                {
                    case 0:
                        message = trackdates.get(0)+"; ; ; ;";
                        break;

                    case 1:
                        message = trackdates.get(0)+"; ; ;Arriving on "+deldate+";";
                        break;

                    case 2:
                        message = trackdates.get(0)+";"+trackdates.get(1)+"; ;Arriving on "+deldate+";";
                        break;


                }
                updatestatuspannel((Activity) context, false ,  message , orderidtxt , cust_phno , cust_name , pos , deldate);
            }
        });

        processedmessage.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert = processedmessage.create();
        alert.show();
    }


    private void updatestatuspannel( Activity context , Boolean sndsms ,  String message , String  orderidtxt ,  String cust_phno ,  String cust_name , int pos , String deldate)
    {
        DatabaseReference trackupdatemsgref = FirebaseDatabase.getInstance().getReference("orderdetaillist/"+orderidtxt+"/trackdate");
        trackupdatemsgref.setValue(message);
        orderstatuspannel.dismiss();
        Toast.makeText(context , "Task Sucessfully done" ,Toast.LENGTH_SHORT).show();
        if (sndsms) {
            new linktosmsserver().sendsms(context, cust_phno, cust_name, pos, deldate);
        }
    }




}