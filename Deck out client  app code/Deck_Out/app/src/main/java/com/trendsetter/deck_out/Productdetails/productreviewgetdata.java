package com.trendsetter.deck_out.Productdetails;

public class productreviewgetdata {

    private String message ;
    private String rating ;
    private String username;
    private String profileimg;

    productreviewgetdata()
    {

    }

    productreviewgetdata(String message ,String rating , String username , String profileimg)
    {
      this.message = message;
      this.rating  = rating;
      this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getRating() {
        return rating;
    }

    public String getUsername() {
        return username;
    }

    public String getImageurl() {
        return profileimg;
    }
}
