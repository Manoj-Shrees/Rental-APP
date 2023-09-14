package com.trendsetter.deck_out.Extra;

public class orderidgenrator {

    String orderidtxt = "";


    public String getorderid(String currentorderid)
    {

        orderidtxt = ""+(Integer.parseInt(currentorderid)+1);

        return  orderidtxt;
    }



}
