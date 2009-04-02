package org.reactivebricks.pulses.list;

public class ClearList<T> implements ListChange<T> {

	@Override
	public void accept(ListChangeVisitor<T> visitor) {
		visitor.onClearList();		
	}

}
