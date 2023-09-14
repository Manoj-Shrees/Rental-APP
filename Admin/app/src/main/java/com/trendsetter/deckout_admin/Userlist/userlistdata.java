package com.trendsetter.deckout_admin.Userlist;

public class userlistdata {

    private String Name , Profilepic , Emailid  , userid , Mobno , checkblock;


    userlistdata()
    {

    }

    userlistdata(String Name , String Profilepic , String Emailid , String userid , String Mobno , String checkblock)
    {
        this.Name = Name;
        this.Profilepic = Profilepic;
        this.Emailid = Emailid;
        this.userid = userid;
        this.Mobno = Mobno;
        this.checkblock = checkblock;
    }

    public String getEmailid() {
        return Emailid;
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

    public String getCheckblock() {
        return checkblock;
    }
}
