package basic.durable;

import java.util.Calendar;
import java.util.Date;

import basic.HardwareClock;

public class HardwareClockImpl implements HardwareClock {

	public Date getTime() {
		return Calendar.getInstance().getTime();
	}

}
