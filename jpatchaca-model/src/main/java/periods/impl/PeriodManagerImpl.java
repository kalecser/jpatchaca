package periods.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import reactive.ListSignal;
import reactive.ListSource;

@SuppressWarnings("boxing")
public class PeriodManagerImpl implements PeriodManager {

	private final List<Period> periods;
	private final List<PeriodsListener> listeners;
	private final ListSource<Period> periodsList = new ListSource<Period>();

	public PeriodManagerImpl() {
		this.periods = new ArrayList<Period>();
		this.listeners = new ArrayList<PeriodsListener>();
	}

	@Override
	public synchronized void addPeriod(final Period period) {

		this.periods.add(period);
		periodsList.add(period);
		firePeriodAdded(period);
	}

	private synchronized void firePeriodAdded(final Period period) {
		for (final PeriodsListener listener : this.listeners) {
			listener.periodAdded(period);
		}

	}

	@Override
	public synchronized List<Period> periods() {
		return Collections.unmodifiableList(this.periods);
	}

	@Override
	public synchronized void addListener(final PeriodsListener listener) {
		this.listeners.add(listener);

		for (final Period period : this.periods) {
			listener.periodAdded(period);
		}
	}

	@Override
	public synchronized Long totalTime() {
		Long totalTime = 0L;

		for (final Period period : this.periods()) {
			totalTime += period.totalTime();
		}

		return totalTime;
	}

	@Override
	public synchronized void removePeriod(final Period period) {
		this.periods.remove(period);
		periodsList.remove(period);
		firePeriodRemoved(period);
	}

	private synchronized void firePeriodRemoved(final Period period) {
		for (final PeriodsListener listener : this.listeners) {
			listener.periodRemoved(period);
		}
	}

	@Override
	public synchronized void removeListener(final PeriodsListener listener) {
		listeners.remove(listener);
	}

	@Override
	public synchronized Period period(final int index) {
		return periods.get(index);
	}

	@Override
	public synchronized ListSignal<Period> periodsList() {
		return periodsList;
	}

}
