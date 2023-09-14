package com.trendsetter.deck_out.Payment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SortedList;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.trendsetter.deck_out.Extra.ServiceWrapper;
import com.trendsetter.deck_out.Extra.currentdate;
import com.trendsetter.deck_out.Extra.orderidgenrator;
import com.trendsetter.deck_out.Homepage.homepage;
import com.trendsetter.deck_out.R;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;


public class Redirecttopayment extends AppCompatActivity {


    private CircleProgressBar paymentprogressbar;
    private TextView messagetxt;
    private SharedPreferences sp;
    String [] pcodes;
    int sublistdatacount = 0 , pnamecount = 0 , ordertypecount = 0 , retaileidcount = 0 , sizenitemscount = 0 , pcodescount=0;

    String userref;
    SharedPreferences sharedpreferencessliderads , sharedpreferencesmostsearch;
    SharedPreferences.Editor slideradseditor , mostsearcheditor;
    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    PayUmoneySdkInitializer.PaymentParam paymentParam = null;

    String hashSequence = "Redirecttopayment", txnid , phone , prodname = "Deck Out", username, email , userid
            , useraddrs , durationtxt , paymenttypetxt , orderidtxt  , odernametxt ,retaileidtxt = "", tpricetxt , retailercode ,
            merchantId , merchantkey , selecteditemcodes, paymnt_stattxt , qauntstxt , ordertypetxt= "" , sizenitemstxt = "Size : ";

    Map<String , HashMap<String , String>> sublistdatalist = new HashMap<>();
    HashMap<String , String>  sublistsubdatalist ;

    orderconfirmsetdata orderconfirdataref =  new orderconfirmsetdata();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentactivitylayout);
        tpricetxt = getIntent().getExtras().getString("redirecttopayamount");
        durationtxt = getIntent().getExtras().getString("redirecttopayduration");
        phone = getIntent().getExtras().getString("redirecttopayphone");
        username = getIntent().getExtras().getString("redirecttopayusername");
        userid = getIntent().getExtras().getString("redirecttopayuserid");
        useraddrs = getIntent().getExtras().getString("redirecttopayuseraddrs");
        email = getIntent().getExtras().getString("redirecttopayuseremail");
        paymenttypetxt = getIntent().getExtras().getString("redirecttopaypaymenttypetxt");
        selecteditemcodes = getIntent().getExtras().getString("redirecttopayitemcodes");
        qauntstxt = getIntent().getExtras().getString("redirecttopayqaunts");
        paymnt_stattxt = getIntent().getExtras().getString("redirecttopaypaymnt_stattxt");
        merchantId = getResources().getString(R.string.merchantid);
        merchantkey = getResources().getString(R.string.merchantkey);


        getorderid();
        createsublistdata();

        try {
            txnid = createTransactionID();
        } catch (Exception e) {
            e.printStackTrace();
        }

        messagetxt = findViewById(R.id.paymentactivitymessage);
        paymentprogressbar = findViewById(R.id.paymentprogressBar);
        paymentprogressbar.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_blue_dark, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        Handler handler = new Handler();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    if (finalI == 4) {
                        messagetxt.setText("Please Wait");
                    }

                    if (finalI == 8) {
                        messagetxt.setText("Redirecting to proceed payment");
                    }


                    if (finalI * 10 >= 90) {
                        if (paymenttypetxt.trim().equals("Postpaid (COD)"))
                        {
                            setorderdetails();
                        }

                        else
                        {
                            startpay();
                        }

                    } else {
                        paymentprogressbar.setProgress(finalI * 10);
                    }
                }
            }, 1000 * (i + 1));

        }

    }

    public void startpay() {
        builder.setAmount(tpricetxt)                          // Payment amount
                .setTxnId(txnid)                     // Transaction ID
                .setPhone(phone.replaceAll("\\s+", ""))                   // User Phone number
                .setProductName(prodname)                   // Product Name or description
                .setFirstName(username)                              // User First name
                .setEmail(email)              // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")     // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(merchantkey)                        // Merchant key
                .setMerchantId(merchantId);

        try {
            paymentParam = builder.build();
            getHashkey();

        } catch (Exception e) {
            /*  Log.e(TAG, " error s "+e.toString());*/
        }

    }

    public void getHashkey() {
        ServiceWrapper service = new ServiceWrapper(null);
        Call<String> call = service.newHashCall(merchantkey, txnid, tpricetxt, prodname,
                username, email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String merchantHash = response.body();
                if (merchantHash.isEmpty() || merchantHash.equals("")) {
                    Toast.makeText(Redirecttopayment.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                    /*      Log.e(TAG, "hash empty");*/
                } else {
                    // mPaymentParams.setMerchantHash(merchantHash);
                    paymentParam.setMerchantHash(merchantHash);
                    // Invoke the following function to open the checkout page.
                    // PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this,-1, true);
                    PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, Redirecttopayment.this, R.style.PayumoneythemeGreen, false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*    Log.e(TAG, "hash error "+ t.toString());*/
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// PayUMoneySdk: Success -- payuResponse{"id":225642,"mode":"CC","status":"success","unmappedstatus":"captured","key":"9yrcMzso","txnid":"223013","transaction_fee":"20.00","amount":"20.00","cardCategory":"domestic","discount":"0.00","addedon":"2018-12-31 09:09:43","productinfo":"a2z shop","firstname":"kamal","email":"kamal.bunkar07@gmail.com","phone":"9144040888","hash":"b22172fcc0ab6dbc0a52925ebbd0297cca6793328a8dd1e61ef510b9545d9c851600fdbdc985960f803412c49e4faa56968b3e70c67fe62eaed7cecacdfdb5b3","field1":"562178","field2":"823386","field3":"2061","field4":"MC","field5":"167227964249","field6":"00","field7":"0","field8":"3DS","field9":" Verification of Secure Hash Failed: E700 -- Approved -- Transaction Successful -- Unable to be determined--E000","payment_source":"payu","PG_TYPE":"AXISPG","bank_ref_no":"562178","ibibo_code":"VISA","error_code":"E000","Error_Message":"No Error","name_on_card":"payu","card_no":"401200XXXXXX1112","is_seamless":1,"surl":"https://www.payumoney.com/sandbox/payment/postBackParam.do","furl":"https://www.payumoney.com/sandbox/payment/postBackParam.do"}
//PayUMoneySdk: Success -- merchantResponse438104
// on successfull txn
        //  request code 10000 resultcode -1
        //tran {"status":0,"message":"payment status for :438104","result":{"postBackParamId":292490,"mihpayid":"225642","paymentId":438104,"mode":"CC","status":"success","unmappedstatus":"captured","key":"9yrcMzso","txnid":"txt12345","amount":"20.00","additionalCharges":"","addedon":"2018-12-31 09:09:43","createdOn":1546227592000,"productinfo":"a2z shop","firstname":"kamal","lastname":"","address1":"","address2":"","city":"","state":"","country":"","zipcode":"","email":"kamal.bunkar07@gmail.com","phone":"9144040888","udf1":"","udf2":"","udf3":"","udf4":"","udf5":"","udf6":"","udf7":"","udf8":"","udf9":"","udf10":"","hash":"0e285d3a1166a1c51b72670ecfc8569645b133611988ad0b9c03df4bf73e6adcca799a3844cd279e934fed7325abc6c7b45b9c57bb15047eb9607fff41b5960e","field1":"562178","field2":"823386","field3":"2061","field4":"MC","field5":"167227964249","field6":"00","field7":"0","field8":"3DS","field9":" Verification of Secure Hash Failed: E700 -- Approved -- Transaction Successful -- Unable to be determined--E000","bank_ref_num":"562178","bankcode":"VISA","error":"E000","error_Message":"No Error","cardToken":"","offer_key":"","offer_type":"","offer_availed":"","pg_ref_no":"","offer_failure_reason":"","name_on_card":"payu","cardnum":"401200XXXXXX1112","cardhash":"This field is no longer supported in postback params.","card_type":"","card_merchant_param":null,"version":"","postUrl":"https:\/\/www.payumoney.com\/mobileapp\/payumoney\/success.php","calledStatus":false,"additional_param":"","amount_split":"{\"PAYU\":\"20.0\"}","discount":"0.00","net_amount_debit":"20","fetchAPI":null,"paisa_mecode":"","meCode":"{\"vpc_Merchant\":\"TESTIBIBOWEB\"}","payuMoneyId":"438104","encryptedPaymentId":null,"id":null,"surl":null,"furl":null,"baseUrl":null,"retryCount":0,"merchantid":null,"payment_source":null,"pg_TYPE":"AXISPG"},"errorCode":null,"responseCode":null}---438104

        // Result Code is -1 send from Payumoney activity
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    setorderdetails();
                }


                else
                {
                   finish();
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            }

        }

        else
        {
            finish();
        }
    }



    public String createTransactionID() throws Exception{
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX).toUpperCase();
    }


    private void createsublistdata()
    {

        pcodes = selecteditemcodes.split(";");
        setSublistdata(pcodes[pcodescount]);

    }

    private void setSublistdata(String pcode)
    {

        sublistsubdatalist = new HashMap<>();
        DatabaseReference productimgurlref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/imgurl1");
        productimgurlref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsublistdatainmap(pcode , "productimgurl" , dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference retailercoderef = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/retailercode");
        retailercoderef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retailercode = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        DatabaseReference productnameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/name");
        productnameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsublistdatainmap(pcode , "productname" , dataSnapshot.getValue().toString() );
                if (pnamecount == 0)
                {
                    odernametxt = dataSnapshot.getValue().toString();
                    pnamecount+=1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference productpriceref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/price");
        productpriceref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsublistdatainmap(pcode , "productprice" , dataSnapshot.getValue().toString() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference productsizedataref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/sizedata");
        productsizedataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsublistdatainmap(pcode , "productsizedata" , dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference producttagnameref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/tagname");
        producttagnameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsublistdatainmap(pcode , "producttagname" , dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference productretaileridref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/retailercode");
        productretaileridref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsublistdatainmap(pcode , "productretailercode" , dataSnapshot.getValue().toString());
                setretailertxt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference producttypeandsizeref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/sizecattype");
        producttypeandsizeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sizedatas = dataSnapshot.getValue().toString();
                DatabaseReference producttyperef = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/mycart/"+pcode+"/Productparent_type");
                producttyperef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        setOrdersizetxt(sizedatas);
                        setsublistdatainmap(pcode , "producttypeandsize" , "Size : "+ sizedatas+"  |  Type : "+dataSnapshot.getValue().toString().replace("#" , ""));
                        setOrdertypetxt(dataSnapshot.getValue().toString().replace("#" , ""));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private void setOrdertypetxt(String dresstype){
        if (!ordertypetxt.contains(dresstype))
        {

            if (ordertypecount == 0)
                ordertypetxt += dresstype;
            else
                ordertypetxt += " , "+dresstype;

            ordertypecount+=1;

        }
    }

    private void setretailertxt(String retailercode){
        if (!retaileidtxt.contains(retailercode))
        {
            if (retaileidcount == 0)
                retaileidtxt += retailercode;
            else
                retaileidtxt += " , "+retailercode;

            retaileidcount+=1;

        }

    }

    private void setOrdersizetxt(String dresssize){

        if (!sizenitemstxt.contains(dresssize))
        {
            if (sizenitemscount == 0)
                sizenitemstxt += dresssize;
            else
                sizenitemstxt += " , "+dresssize;

            sizenitemscount+=1;
        }

    }


    private void setsublistdatainmap(String pcode  , String key , String data)
    {
        sublistdatacount+=1;
        sublistsubdatalist.put(key , data);

        if (sublistdatacount==7)
        {
            sublistsubdatalist.put("productid" , pcode);
            sublistdatalist.put(pcode , sublistsubdatalist);
            sublistdatacount = 0;
            pcodescount+=1;
            if (pcodes.length > pcodescount)
            createsublistdata();

        }

    }



    private void  setorderdetails()
    {
            String datestxt [] = durationtxt.split("to");
            String duedatetxt = datestxt[1].trim();
            String neworderidtxt = new orderidgenrator().getorderid(orderidtxt);
            String initdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date(new currentdate().getcurrdate()));
            String trackdatetxt = initdate+"; ; ;Arriving on "+datestxt[1].trim()+";";
            sizenitemstxt+="  |  Items : "+qauntstxt;

        orderconfirdataref.setorderdate("Client" , useraddrs , userid , username , phone ,"N/A" ,
                initdate , duedatetxt , durationtxt , odernametxt  , paymenttypetxt , neworderidtxt , ordertypetxt , paymnt_stattxt ,
                retaileidtxt , sizenitemstxt , tpricetxt , trackdatetxt ,   sublistdatalist);

        afterdataupload();
    }

    private void getorderid()
    {
        DatabaseReference orderidef  =  FirebaseDatabase.getInstance().getReference("deckoutadmin/ordecount");
        orderidef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setotderid(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setotderid(String orderid)
    {
        this.orderidtxt = orderid;

    }



    private void afterdataupload()
    {
        orderconfirdataref.deletefromcart(selecteditemcodes,userid);

        AlertDialog.Builder  paymentfinisheddialog = new AlertDialog.Builder(this);
        paymentfinisheddialog.setTitle("Message");
        paymentfinisheddialog.setMessage("\n\nOrder Has been Initiate Sucessfully We will keep you update via SMS.");
        paymentfinisheddialog.setPositiveButton("Exit to Homepage", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), homepage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        paymentfinisheddialog.show();


    }


}



