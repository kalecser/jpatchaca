package periods.impl.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

import periods.Period;
import periods.impl.PeriodsFactoryImpl;



public class PeriodTest extends TestCase {

	
	public void testPeriod() throws ParseException{
		final DateFormat format = new SimpleDateFormat("yyyy-MM-d hh:mm");
		
		
		final Period period = new PeriodsFactoryImpl().createPeriod(format.parse("2005-12-6 23:40"));
		assertEquals(format.parse("2005-12-6 00:00"), period.getDay());
		
		assertEquals(0.0, period.getMiliseconds());
			
		period.setStop(format.parse("2005-12-7 01:10"));
		assertEquals(1.5, period.getMiliseconds() / DateUtils.MILLIS_PER_HOUR);
		
	}
}
