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
	
	public synchronized void addPeriod(Period period) {
		
		this.periods.add(period);	
		firePeriodAdded(period);
	}

	private synchronized void firePeriodAdded(Period period) {
		for (final PeriodsListener listener : this.listeners){
			listener.periodAdded(period);
		}
		
	}

	public synchronized List<Period> periods() {
		return this.periods;
	}

	public synchronized void addListener(PeriodsListener listener) {
		this.listeners.add(listener);
		
		for (final Period period : this.periods){
			listener.periodAdded(period);
		}
	}

	public synchronized  Long totalTime() {
		Long totalTime = 0L;
		
		for(final Period period : this.periods()) {
			totalTime += period.totalTime();
		}
		
		return totalTime;
	}

	public synchronized void removePeriod(Period period) {
		this.periods.remove(period);
		firePeriodRemoved(period);
	}

	private synchronized void firePeriodRemoved(Period period) {
		for (final PeriodsListener listener : this.listeners){
			listener.periodRemoved(period);
		}		
	}

	@Override
	public synchronized void removeListener(PeriodsListener listener) {
		listeners.remove(listener);		
	}

	@Override
	public synchronized Period period(int index) {
		return periods.get(index);
	}

	
}
