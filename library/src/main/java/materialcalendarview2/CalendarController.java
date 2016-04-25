package materialcalendarview2;

import android.util.MonthDisplayHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import materialcalendarview2.model.DayTime;
import materialcalendarview2.util.CalendarUtil;

/**
 * @author jonatan.salas
 */
public final class CalendarController {
    private static CalendarController instance = null;

    private int monthIndex = 0;
    private Locale locale = Locale.getDefault();
    private Calendar calendar = Calendar.getInstance(locale);

    private MonthDisplayHelper helper = new MonthDisplayHelper(
            CalendarUtil.getYear(calendar),
            CalendarUtil.getMonth(calendar),
            CalendarUtil.getFirstDayOfWeek(calendar)
    );

    //Prevent from instantiation
    private CalendarController() { }

    public static CalendarController getInstance() {
        if (null == instance) {
            instance = new CalendarController();
        }

        return instance;
    }

    public Calendar getCalendarFromIndex(int index) {
        final Calendar calendar = Calendar.getInstance(getLocale());
        calendar.add(Calendar.MONTH, index);

        return calendar;
    }

    //TODO JS: Needs to be implemented
    public List<DayTime> getListFromIndex(int index) {
        return new ArrayList<>();
    }

    public Locale getLocale() {
        return locale;
    }

    public CalendarController setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public CalendarController setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
        return this;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public CalendarController setCalendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }
}
