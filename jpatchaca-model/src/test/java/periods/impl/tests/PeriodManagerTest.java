package periods.impl.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import periods.impl.PeriodManagerImpl;

public class PeriodManagerTest {

	PeriodManager manager;

	private final Mockery context = new JUnit4Mockery();
	
	@Before
	public void setUp() {
		manager = new PeriodManagerImpl();
	}

	@Test
	public void testAddPeriods() {
		final PeriodManager manager = new PeriodManagerImpl();

		final Period period0 = new Period(new Date(0));
		final Period period1 = new Period(new Date(1));

		manager.addPeriod(period0);
		manager.addPeriod(period1);

		Assert.assertEquals(2, manager.periodsList().currentSize());

		manager.removePeriod(period0);
		Assert.assertEquals(1, manager.periodsList().currentSize());
	}

	@Test
	public void testListeners() throws ParseException {
		final PeriodsListener listenerMocker = context.mock(PeriodsListener.class);

		final DateFormat dateTimeFormat = new SimpleDateFormat(
				"dd/MM/yyyy hh:mm");

		final Period period1 = new Period(dateTimeFormat
				.parse("16/12/2005 00:35"), dateTimeFormat
				.parse("16/12/2005 01:35"));
		final Period period2 = new Period(dateTimeFormat
				.parse("16/12/2005 02:35"), dateTimeFormat
				.parse("16/12/2005 03:35"));
		final Period period3 = new Period(dateTimeFormat
				.parse("17/12/2005 02:35"), dateTimeFormat
				.parse("17/12/2005 03:35"));
		final Period period4 = new Period(dateTimeFormat
				.parse("19/12/2005 02:35"), dateTimeFormat
				.parse("19/12/2005 03:35"));

		manager.addPeriod(period1);

		context.checking(new Expectations() {

			{
				oneOf(listenerMocker).periodAdded(period1);
				oneOf(listenerMocker).periodAdded(period2);
			}
		});
		manager.addListener(listenerMocker);
		manager.addPeriod(period2);

		context.checking(new Expectations() {

			{
				oneOf(listenerMocker).periodAdded(period3);
			}
		});
		manager.addPeriod(period3);

		context.checking(new Expectations() {

			{
				oneOf(listenerMocker).periodAdded(period4);
			}
		});
		manager.addPeriod(period4);

		context.checking(new Expectations() {

			{
				oneOf(listenerMocker).periodRemoved(period4);
			}
		});
		manager.removePeriod(period4);

	}

	@Test
	public void testTotalTime() {

		manager.addPeriod(new Period(new Date(0), new Date(100)));
		manager.addPeriod(new Period(new Date(100), new Date(200)));
		Assert.assertTrue(200L == manager.totalTime());
	}

}
