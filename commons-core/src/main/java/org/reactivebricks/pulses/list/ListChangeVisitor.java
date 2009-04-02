package org.reactivebricks.pulses.list;

public interface ListChangeVisitor<T> {

	void onClearList();
	void onAddElement(T value);
	void onRemoveElement(int index);

}
