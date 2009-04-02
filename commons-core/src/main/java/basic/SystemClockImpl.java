package basic;

import java.util.Date;



public class SystemClockImpl implements SystemClock {



	private Long time;

	public void setTime(Long time) {
		this.time = time;		
	}

	public Long getTime() {
		return time;
	}

	public Date getDate() {
		return new Date(getTime());
	}

}
