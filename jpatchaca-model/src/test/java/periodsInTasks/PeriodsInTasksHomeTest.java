package periodsInTasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import periods.Period;
import periodsInTasks.impl.PeriodsInTasksHomeImpl;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class PeriodsInTasksHomeTest extends TestCase {

	public void testMonthYears() throws MustBeCalledInsideATransaction,
			ParseException {
		final PeriodsInTasksHome periodsInTasks = new PeriodsInTasksHomeImpl(
				new MockTasksSystem());

		periodsInTasks.addPeriodToTask(new ObjectIdentity("1"), new Period(
				date("12-01-2008")));
		periodsInTasks.addPeriodToTask(new ObjectIdentity("1"), new Period(
				date("12-10-2008")));
		periodsInTasks.addPeriodToTask(new ObjectIdentity("1"), new Period(
				date("01-10-2009")));
		periodsInTasks.addPeriodToTask(new ObjectIdentity("1"), new Period(
				date("01-10-2007")));
		periodsInTasks.removePeriodFromTask(new MockTask(), new Period(
				date("01-10-2007")));

		final List<MonthYear> monthYears = periodsInTasks.monthYears();
		assertEquals("12-2008", monthYears.get(0).print());
		assertEquals("01-2009", monthYears.get(1).print());
	}

	public void testMonthYearsEditPeriod()
			throws MustBeCalledInsideATransaction, ParseException {
		final PeriodsInTasksHome periodsInTasks = new PeriodsInTasksHomeImpl(
				new MockTasksSystem());
		final Period period = new Period(date("12-01-2008"));

		periodsInTasks.addPeriodToTask(new ObjectIdentity("1"), period);

		final List<MonthYear> monthYears = periodsInTasks.monthYears();
		assertEquals("12-2008", monthYears.get(0).print());

		period.setStart(date("01-01-2008"));
		final List<MonthYear> monthYearsAfterEdition = periodsInTasks
				.monthYears();
		assertEquals("01-2008", monthYearsAfterEdition.get(0).print());

	}

	private Date date(final String string) throws ParseException {
		return new SimpleDateFormat("MM-dd-yyyy").parse(string);
	}

}
