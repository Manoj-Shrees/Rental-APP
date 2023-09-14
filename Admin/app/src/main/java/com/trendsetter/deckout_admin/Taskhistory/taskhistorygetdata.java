package com.trendsetter.deckout_admin.Taskhistory;

public class taskhistorygetdata {

   private String category ;
   private String type ;
   private String date ;
   private String imagecode;
   private String id;

    taskhistorygetdata()
    {

    }


    taskhistorygetdata(String category , String type , String date , String imagecode , String id)
    {
       this.category = category;
       this.type = type;
       this.date = date;
       this.imagecode = imagecode;
       this.id = id;
    }


    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getImagecode() {
        return imagecode;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
