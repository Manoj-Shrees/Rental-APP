package com.trendsetter.deckout_admin.Uploaddressdetails;

public class uploaddressuserlistdata {

    private String Name , Profilepic  , userid , Mobno;


    uploaddressuserlistdata()
    {

    }

    uploaddressuserlistdata(String Name , String Profilepic , String userid , String Mobno)
    {
        this.Name = Name;
        this.Profilepic = Profilepic;
        this.userid = userid;
        this.Mobno = Mobno;
    }

    public String getName() {
        return Name;
    }

    public String getProfilepic() {
        return Profilepic;
    }

    public String getUserid() {
        return userid;
    }

    public String getMobno() {
        return Mobno;
    }
}
