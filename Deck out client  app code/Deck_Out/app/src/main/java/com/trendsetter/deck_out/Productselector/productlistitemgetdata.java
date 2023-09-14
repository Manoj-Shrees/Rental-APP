package com.trendsetter.deck_out.Productselector;

public class productlistitemgetdata {

    private String name;
    private String size_available;
    private String description;
    private String price;
    private String no_of_views;
    private String product_id;
    private String product_likes;
    private String total_ratings;
    private String imgurl1;
    private String imgurl2;
    private String imgurl3;
    private String imgurl4;
    private String dress_code;
    private String dress_sizetitle;
    private String Productparent_type;
    private String retailercode;
    private String retailername;



    productlistitemgetdata()
    {

    }

    productlistitemgetdata(String name, String size_available, String description ,String Productparent_type , String retailercode ,  String retailername ,  String dress_code , String dress_sizetitle,String product_likes , String price, String no_of_views, String product_id, String total_ratings , String imgurl1, String imgurl2, String imgurl3, String imgurl4)
    {
       this.name = name;
       this.description = description;
       this.size_available = size_available;
       this.price = price;
       this.no_of_views = no_of_views;
       this.product_id = product_id;
       this.total_ratings = total_ratings;
       this.product_likes = product_likes;
       this.imgurl1 = imgurl1;
       this.imgurl2 = imgurl2;
       this.imgurl3 = imgurl3;
       this.imgurl4 = imgurl4;
       this.dress_code = dress_code;
       this.dress_sizetitle = dress_sizetitle;
       this.Productparent_type = Productparent_type;
       this.retailercode = retailercode;
       this.retailername = retailername;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDress_code() {
        return dress_code;
    }

    public String getDress_sizetitle() {
        return dress_sizetitle;
    }

    public String getNo_of_views() {
        return no_of_views;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_likes() {
        return product_likes;
    }

    public String getSize_available() {
        return size_available;
    }

    public String getTotal_ratings() {
        return total_ratings;
    }

    public String getImgurl1() {
        return imgurl1;
    }

    public String getImgurl2() {
        return imgurl2;
    }

    public String getImgurl3() {
        return imgurl3;
    }

    public String getImgurl4() {
        return imgurl4;
    }

    public String getRetailername() {
        return retailername;
    }

    public String getRetailercode() {
        return retailercode;
    }

    public String getProductparent_type() {
        return Productparent_type;
    }
}
