package com.trendsetter.deckout_Delivery;

public class orderdetailslistgetdata {

    String productimgurl  , productname , producttagname , producttypeandsize;

    orderdetailslistgetdata()
    {

    }


    orderdetailslistgetdata(String productimgurl  , String productname , String producttagname , String producttypeandsize)
    {

       this.productimgurl = productimgurl;
       this.productname = productname;
       this.producttagname = producttagname;
       this.producttypeandsize = producttypeandsize;

    }


    public String getProductimgurl() {
        return productimgurl;
    }

    public String getProductname() {
        return productname;
    }


    public String getProducttagname() {
        return producttagname;
    }

    public String getProducttypeandsize() {
        return producttypeandsize;
    }
}
