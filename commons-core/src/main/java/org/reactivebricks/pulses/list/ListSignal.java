package org.reactivebricks.pulses.list;

import java.util.List;

import org.reactivebricks.pulses.Pulse;

public interface ListSignal<T> {

	public Pulse<List<T>> currentList();
	public Pulse<Integer> currentSize();
	
	public void supply(ListChange<T> car);

}