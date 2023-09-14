package com.trendsetter.deck_out.Profile;

public class profileaddressgetdata {

    private String addresstxt;
    private String deliveryname;

    profileaddressgetdata()
    {

    }


    profileaddressgetdata(String addresstxt , String deliveryname)
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
