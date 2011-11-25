package basic;

import java.util.Date;



public class SystemClockImpl implements SystemClock {



	private Long time;

	@Override
	public void setTime(Long time) {
		this.time = time;		
	}

	@Override
	public Long getTime() {
		return time;
	}

	@Override
	public Date getDate() {
		return new Date(getTime());
	}

}
