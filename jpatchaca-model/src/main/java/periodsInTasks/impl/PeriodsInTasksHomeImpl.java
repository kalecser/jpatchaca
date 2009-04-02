package periodsInTasks.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import periods.Period;
import periodsInTasks.MonthYear;
import periodsInTasks.PeriodsInTasksHome;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class PeriodsInTasksHomeImpl implements PeriodsInTasksHome{

	private final TasksSystem taskSystem;
	private final List<Period> periods = new ArrayList<Period>();

	public PeriodsInTasksHomeImpl(final TasksSystem taskSystem){
		this.taskSystem = taskSystem;		
	}
		
	@Override
	public synchronized void addPeriodToTask(final ObjectIdentity taskId, final Period period) throws MustBeCalledInsideATransaction{
		taskSystem.addPeriodToTask(taskId, period);
		periods.add(period);
	}

	@Override
	public synchronized void removePeriodFromTask(final TaskView task, final Period period) throws MustBeCalledInsideATransaction {
		taskSystem.removePeriod(task, period);
		periods.remove(period);
	}

	private MonthYear getMonthYear(final Period period) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(period.getMonth());
		final MonthYear my = new MonthYear(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
		return my;
	}

	@Override
	public synchronized List<MonthYear> monthYears() {
		
		final List<MonthYear> monthYears = new ArrayList<MonthYear>();
		for (final Period period : periods){
			final MonthYear monthYear = getMonthYear(period);
			if (!monthYears.contains(monthYear))
				monthYears.add(monthYear);
		}
		
		return monthYears;
	}


}
