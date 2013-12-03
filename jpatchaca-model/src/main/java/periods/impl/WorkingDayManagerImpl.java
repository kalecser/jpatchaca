package periods.impl;

import de.jollyday.Holiday;
import de.jollyday.HolidayManager;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import periods.WorkingDayManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class WorkingDayManagerImpl implements WorkingDayManager {

    private final LocalDateCalculator calculator;

    {
        final HolidayManager manager = HolidayManager.getInstance(de.jollyday.HolidayCalendar.BRAZIL);
        final DateTime today = new DateTime();

        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.addAll(extract(manager.getHolidays(today.getYear())));
        holidays.addAll(extract(manager.getHolidays(today.getYear() - 1)));
        holidays.addAll(extract(manager.getHolidays(today.getYear() + 1)));

        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("BR", new DefaultHolidayCalendar<LocalDate>(holidays));
        calculator = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("BR",
                HolidayHandlerType.BACKWARD);
    }

    private Set<LocalDate> extract(Set<Holiday> holidays) {
        Set<LocalDate> ret = new HashSet<LocalDate>();

        for (Holiday holiday : holidays)
            ret.add(new LocalDate(holiday.getDate(), ISOChronology.getInstance()));

        return ret;
    }

    @Override
    public boolean isWorkingDay(Date date) {
        return !calculator.isNonWorkingDay(new LocalDate(date));
    }

    private Date addDays(int delta, Date when) {
        return new DateTime(when).plusDays(delta).toDate();
    }

    @Override
    public Date nextWorkingDay(int delta, Date when) {
        Date calculated = when;
        int realCount = 0;

        while (realCount < delta) {
            if (isWorkingDay(calculated)) realCount++;
            calculated = addDays(1, calculated);
        }

        return lastSecondOf(calculated);
    }

    private Date lastSecondOf(Date when) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(when);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
