package periods.adapters;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import periods.PatchacaPeriodsOperator;
import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TasksSystem;
import tasks.adapters.PatchacaTasksOperatorUsingBusinessLayer;
import tasks.tasks.TaskView;

public final class PatchacaPeriodsOperatorUsingBusinessLayer implements
		PatchacaPeriodsOperator {

	private final PatchacaTasksOperatorUsingBusinessLayer tasksOperator;
	private final TasksSystem tasksSystem;
	private final DateFormat format = new SimpleDateFormat("hh:mm a");
	private final PeriodsInTasksSystem periodsSystem;

	public PatchacaPeriodsOperatorUsingBusinessLayer(
			final PatchacaTasksOperatorUsingBusinessLayer tasksOperator, 
			final TasksSystem tasksSystem, 
			final PeriodsInTasksSystem periodsSystem) {
				this.tasksOperator = tasksOperator;
				this.tasksSystem = tasksSystem;
				this.periodsSystem = periodsSystem;
			
				
	}

	@Override
	public void editPeriod(final String taskName, final int periodIndex, final String start,
			final String stop) {
		final TaskView taskByName = tasksOperator.taskByName(taskName);
		
		try {
			tasksSystem.setPeriodStarting(taskByName, periodIndex, format.parse(start));
			tasksSystem.setPeriodEnding(taskByName, periodIndex, format.parse(stop));
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void editPeriod(final String taskName, final int periodIndex, final String start) {
		final TaskView taskByName = tasksOperator.taskByName(taskName);
			
		
		try {
			tasksSystem.setPeriodStarting(taskByName, periodIndex, format.parse(start));
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void assertPeriodCount(final String taskName, final int i) {
		if (tasksOperator.taskByName(taskName).periodsCount() != i)
			throw new RuntimeException("There are periods in task " + taskName + ", there should be none");
	}

	@Override
	public void removePeriod(final String taskName, final int i) {
		final TaskView taskByName = tasksOperator.taskByName(taskName);
		periodsSystem.removePeriod(taskByName, taskByName.getPeriod(i));
		
	}

	@Override
	public void addPeriod(final String taskName) {
		final Date date = Calendar.getInstance().getTime();
		final Period period = new Period(date,date);
		periodsSystem.addPeriod(tasksOperator.taskByName(taskName), period);
	}

	@Override
	public void assertPeriodDay(final String taskName, final int i, final String dateMM_DD_YYYY) {
		final Date date = parseDateMM_dd_YYYY(dateMM_DD_YYYY);
		final TaskView task = tasksOperator.taskByName(taskName);
		final Date actual = transformInMM_dd_yyyy(task.periodAt(i).endTime());
		Assert.assertEquals(date, actual);
	}

	private Date transformInMM_dd_yyyy(final Date date) {
		final SimpleDateFormat mm_dd_YYYY = new SimpleDateFormat("MM_dd_yyyy");
		
		Date actual; 
		try {
			actual = mm_dd_YYYY.parse(mm_dd_YYYY.format(date));
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
		return actual;
	}

	private Date parseDateMM_dd_YYYY(final String dateMM_DD_YYYY) {
		final String format = "MM_dd_yyyy";
		try {
			return  new SimpleDateFormat(format).parse(dateMM_DD_YYYY);
		} catch (final ParseException e) {
			throw new InvalidParameterException("The date: '" + dateMM_DD_YYYY + " is not parseable by " + format );
		}
	}

	@Override
	public void editPeriodDay(final String taskName, final int i, final String dateMM_DD_YYYY) {
		final TaskView task = tasksOperator.taskByName(taskName);
		final Date date = parseDateMM_dd_YYYY(dateMM_DD_YYYY);
		periodsSystem.editPeriodDay(task, i, date);			
	}

}
