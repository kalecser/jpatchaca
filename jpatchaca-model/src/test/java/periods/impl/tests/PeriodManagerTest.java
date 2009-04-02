package periods.impl.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import periods.impl.PeriodManagerImpl;



public class PeriodManagerTest extends MockObjectTestCase {

	PeriodManager manager;
	
	@Override
	protected void setUp() throws Exception {
		manager = new PeriodManagerImpl();
		super.setUp();
	}
	
	public void testAddPeriods() throws ParseException{
		final Mock listenerMocker = mock(PeriodsListener.class);
		final PeriodsListener listenerMock = (PeriodsListener) listenerMocker.proxy();
				
		final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		final Period period1 = new Period(dateTimeFormat.parse("16/12/2005 00:35"),
				   dateTimeFormat.parse("16/12/2005 01:35"));
		final Period period2 = new Period(dateTimeFormat.parse("16/12/2005 02:35"),
				   dateTimeFormat.parse("16/12/2005 03:35"));
		final Period period3 = new Period(dateTimeFormat.parse("17/12/2005 02:35"),
				   dateTimeFormat.parse("17/12/2005 03:35"));
		final Period period4 = new Period(dateTimeFormat.parse("19/12/2005 02:35"),
				   dateTimeFormat.parse("19/12/2005 03:35"));
		
		manager.addPeriod(period1);
		
		listenerMocker.expects(once()).method("periodAdded").with(eq(period1));
		listenerMocker.expects(once()).method("periodAdded").with(eq(period2));
		manager.addListener(listenerMock);
		manager.addPeriod(period2);				
		
		listenerMocker.expects(once()).method("periodAdded").with(eq(period3));
		manager.addPeriod(period3);
		
		listenerMocker.expects(once()).method("periodAdded").with(eq(period4));
		manager.addPeriod(period4);
				
		listenerMocker.expects(once()).method("periodRemoved").with(eq(period4));
		manager.removePeriod(period4);
		
		
		
	}
	
	public void testTotalTime() {
		
		manager.addPeriod(new Period(new Date(0), new Date(100)));
		manager.addPeriod(new Period(new Date(100), new Date(200)));
		assertTrue(200L == manager.totalTime());		
	}
	

	

}
