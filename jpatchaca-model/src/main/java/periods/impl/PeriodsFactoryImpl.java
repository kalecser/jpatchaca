package periods.impl;

import java.util.Date;

import periods.Period;
import periods.PeriodsFactory;

public class PeriodsFactoryImpl implements PeriodsFactory {


	public PeriodsFactoryImpl(){
	}
	
	public Period createPeriod(Date start) {
		return new Period( start);
	}

}
