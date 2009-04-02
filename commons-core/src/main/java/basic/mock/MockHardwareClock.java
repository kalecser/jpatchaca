package basic.mock;

import java.util.Date;

import basic.HardwareClock;

public class MockHardwareClock implements HardwareClock {

	private Date time = new Date(0);
	
	public Date getTime() {
		return (Date) time.clone() ;
	}

	public void setTime(Date time) {
		this.time = (Date) time.clone();
	}

	public void setTime(int time) {
		this.time = new Date(time);		
	}

}
