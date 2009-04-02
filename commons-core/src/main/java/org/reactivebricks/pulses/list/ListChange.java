package org.reactivebricks.pulses.list;

public interface ListChange<T> {

	void accept(ListChangeVisitor<T> visitor);
}
