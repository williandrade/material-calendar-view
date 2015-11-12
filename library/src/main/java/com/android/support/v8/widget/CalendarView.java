/*
 * Copyright (C) 2015 Jonatan E. Salas { link: http://the-android-developer.blogspot.com.ar }
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.support.v8.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.android.support.v8.R;
import com.android.support.v8.model.DayTime;
import com.android.support.v8.util.CalendarUtil;

/**
 * This class is a calendar widget for displaying dates, selecting, adding and associating event for a
 * specific day.
 *
 * @attr ref R.styleable#CalendarView_scrollEnable
 * @attr ref R.styleable#CalendarView_headerViewEnable
 * @attr ref R.styleable#CalendarView_weekendEnable
 * @attr ref R.styleable#CalendarView_holidayEnable
 * @attr ref R.styleable#CalendarView_calendarViewBackgroundColor
 * @attr ref R.styleable#CalendarView_headerViewBackgroundColor
 * @attr ref R.styleable#CalendarView_headerViewTextColor
 * @attr ref R.styleable#CalendarView_headerViewFontSize
 * @attr ref R.styleable#CalendarView_weekViewBackgroundColor
 * @attr ref R.styleable#CalendarView_weekViewTextColor
 * @attr ref R.styleable#CalendarView_weekViewFontSize
 * @attr ref R.styleable#CalendarView_adapterViewBackgroundColor
 * @attr ref R.styleable#CalendarView_adapterViewTextColor
 * @attr ref R.styleable#CalendarView_adapterViewFontSize
 * @attr ref R.styleable#CalendarView_disabledBackgroundColor
 * @attr ref R.styleable#CalendarView_disabledTextColor
 * @attr ref R.styleable#CalendarView_selectedBackgroundColor
 * @attr ref R.styleable#CalendarView_selectedTextColor
 * @attr ref R.styleable#CalendarView_weekendBackgroundColor
 * @attr ref R.styleable#CalendarView_weekendTextColor
 * @attr ref R.styleable#CalendarView_holidayBackgroundColor
 * @attr ref R.styleable#CalendarView_holidayTextColor
 * @attr ref R.styleable#CalendarView_currentBackgroundColor
 * @attr ref R.styleable#CalendarView_currentTextColor
 * @attr ref R.styleable#CalendarView_drawableColor
 * @attr ref R.styleable#CalendarView_leftArrowDrawable
 * @attr ref R.styleable#CalendarView_rightArrowDrawable
 *
 * @author jonatan.salas
 */
public class CalendarView extends FrameLayout {
    private static final int SPAN_COUNT = 7;
    private static final int SIZE = 42;

    protected LayoutInflater mInflater;
    protected Context mContext;

    //View containers..
    protected View mRootView;
    protected View mHeaderView;
    protected View mWeekView;
    protected RecyclerView mAdapterView;

    protected Calendar mCurrentCalendar;

    @Nullable
    private OnMonthChangeListener mOnMonthChangeListener;

    @Nullable
    private OnDateChangeListener mOnDateChangeListener;

    private Boolean mScrollEnabled = true;
    private Boolean mHeaderViewEnabled = true;
    private Boolean mWeekendEnabled = true;
    private Boolean mHolidayEnabled = true;

    //Variable for custom typeface
    private Typeface mTypeface;

    //General background color
    private int mCalendarViewBackgroundColor;

    //HeaderView background and text color + font size
    private int mHeaderViewBackgroundColor;
    private int mHeaderViewTextColor;
    private float mHeaderViewFontSize;

    //WeekView background and text color + font size
    private int mWeekViewBackgroundColor;
    private int mWeekViewTextColor;
    private float mWeekViewFontSize;

    //AdapterView background and text color + font size
    private int mAdapterViewBackgroundColor;
    private int mAdapterViewTextColor;
    private float mAdapterViewFontSize;

    //Disable days background and text color variables
    private int mDisabledBackgroundColor;
    private int mDisabledTextColor;

    //Selected day background and text color variables
    private int mSelectedBackgroundColor;
    private int mSelectedTextColor;

    //Weekend day background and text color variables
    private int mWeekendBackgroundColor;
    private int mWeekendTextColor;

    //Holiday background and text color variables
    private int mHolidayBackgroundColor;
    private int mHolidayTextColor;

    //Current day background and text color
    private int mCurrentBackgroundColor;
    private int mCurrentTextColor;

    //ImageButton drawable..
    private Drawable mLeftArrowDrawable;
    private Drawable mRightArrowDrawable;

    //Drawable color..
    private int mDrawableColor;

    /**
     * The callback used to indicate the user changes the date.
     *
     * @author jonatan.salas
     */
    public interface OnDateChangeListener {

        /**
         * Called upon change of the selected day.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         * @param dayOfMonth The day of the month that was set.
         */
        void onDateChanged(@NonNull View view, int year, int month, int dayOfMonth);
    }

    /**
     * The callback used to indicate the user changes the month.
     *
     * @author jonatan.salas
     */
    public interface OnMonthChangeListener {

        /**
         * Called upon change of the current month.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         */
        void onMonthChanged(@NonNull View view, int year, int month);
    }

    /**
     * Constructor with params. It takes the context as param, and use to get the
     * resources that needs to inflate correctly. It requires a non null context object.
     *
     * @param context The application context used to get needed resources.
     */
    public CalendarView(@NonNull Context context) {
        super(context);

        if(isInEditMode())
            return;

        saveValues(context);
        init();
    }

    /**
     * Constructor with params. It takes the context as main param, and an AttributeSet as second
     * param. We use the context to get the resources that needs to inflate correctly and the AttributeSet
     * object used to style the view after inflate it.
     *
     * @param context The application context used to get needed resources.
     * @param attrs The AttributeSet used to get custom styles and apply to this view.
     */
    public CalendarView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);

        if(isInEditMode())
            return;

        saveValues(context);
        style(context, attrs);
        init();
    }

    /**
     * Constructor with params. It takes the context as main param, and an AttributeSet as second
     * param. We use the context to get the resources that needs to inflate correctly and the AttributeSet
     * object used to style the view after inflate it.
     *
     * @param context The application context used to get needed resources.
     * @param attrs The AttributeSet used to get custom styles and apply to this view.
     * @param defStyle Style definition for this View
     */
    public CalendarView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if(isInEditMode())
            return;

        saveValues(context);
        style(context, attrs);
        init();
    }

    /**
     * Protected method used to get some attributes.
     *
     * @param context the context object used to get the attributes.
     */
    protected void saveValues(@NonNull Context context) {
        mInflater = obtainLayoutInflater(context);
        mCurrentCalendar = obtainCalendar();
        mContext = context;
    }

    /**
     * Protected method used to style the view from AttributeSet object.
     *
     * @param attrs AttributeSet object with custom values to be applied.
     */
    protected void style(@NonNull Context context, @NonNull AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);

        final int white = ContextCompat.getColor(context, R.color.white);
        final int colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary);
        final int colorAccent = ContextCompat.getColor(context, R.color.colorAccent);
        final int darkerGray = ContextCompat.getColor(context, android.R.color.darker_gray);

        final float titleFontSize = 15f;
        final float fontSize = 14f;

        try {
            //Boolean values..
            mScrollEnabled = a.getBoolean(R.styleable.CalendarView_scrollEnable, true);
            mHeaderViewEnabled = a.getBoolean(R.styleable.CalendarView_headerViewEnable, true);
            mWeekendEnabled = a.getBoolean(R.styleable.CalendarView_weekendEnable, true);
            mHolidayEnabled = a.getBoolean(R.styleable.CalendarView_holidayEnable, true);

            //Font size values..
            mHeaderViewFontSize = a.getFloat(R.styleable.CalendarView_headerViewFontSize, titleFontSize);
            mWeekViewFontSize = a.getFloat(R.styleable.CalendarView_weekViewFontSize, titleFontSize);
            mAdapterViewFontSize = a.getFloat(R.styleable.CalendarView_adapterViewFontSize, fontSize);

            //Background color values..
            mCalendarViewBackgroundColor = a.getColor(R.styleable.CalendarView_calendarViewBackgroundColor, colorPrimary);
            mHeaderViewBackgroundColor = a.getColor(R.styleable.CalendarView_headerViewBackgroundColor, colorPrimary);
            mWeekViewBackgroundColor = a.getColor(R.styleable.CalendarView_weekViewBackgroundColor, colorPrimary);
            mAdapterViewBackgroundColor = a.getColor(R.styleable.CalendarView_adapterViewBackgroundColor, colorPrimary);
            mDisabledBackgroundColor = a.getColor(R.styleable.CalendarView_disabledBackgroundColor, colorPrimary);
            mSelectedBackgroundColor = a.getColor(R.styleable.CalendarView_selectedBackgroundColor, colorAccent);
            mWeekendBackgroundColor = a.getColor(R.styleable.CalendarView_weekViewBackgroundColor, colorPrimary);
            mHolidayBackgroundColor = a.getColor(R.styleable.CalendarView_holidayBackgroundColor, colorPrimary);
            mCurrentBackgroundColor = a.getColor(R.styleable.CalendarView_currentBackgroundColor, colorAccent);

            //Text Color values..
            mHeaderViewTextColor = a.getColor(R.styleable.CalendarView_headerViewTextColor, colorAccent);
            mWeekViewTextColor = a.getColor(R.styleable.CalendarView_weekViewTextColor, white);
            mAdapterViewTextColor = a.getColor(R.styleable.CalendarView_adapterViewTextColor, white);
            mDisabledTextColor = a.getColor(R.styleable.CalendarView_disabledTextColor, darkerGray);
            mSelectedTextColor = a.getColor(R.styleable.CalendarView_selectedTextColor, white);
            mWeekendTextColor = a.getColor(R.styleable.CalendarView_weekendTextColor, colorAccent);
            mHolidayTextColor = a.getColor(R.styleable.CalendarView_holidayTextColor, colorAccent);
            mCurrentTextColor = a.getColor(R.styleable.CalendarView_currentTextColor, white);

            //Drawable Color values..
            mDrawableColor = a.getColor(R.styleable.CalendarView_drawableColor, colorAccent);

            //Arrow drawables..
            mLeftArrowDrawable = a.getDrawable(R.styleable.CalendarView_leftArrowDrawable);
            mRightArrowDrawable = a.getDrawable(R.styleable.CalendarView_rightArrowDrawable);

        } finally {
            a.recycle();
        }
    }

    /**
     *
     */
    protected void init() {
        mRootView = mInflater.inflate(R.layout.calendar_view, this, true);
        mRootView.setBackgroundColor(mCalendarViewBackgroundColor);

        //Init CalendarView parts..
        initHeaderView();
        initWeekView();
        initAdapterView();
    }

    /**
     *
     */
    protected void initHeaderView() {
        mHeaderView = mRootView.findViewById(R.id.header_view);
        mHeaderView.setBackgroundColor(mHeaderViewBackgroundColor);

        final TextView monthTitleTextView = (TextView) findViewById(R.id.date_title);
        monthTitleTextView.setText(getHeaderTitle());
        monthTitleTextView.setTextColor(mHeaderViewTextColor);
        monthTitleTextView.setTextSize(mHeaderViewFontSize);

        //TODO JS: Add custom typeface..
        monthTitleTextView.setTypeface(Typeface.DEFAULT_BOLD);

        final ImageView nextButton = (ImageView) findViewById(R.id.next_button);
        nextButton.setEnabled(true);
        nextButton.setClickable(true);

        if(null != mRightArrowDrawable)
            nextButton.setImageDrawable(mRightArrowDrawable);

        nextButton.setColorFilter(mDrawableColor, PorterDuff.Mode.SRC_ATOP);

        final ImageView backButton = (ImageView) findViewById(R.id.back_button);

        backButton.setEnabled(true);
        backButton.setClickable(true);

        if(null != mLeftArrowDrawable)
            nextButton.setImageDrawable(mLeftArrowDrawable);

        backButton.setColorFilter(mDrawableColor, PorterDuff.Mode.SRC_ATOP);

        mHeaderView.invalidate();
    }

    /**
     *
     */
    protected void initWeekView() {
        mWeekView = mRootView.findViewById(R.id.week_view);
        mWeekView.setBackgroundColor(mWeekViewBackgroundColor);
        String dayName;
        TextView dayOfWeek;

        for(int i = 1; i < getShortWeekDays().length; i++) {
            dayName = getShortWeekDay(i);
            int length = (dayName.length() < 3)? dayName.length() : 3;
            dayName = dayName.substring(0, length).toUpperCase();

            final String tag = String.valueOf(CalendarUtil.calculateWeekIndex(i, getCurrentCalendar()));

            dayOfWeek = (TextView) findViewWithTag(tag);
            dayOfWeek.setText(dayName);
            dayOfWeek.setTextColor(mWeekViewTextColor);
            dayOfWeek.setTextSize(mWeekViewFontSize);
        }

        mWeekView.invalidate();
    }

    /**
     *
     */
    protected void initAdapterView() {
        final List<DayTime> days = obtainDays(obtainMonthDisplayHelper(), getCurrentCalendar());
        final DayAdapter dayAdapter = new DayAdapter(mContext, days);

        mAdapterView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mAdapterView.setLayoutManager(obtainGridLayoutManager());
        mAdapterView.setHasFixedSize(true);
        mAdapterView.setAdapter(dayAdapter);
        mAdapterView.setItemAnimator(new DefaultItemAnimator());
        mAdapterView.setNestedScrollingEnabled(mScrollEnabled);
        mAdapterView.setBackgroundColor(mAdapterViewBackgroundColor);

        dayAdapter.notifyDataSetChanged();

        mAdapterView.invalidate();
    }

    /**
     * @author jonatan.salas
     */
    protected class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
        protected List<DayTime> mItems;
        protected Context mContext;

        /**
         * @author jonatan.salas
         */
        protected class DayViewHolder extends RecyclerView.ViewHolder {
            protected TextView mDayView;

            /**
             *
             * @param view
             */
            public DayViewHolder(@NonNull View view) {
                super(view);
                mDayView = (TextView) view.findViewById(R.id.day_view);
            }
        }

        /**
         *
         * @param context
         * @param items
         */
        public DayAdapter(@NonNull Context context, @NonNull List<DayTime> items) {
            mContext = context;
            mItems = items;
        }

        @Override
        public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View v = mInflater.inflate(R.layout.day_view, parent, false);
            return new DayViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayViewHolder holder, int position) {
            final DayTime day = mItems.get(position);
            holder.mDayView.setClickable(true);
            holder.mDayView.setText(String.valueOf(day.getDay()));
            holder.mDayView.setTextSize(mAdapterViewFontSize);
            holder.mDayView.setTextColor(mAdapterViewTextColor);

            if (!day.isCurrentMonth()) {
                holder.mDayView.setTypeface(Typeface.DEFAULT_BOLD);
                holder.mDayView.setBackgroundColor(mDisabledBackgroundColor);
                holder.mDayView.setTextColor(mDisabledTextColor);
                holder.mDayView.setEnabled(false);
                holder.mDayView.setClickable(false);
            }

            if(day.isWeekend()) {
                holder.mDayView.setBackgroundColor(mWeekendBackgroundColor);
                holder.mDayView.setTextColor(mWeekendTextColor);
                holder.mDayView.setTypeface(Typeface.DEFAULT_BOLD);
            }

            if(day.isCurrentDay()) {
                holder.mDayView.setBackgroundColor(mCurrentBackgroundColor);
                holder.mDayView.setTextColor(mCurrentTextColor);
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        /**
         * Method used to set a List of days in the adapter.
         *
         * @param items the list of days to show.
         */
        public void setItems(List<DayTime> items) {
            this.mItems = items;
        }
    }

    //-----------------------------------------------------------------//
    //                         PROTECTED GETTERS                       //
    //-----------------------------------------------------------------//
    /**
     *
     * @param helper
     * @param currentCalendar
     * @return
     */
    protected List<DayTime> obtainDays(@NonNull MonthDisplayHelper helper, Calendar currentCalendar) {
        List<DayTime> days = new ArrayList<>(SIZE);

        for(int i = 0; i < 6; i++) {
            int n[] = helper.getDigitsForRow(i);

            for(int d = 0; d < 7; d++) {
                if(helper.isWithinCurrentMonth(i, d)) {
                    Calendar calendar = obtainCalendar();
                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);

                    if(n[d] == currentCalendar.get(Calendar.DAY_OF_MONTH) && CalendarUtil.isWeekend(calendar)) {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setIsCurrentDay(true)
                                .setIsCurrentMonth(true)
                                .setIsCurrentYear(true)
                                .setIsWeekend(true)
                                .setEventList(null);

                        days.add(dayTime);
                    } else if(n[d] == currentCalendar.get(Calendar.DAY_OF_MONTH)) {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setIsCurrentDay(true)
                                .setIsCurrentMonth(true)
                                .setIsCurrentYear(true)
                                .setIsWeekend(false)
                                .setEventList(null);

                        days.add(dayTime);
                    } else if(CalendarUtil.isWeekend(calendar)) {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setIsCurrentDay(false)
                                .setIsCurrentMonth(true)
                                .setIsCurrentYear(true)
                                .setIsWeekend(true)
                                .setEventList(null);

                        days.add(dayTime);
                    } else {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setIsCurrentDay(false)
                                .setIsCurrentMonth(true)
                                .setIsCurrentYear(true)
                                .setIsWeekend(false)
                                .setEventList(null);

                        days.add(dayTime);
                    }

                } else {
                    int month = getMonth();
                    int year = getYear();

                    if(d == 0) {
                        Calendar calendar = obtainCalendar();
                        calendar.set(Calendar.DAY_OF_MONTH, n[d]);
                        calendar.set(Calendar.MONTH, -1);

                        month = calendar.get(Calendar.MONTH);
                        year = calendar.get(Calendar.YEAR);

                    } else if(d == 6) {
                        Calendar calendar = obtainCalendar();
                        calendar.set(Calendar.DAY_OF_MONTH, n[d]);
                        calendar.set(Calendar.MONTH, 1);

                        month = calendar.get(Calendar.MONTH);
                        year = calendar.get(Calendar.YEAR);
                    }

                    final DayTime dayTime = new DayTime()
                            .setDay(n[d])
                            .setMonth(month)
                            .setYear(year)
                            .setIsCurrentDay(false)
                            .setIsCurrentMonth(false)
                            .setIsCurrentYear(true)
                            .setIsWeekend(false)
                            .setEventList(null);

                    days.add(dayTime);
                }
            }
        }

        return days;
    }

    /**
     *
     * @return
     */
    protected MonthDisplayHelper obtainMonthDisplayHelper() {
        return new MonthDisplayHelper(getYear(), getMonth(), getFirstDayOfWeek());
    }

    /**
     *
     * @return
     */
    protected GridLayoutManager obtainGridLayoutManager() {
        return new GridLayoutManager(mContext, SPAN_COUNT);
    }

    /**
     *
     * @param context
     * @return
     */
    protected LayoutInflater obtainLayoutInflater(@NonNull Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     *
     * @return
     */
    protected Calendar obtainCalendar() {
        return Calendar.getInstance(Locale.getDefault());
    }

    //-----------------------------------------------------------------//
    //                            SETTERS                              //
    //-----------------------------------------------------------------//
    public void setOnMonthChangeListener(@Nullable OnMonthChangeListener onMonthChangeListener) {
        this.mOnMonthChangeListener = onMonthChangeListener;
    }

    public void setOnDateChangeListener(@Nullable OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    public void setScrollEnabled(Boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    //-----------------------------------------------------------------//
    //                            GETTERS                              //
    //-----------------------------------------------------------------//
    public OnMonthChangeListener getOnMonthChangeListener() {
        return mOnMonthChangeListener;
    }

    public OnDateChangeListener getOnDateChangeListener() {
        return mOnDateChangeListener;
    }

    public Boolean isScrollEnabled() {
        return mScrollEnabled;
    }

    public int getYear() {
        return mCurrentCalendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return mCurrentCalendar.get(Calendar.MONTH);
    }

    public int getFirstDayOfWeek() {
        return mCurrentCalendar.getFirstDayOfWeek();
    }

    public int getCurrentDay() {
        return mCurrentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getShortWeekDay(int position) {
        return getShortWeekDays()[position].trim().toUpperCase(Locale.getDefault());
    }

    public String[] getShortWeekDays() {
        return new DateFormatSymbols(Locale.getDefault()).getShortWeekdays();
    }

    public String getHeaderTitle() {
        final String month = new DateFormatSymbols(Locale.getDefault()).getMonths()[getMonth()];
        return month.toUpperCase(Locale.getDefault()) + " " + String.valueOf(getYear());
    }

    public Calendar getCurrentCalendar() {
        return mCurrentCalendar;
    }
}