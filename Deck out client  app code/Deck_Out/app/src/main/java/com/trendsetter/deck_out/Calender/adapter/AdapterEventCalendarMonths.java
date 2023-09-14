package com.trendsetter.deck_out.Calender.adapter;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trendsetter.deck_out.Calender.customviews.DateRangeCalendarView;
import com.trendsetter.deck_out.Calender.customviews.DateRangeMonthView;
import com.trendsetter.deck_out.Calender.manager.DateRangeCalendarManager;
import com.trendsetter.deck_out.Calender.models.CalendarStyleAttr;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterEventCalendarMonths extends PagerAdapter {

    private Context mContext;
    private List<Calendar> dataList = new ArrayList<>();
    private CalendarStyleAttr calendarStyleAttr;
    private DateRangeCalendarView.CalendarListener calendarListener;
    private DateRangeCalendarManager dateRangeCalendarManager;
    private Handler mHandler;

    public AdapterEventCalendarMonths(Context mContext, List<Calendar> list, CalendarStyleAttr calendarStyleAttr) {
        this.mContext = mContext;
        dataList = list;
        this.calendarStyleAttr = calendarStyleAttr;
        dateRangeCalendarManager = new DateRangeCalendarManager();
        mHandler = new Handler();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Calendar modelObject = dataList.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_pager_month, container, false);

        DateRangeMonthView dateRangeMonthView = layout.findViewById(R.id.cvEventCalendarView);
        dateRangeMonthView.drawCalendarForMonth(calendarStyleAttr, getCurrentMonth(modelObject), dateRangeCalendarManager);
        dateRangeMonthView.setCalendarListener(calendarAdapterListener);

        container.addView(layout);
        return layout;
    }

    /**
     * To clone calendar obj and get current month calendar starting from 1st date.
     *
     * @param calendar - Calendar
     * @return - Modified calendar obj of month of 1st date.
     */
    private Calendar getCurrentMonth(Calendar calendar) {
        Calendar current = (Calendar) calendar.clone();
        current.set(Calendar.DAY_OF_MONTH, 1);
        return current;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    private DateRangeCalendarView.CalendarListener calendarAdapterListener = new DateRangeCalendarView.CalendarListener() {
        @Override
        public void onFirstDateSelected(Calendar startDate) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 50);


            if (calendarListener != null) {
                calendarListener.onFirstDateSelected(startDate);
            }
        }

        @Override
        public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 50);
            if (calendarListener != null) {
                calendarListener.onDateRangeSelected(startDate, endDate);
            }
        }
    };

    public void setCalendarListener(DateRangeCalendarView.CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    /**
     * To redraw calendar.
     */
    public void invalidateCalendar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 50);
    }

    /**
     * To remove all selection and redraw current calendar
     */
    public void resetAllSelectedViews() {
        dateRangeCalendarManager.setMinSelectedDate(null);
        dateRangeCalendarManager.setMaxSelectedDate(null);
        notifyDataSetChanged();
    }

    public void setMinSelectedDate(Calendar minSelectedDate) {
        dateRangeCalendarManager.setMinSelectedDate(minSelectedDate);
    }

    public void setMaxSelectedDate(Calendar maxSelectedDate) {
        dateRangeCalendarManager.setMaxSelectedDate(maxSelectedDate);
    }

    public Calendar getMinSelectedDate() {
        return dateRangeCalendarManager.getMinSelectedDate();
    }

    public Calendar getMaxSelectedDate() {
        return dateRangeCalendarManager.getMaxSelectedDate();
    }
}
