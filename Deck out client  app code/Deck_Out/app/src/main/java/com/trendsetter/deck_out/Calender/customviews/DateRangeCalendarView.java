package com.trendsetter.deck_out.Calender.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.trendsetter.deck_out.Calender.adapter.AdapterEventCalendarMonths;
import com.trendsetter.deck_out.Calender.models.CalendarStyleAttr;
import com.trendsetter.deck_out.R;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DateRangeCalendarView extends LinearLayout {

    public interface CalendarListener {
        void onFirstDateSelected(Calendar startDate);

        void onDateRangeSelected(Calendar startDate, Calendar endDate);
    }

    private CustomTextView tvYearTitle;
    private AppCompatImageView imgVNavLeft, imgVNavRight;
    private List<Calendar> dataList = new ArrayList<>();

    private AdapterEventCalendarMonths adapterEventCalendarMonths;
    private Locale locale;

    private ViewPager vpCalendar;
    private CalendarStyleAttr calendarStyleAttr;


    private final static int TOTAL_ALLOWED_MONTHS = 4;

    public DateRangeCalendarView(Context context) {
        super(context);
        initViews(context, null);
    }

    public DateRangeCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public DateRangeCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {

        locale = context.getResources().getConfiguration().locale;
        calendarStyleAttr = new CalendarStyleAttr(context, attrs);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_calendar_container, this, true);

        RelativeLayout rlHeaderCalendar = findViewById(R.id.rlHeaderCalendar);
        rlHeaderCalendar.setBackground(calendarStyleAttr.getHeaderBg());

        tvYearTitle = findViewById(R.id.tvYearTitle);
        imgVNavLeft = findViewById(R.id.imgVNavLeft);
        imgVNavRight = findViewById(R.id.imgVNavRight);

        vpCalendar = findViewById(R.id.vpCalendar);


        dataList.clear();
        Calendar today = (Calendar) Calendar.getInstance().clone();
        today.add(Calendar.MONTH, -TOTAL_ALLOWED_MONTHS);

        for (int i = 0; i < TOTAL_ALLOWED_MONTHS * 2; i++) {
            dataList.add((Calendar) today.clone());
            today.add(Calendar.MONTH, 1);
        }

        adapterEventCalendarMonths = new AdapterEventCalendarMonths(context, dataList, calendarStyleAttr);
        vpCalendar.setAdapter(adapterEventCalendarMonths);
        vpCalendar.setOffscreenPageLimit(0);
        vpCalendar.setCurrentItem(TOTAL_ALLOWED_MONTHS);
        setCalendarYearTitle(TOTAL_ALLOWED_MONTHS);

        setListeners();
    }

    private void setListeners() {

        vpCalendar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCalendarYearTitle(position);
                setNavigationHeader(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imgVNavLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int newPosition = vpCalendar.getCurrentItem() - 1;
                if (newPosition > -1) {
                    vpCalendar.setCurrentItem(newPosition);
                }
            }
        });
        imgVNavRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int newPosition = vpCalendar.getCurrentItem() + 1;
                if (newPosition < dataList.size()) {
                    vpCalendar.setCurrentItem(newPosition);
                }
            }
        });
    }


    /**
     * To set navigation header ( Left-Right button )
     *
     * @param position New page position
     */
    private void setNavigationHeader(int position) {
        imgVNavRight.setVisibility(VISIBLE);
        imgVNavLeft.setVisibility(VISIBLE);
        if (position == 0) {
            imgVNavLeft.setVisibility(INVISIBLE);
        } else if (position == dataList.size() - 1) {
            imgVNavRight.setVisibility(INVISIBLE);
        }
    }

    /**
     * To set calendar year title
     *
     * @param position data list position for getting date
     */
    private void setCalendarYearTitle(int position) {

        Calendar currentCalendarMonth = dataList.get(position);
        String dateText = new DateFormatSymbols(locale).getMonths()[currentCalendarMonth.get(Calendar.MONTH)];
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());

        String yearTitle = dateText + " " + currentCalendarMonth.get(Calendar.YEAR);

        tvYearTitle.setText(yearTitle);
        tvYearTitle.setTextColor(calendarStyleAttr.getTitleColor());

    }

    /**
     * To set calendar listener
     *
     * @param calendarListener Listener
     */
    public void setCalendarListener(final CalendarListener calendarListener) {
        adapterEventCalendarMonths.setCalendarListener(calendarListener);
    }

    /**
     * To apply custom fonts to all the text views
     *
     * @param fonts - Typeface that you want to apply
     */
    public void setFonts(Typeface fonts) {
        tvYearTitle.setTypeface(fonts);
        calendarStyleAttr.setFonts(fonts);
        adapterEventCalendarMonths.invalidateCalendar();
    }

    /**
     * To remove all selection and redraw current calendar
     */
    public void resetAllSelectedViews() {
        adapterEventCalendarMonths.resetAllSelectedViews();
    }

    /**
     * To set week offset. To start week from any of the day. Default is 0 (Sunday).
     *
     * @param offset 0-Sun, 1-Mon, 2-Tue, 3-Wed, 4-Thu, 5-Fri, 6-Sat
     */
    public void setWeekOffset(int offset) {
        calendarStyleAttr.setWeekOffset(offset);
        adapterEventCalendarMonths.invalidateCalendar();
    }

    /**
     * To set left navigation ImageView drawable
     */
    public void setNavLeftImage(@NonNull Drawable leftDrawable) {
        imgVNavLeft.setImageDrawable(leftDrawable);
    }

    /**
     * To set right navigation ImageView drawable
     */
    public void setNavRightImage(@NonNull Drawable rightDrawable) {
        imgVNavRight.setImageDrawable(rightDrawable);
    }

    /**
     * Sets start date.
     *
     * @param startDate
     */
    public void setStartDate(Calendar startDate) {
        adapterEventCalendarMonths.setMinSelectedDate(startDate);
    }

    /**
     * Sets end date.
     *
     * @param endDate
     */
    public void setEndDate(Calendar endDate) {
        adapterEventCalendarMonths.setMaxSelectedDate(endDate);
    }


    /**
     * Gets start date.
     */
    public Calendar getStartDate() {
        return adapterEventCalendarMonths.getMinSelectedDate();
    }


    /**
     * Gets end date.
     */
    public Calendar getEndDate() {
        return adapterEventCalendarMonths.getMaxSelectedDate();
    }
}
