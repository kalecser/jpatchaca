package periods.impl;

import java.util.ArrayList;
import java.util.List;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;



public class PeriodManagerImpl implements PeriodManager {

	private final List<Period> periods;
	private final List<PeriodsListener> listeners;

	public PeriodManagerImpl(){
		this.periods = new ArrayList<Period>();
		this.listeners = new ArrayList<PeriodsListener>();
	}
	
	public void addPeriod(Period period) {
		
		this.periods.add(period);	
		firePeriodAdded(period);
	}

	private void firePeriodAdded(Period period) {
		for (final PeriodsListener listener : this.listeners){
			listener.periodAdded(period);
		}
		
	}

	public List<Period> periods() {
		return this.periods;
	}

	public void addListener(PeriodsListener listener) {
		this.listeners.add(listener);
		
		for (final Period period : this.periods){
			listener.periodAdded(period);
		}
	}

	public Long totalTime() {
		Long totalTime = 0L;
		
		for(final Period period : this.periods()) {
			totalTime += period.totalTime();
		}
		
		return totalTime;
	}

	public void removePeriod(Period period) {
		this.periods.remove(period);
		firePeriodRemoved(period);
	}

	private void firePeriodRemoved(Period period) {
		for (final PeriodsListener listener : this.listeners){
			listener.periodRemoved(period);
		}		
	}

	@Override
	public void removeListener(PeriodsListener listener) {
		listeners.remove(listener);		
	}

	@Override
	public Period period(int index) {
		return periods.get(index);
	}

	
}
