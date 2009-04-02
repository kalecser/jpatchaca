package basic;

import java.util.Date;



public interface SystemClock {

		Date getDate();
		Long getTime();
		void setTime(Long time);
}
