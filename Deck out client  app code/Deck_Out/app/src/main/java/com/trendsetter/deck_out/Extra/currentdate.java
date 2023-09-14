package com.trendsetter.deck_out.Extra;

import java.text.SimpleDateFormat;
import java.util.Date;

public class currentdate {

    public String getcurrdate()
    {
        String date = "";


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        date = sdf.format(new Date());

        return date;
    }
}
