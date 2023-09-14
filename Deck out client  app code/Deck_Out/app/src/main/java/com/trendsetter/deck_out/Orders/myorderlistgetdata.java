package com.trendsetter.deck_out.Orders;

public class myorderlistgetdata {

    private  String cust_addrs ;
    private  String cust_name ;
    private  String cust_phoneno ;
    private  String delivered_status;
    private  String duedate;
    private  String duration;
    private  String id;
    private  String initdate;
    private  String name;
    private  String ordertype;
    private  String paymenttype;
    private  String paymnt_stat;
    private  String sizenitems;
    private  String tprice;
    private  String trackcode;
    private  String trackdate;
    private  String isorderconfirm;
    private  String iscancelled;


    myorderlistgetdata()
    {

    }

    myorderlistgetdata(String cust_addrs , String cust_name , String cust_phoneno , String delivered_status
            , String duedate , String duration , String id , String initdate , String name , String ordertype
           , String paymenttype , String paymnt_stat , String sizenitems  , String tprice , String trackcode ,
                       String trackdate , String isorderconfirm , String iscancelled)
    {
       this.cust_addrs = cust_addrs;
       this.cust_name = cust_name;
       this.cust_phoneno = cust_phoneno;
       this.delivered_status = delivered_status;
       this.duedate = duedate;
       this.duration = duration;
       this.id = id ;
       this.initdate = initdate;
       this.name = name;
       this.ordertype = ordertype;
       this.paymenttype = paymenttype;
       this.paymnt_stat = paymnt_stat;
       this.sizenitems = sizenitems;
       this.tprice = tprice;
       this.trackcode = trackcode;
       this.trackdate = trackdate;
       this.isorderconfirm = isorderconfirm;
       this.iscancelled = iscancelled;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCust_addrs() {
        return cust_addrs;
    }

    public String getCust_name() {
        return cust_name;
    }

    public String getCust_phoneno() {
        return cust_phoneno;
    }

    public String getDelivered_status() {
        return delivered_status;
    }

    public String getDuedate() {
        return duedate;
    }

    public String getDuration() {
        return duration;
    }

    public String getInitdate() {
        return initdate;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public String getPaymnt_stat() {
        return paymnt_stat;
    }

    public String getSizenitems() {
        return sizenitems;
    }

    public String getTprice() {
        return tprice;
    }

    public String getTrackcode() {
        return trackcode;
    }

    public String getTrackdate() {
        return trackdate;
    }

    public String getIsorderconfirm() {
        return isorderconfirm;
    }

    public String getIscancelled() {
        return iscancelled;
    }
}
