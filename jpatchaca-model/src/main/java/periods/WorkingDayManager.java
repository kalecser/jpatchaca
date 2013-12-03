package periods;

import java.util.Date;

public interface WorkingDayManager {

    boolean isWorkingDay(Date date);

    Date nextWorkingDay(int delta, Date when);
}
