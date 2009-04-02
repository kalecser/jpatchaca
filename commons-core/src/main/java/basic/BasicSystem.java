package basic;

import java.util.Date;

public interface BasicSystem {
	
	Date getTime();
	Date getHardwareTime();
	SystemClock systemClock();
	IdProvider idProvider();
	
}
