package ui.swing.mainScreen.periods;

import lang.Maybe;

import org.reactive.Signal;

import periods.Period;
import reactive.ListSource;
import ui.swing.tasks.SelectedTaskPeriods;

public class SelectedTaskPeriodsMock implements SelectedTaskPeriods {

	
	private final ListSource<Period> periods = new ListSource<Period>();
	
	@Override
	public Signal<Integer> size() {
		return periods.size(); 
	}

	@Override
	public int currentSize() {
		return periods.currentSize();	}

	@Override
	public Period currentGet(int rowIndex) {
		Maybe<Period> currentGet = periods.currentGet(rowIndex);
		return currentGet.unbox();	}

	@Override
	public Signal<Maybe<Period>> get(int rowIndex) {
		return periods.get(rowIndex);	}

	public void addPeriod(Period period) {
		periods.add(period);		
	}

}
