package com.trendsetter.deck_out.Orders;


import android.os.Parcel;
import android.os.Parcelable;

public class orderlistDataWrapper implements Parcelable {

    private String hex;
    private String name;
    private String rgb;

    private orderlistDataWrapper(Parcel in) {
        hex = in.readString();
        name = in.readString();
        rgb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hex);
        dest.writeString(name);
        dest.writeString(rgb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @return
     * The hex
     */
    public String getHex() {
        return hex;
    }

    /**
     *
     * @param hex
     * The hex
     */
    public void setHex(String hex) {
        this.hex = hex;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The rgb
     */
    public String getRgb() {
        return rgb;
    }

    /**
     *
     * @param rgb
     * The rgb
     */
    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public static final Creator<orderlistDataWrapper> CREATOR = new Creator<orderlistDataWrapper>() {
        @Override
        public orderlistDataWrapper createFromParcel(Parcel in) {
            return new orderlistDataWrapper(in);
        }

        @Override
        public orderlistDataWrapper[] newArray(int size) {
            return new orderlistDataWrapper[size];
        }
    };
}