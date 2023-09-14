package com.trendsetter.deckout_admin.hflcz;

public class hflgetdata {
    private String Answer;
    private String Imgurl;
    private String id;

    hflgetdata()
    {

    }

    hflgetdata(String Answer, String Imgurl , String id)
    {

        this.Answer = Answer;
        this.Imgurl = Imgurl;
        this.id = id ;
    }


    public String getId() {
        return id;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public String getAnswer() {
        return Answer;
    }


}
