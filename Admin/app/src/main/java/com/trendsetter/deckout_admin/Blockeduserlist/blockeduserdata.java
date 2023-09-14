package com.trendsetter.deckout_admin.Blockeduserlist;

public class blockeduserdata {


    private String Name, Profilepic, Emailid , userid , Mobno , checkblock;


    blockeduserdata() {

    }

    blockeduserdata(String Name, String Profilepic, String Emailid , String userid , String Mobno , String checkblock) {
        this.Name = Name;
        this.Profilepic = Profilepic;
        this.Emailid = Emailid;
        this.userid = userid;
        this.Mobno = Mobno;
        this.checkblock = checkblock;
    }

    public String getProfilepic() {
        return Profilepic;
    }


    public String getName() {
        return Name;
    }


    public String getEmailid() {
        return Emailid;
    }

    public String getUserid() {
        return userid;
    }

    public String getMobno() {
        return Mobno;
    }

    public String getCheckblock() {
        return checkblock;
    }
}
