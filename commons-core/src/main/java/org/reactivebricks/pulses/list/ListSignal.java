package org.reactivebricks.pulses.list;

import java.util.List;

public interface ListSignal<T> {

	public List<T> currentList();
	public Integer currentSize();
	
	public void supply(ListChange<T> car);

}