package com.trendsetter.deck_out.Orders;

public class myorderproductlistgetdata {

    private String productimgurl;
    private String productname;
    private String producttagname;
    private String producttypeandsize;
    private String productprice;
    private String productsizedata;

    myorderproductlistgetdata()
    {

    }


    myorderproductlistgetdata(String productimgurl , String productname , String producttagname , String producttypeandsize , String productprice , String productsizedata)
    {
        this.productimgurl = productimgurl;
        this.productname = productname;
        this.producttagname = producttagname;
        this.producttypeandsize = producttypeandsize;
        this.productprice = productprice;
        this.productsizedata = productsizedata;
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

    public String getProductprice() {
        return productprice;
    }

    public String getProductsizedata() {
        return productsizedata;
    }
}
