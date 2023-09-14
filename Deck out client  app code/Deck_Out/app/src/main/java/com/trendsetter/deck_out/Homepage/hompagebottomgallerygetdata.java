package com.trendsetter.deck_out.Homepage;

public class hompagebottomgallerygetdata  {

    private String name;
    private String imgurl;

    hompagebottomgallerygetdata ()
    {

    }


    hompagebottomgallerygetdata ( String name , String imgurl)
    {
        this.name = name;
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public String getImgurl() {
        return imgurl;
    }
}
