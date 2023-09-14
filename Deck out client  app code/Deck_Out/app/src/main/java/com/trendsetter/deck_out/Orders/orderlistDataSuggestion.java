package com.trendsetter.deck_out.Orders;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;


public class orderlistDataSuggestion implements SearchSuggestion {

    private String mColorName;
    private boolean mIsHistory = false;

    public orderlistDataSuggestion(String suggestion) {
        this.mColorName = suggestion.trim().replace("~", "@").replace("`", ".");
    }

    public orderlistDataSuggestion(Parcel source) {
        this.mColorName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mColorName;
    }

    public static final Creator<orderlistDataSuggestion> CREATOR = new Creator<orderlistDataSuggestion>() {
        @Override
        public orderlistDataSuggestion createFromParcel(Parcel in) {
            return new orderlistDataSuggestion(in);
        }

        @Override
        public orderlistDataSuggestion[] newArray(int size) {
            return new orderlistDataSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mColorName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}