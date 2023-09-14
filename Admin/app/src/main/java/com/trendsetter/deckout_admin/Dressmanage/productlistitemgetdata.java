package com.trendsetter.deckout_admin.Dressmanage;

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
    private String Product_type;
    private String Productparent_type;
    private String retailercode;
    private String retailername;
    private String Productsubparenttype;
    private String checknew;

    productlistitemgetdata()
    {

    }

    productlistitemgetdata(String name, String size_available, String description , String Product_type ,  String Productsubparenttype , String checknew ,  String Productparent_type , String dress_code , String dress_sizetitle,String product_likes , String price, String no_of_views, String product_id, String total_ratings , String retailercode ,  String retailername , String imgurl1, String imgurl2, String imgurl3, String imgurl4)
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
       this.Product_type = Product_type;
       this.dress_sizetitle = dress_sizetitle;
       this.Productparent_type = Productparent_type;
       this.retailercode = retailercode;
       this.retailername = retailername;
       this.Productsubparenttype = Productsubparenttype;
       this.checknew = checknew;

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

    public String getProductparent_type() {
        return Productparent_type;
    }

    public String getRetailercode() {
        return retailercode;
    }

    public String getProduct_type() {
        return Product_type;
    }

    public String getRetailername() {
        return retailername;
    }

    public String getChecknew() {
        return checknew;
    }

    public String getProductsubparenttype() {
        return Productsubparenttype;
    }

}
