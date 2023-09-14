package com.trendsetter.deckout_admin.Dressmanage;

public class dressgallerygetdata {
    private String name;
    private String imgurl;
    private String id;

    dressgallerygetdata()
    {

    }

    dressgallerygetdata(String name, String imgurl , String id)
    {

        this.name = name;
        this.imgurl = imgurl;
        this.id = id ;
    }


    public String getName() {
        return name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getId() {
        return id;
    }
}
