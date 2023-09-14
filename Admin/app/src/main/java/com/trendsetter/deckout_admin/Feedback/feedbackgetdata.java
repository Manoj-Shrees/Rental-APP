package com.trendsetter.deckout_admin.Feedback;

public class feedbackgetdata {


    private  String feedbacktxt;
    private  String id;
    private  String name;
    private  String propic ;
    private  String type ;

    feedbackgetdata()
    {

    }


    feedbackgetdata(String feedbacktxt , String id  , String name , String propic , String type)
    {
        this.feedbacktxt = feedbacktxt;
        this.id = id;
        this.name = name;
        this.propic = propic;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getFeedbacktxt() {
        return feedbacktxt;
    }

    public String getName() {
        return name;
    }

    public String getPropic() {
        return propic;
    }

}
