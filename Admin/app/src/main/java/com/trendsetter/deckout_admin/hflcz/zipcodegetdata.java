package com.trendsetter.deckout_admin.hflcz;

public class zipcodegetdata  {

    private String landmark;
    private String region;
    private String zipcode;

    zipcodegetdata()
    {

    }


    zipcodegetdata(String landmark , String region , String zipcode)
    {
       this.landmark = landmark;
       this.region = region;
       this.zipcode = zipcode;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getRegion() {
        return region;
    }

    public String getZipcode() {
        return zipcode;
    }
}