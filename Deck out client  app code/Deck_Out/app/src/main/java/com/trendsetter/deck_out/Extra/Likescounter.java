package com.trendsetter.deck_out.Extra;

public class Likescounter {

    public String setcounts(Long number)
    {
        return coolFormat(number, 0);
    }


    private static char[] c = new char[]{'k', 'm', 'b', 't'};

    private static String coolFormat(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;
        return (d < 1000? ((d > 99.9 || isRound || (!isRound && d > 9.99)? (int) d * 10 / 10 : d + "") + "" + c[iteration]) : coolFormat(d, iteration+1));

    }


}
