package localization;

import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.picocontainer.Startable;

public class BrazilDaylightSavingTimezoneAdjuster implements Startable {

	@Override
	public void start() {
		
		TimeZone tz = TimeZone.getDefault();
		if (tz.useDaylightTime() && tz.getDisplayName().equals("Brasilia Time")){
			TimeZone.setDefault(new SimpleTimeZone(tz.getRawOffset(),
					tz.getID() + " (Horário de Verão 2007/2008)",
					Calendar.OCTOBER,
					19,
					0,
					0,
					Calendar.FEBRUARY,
					15,
					0,
					0));
			
			
		}
	}

	@Override
	public void stop() {

	}
	

}
