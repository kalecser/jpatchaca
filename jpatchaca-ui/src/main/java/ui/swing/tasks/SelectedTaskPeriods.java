package ui.swing.tasks;

import lang.Maybe;

import org.reactive.Signal;

import periods.Period;

public interface SelectedTaskPeriods {

	public abstract Signal<Integer> size();

	public abstract int currentSize();

	public abstract Period currentGet(final int rowIndex);

	public abstract Signal<Maybe<Period>> get(final int rowIndex);

}