package materialcalendarview2.callback;

import android.util.MonthDisplayHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import materialcalendarview2.callback.base.CallBack;
import materialcalendarview2.model.DayTime;

import static materialcalendarview2.util.CalendarUtil.getFirstDayOfWeek;
import static materialcalendarview2.util.CalendarUtil.getMonth;
import static materialcalendarview2.util.CalendarUtil.getYear;
import static materialcalendarview2.util.CalendarUtil.isWeekend;

/**
 * @author jonatan.salas
 */
public final class MonthListHelper {
    private static MonthListHelper instance = null;
    private final MonthDisplayHelper helper;
    private final List<DayTime> list;
    private final int index;

    private MonthListHelper(final int year, final int month, final int firstDayOfWeek, final int monthIndex) {
        this.helper = new MonthDisplayHelper(year, month, firstDayOfWeek);
        this.list = new ArrayList<>(42);
        this.index = monthIndex;
    }

    public static MonthListHelper getInstance(final int year, final int month, final int firstDayOfWeek, final int monthIndex) {
        if (null == instance) {
            instance = new MonthListHelper(year, month, firstDayOfWeek, monthIndex);
        }

        return instance;
    }

    public List<DayTime> onInitMonth() {
        return null;
    }

    public List<DayTime> onNextMonth() {
        return null;
    }

    public List<DayTime> onPreviousMonth() {
        return null;
    }
//
//
//
//
//
//    @Override
//    public List<DayTime> execute() {
//
//        //TODO JS: Evaluate this code.
//        for (int i = 0; i < 6; i++) {
//            int n[] = helper.getDigitsForRow(i);
//
//            for (int d = 0; d < 7; d++) {
//                if (helper.isWithinCurrentMonth(i, d)) {
//                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
//                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);
//                    calendar.add(Calendar.MONTH, index);
//
//                    int m = getMonth(calendar);
//                    int y = getYear(calendar);
//
//                    if (n[d] == calendar.get(Calendar.DAY_OF_MONTH) && isWeekend(calendar) && index == 0) {
//                        DayTime dayTime = new DayTime()
//                                .setDay(n[d])
//                                .setMonth(m)
//                                .setYear(y)
//                                .setCurrentDay(false)
//                                .setCurrentMonth(true)
//                                .setCurrentYear(true)
//                                .setWeekend(true)
//                                .setEventList(null);
//
//                        list.add(dayTime);
//                    } else if (n[d] == calendar.get(Calendar.DAY_OF_MONTH) && index == 0) {
//                        DayTime dayTime = new DayTime()
//                                .setDay(n[d])
//                                .setMonth(m)
//                                .setYear(y)
//                                .setCurrentDay(true)
//                                .setCurrentMonth(true)
//                                .setCurrentYear(true)
//                                .setWeekend(false)
//                                .setEventList(null);
//
//                        list.add(dayTime);
//                    } else if (isWeekend(calendar)) {
//                        DayTime dayTime = new DayTime()
//                                .setDay(n[d])
//                                .setMonth(m)
//                                .setYear(y)
//                                .setCurrentDay(false)
//                                .setCurrentMonth(true)
//                                .setCurrentYear(true)
//                                .setWeekend(true)
//                                .setEventList(null);
//
//                        list.add(dayTime);
//                    } else {
//                        final DayTime dayTime = new DayTime()
//                                .setDay(n[d])
//                                .setMonth(m)
//                                .setYear(y)
//                                .setCurrentDay(false)
//                                .setCurrentMonth(true)
//                                .setCurrentYear(true)
//                                .setWeekend(false)
//                                .setEventList(null);
//
//                        list.add(dayTime);
//                    }
//
//                } else {
//                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
//                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);
//                    calendar.add(Calendar.MONTH, index);
//
//                    int m = getMonth(calendar);
//                    int y = getYear(calendar);
//
//                    DayTime dayTime = new DayTime()
//                            .setDay(n[d])
//                            .setMonth(m)
//                            .setYear(y)
//                            .setCurrentDay(false)
//                            .setCurrentMonth(false)
//                            .setCurrentYear(true)
//                            .setWeekend(false)
//                            .setEventList(null);
//
//                    list.add(dayTime);
//                }
//            }
//        }
//
//        return list;
//    }
}
