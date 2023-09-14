package com.trendsetter.deck_out.Payment;

public class paymentaddressgetdata  {

   private  String addresstxt , deliveryname;

    paymentaddressgetdata()
    {

    }


    paymentaddressgetdata(String addresstxt , String deliveryname)
    {
        this.addresstxt = addresstxt;
        this.deliveryname = deliveryname;
    }


    public String getAddresstxt() {
        return addresstxt;
    }

    public String getDeliveryname() {
        return deliveryname;
    }
}
