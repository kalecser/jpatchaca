package org.reactivebricks.pulses.list;

public class RemoveElement<T> implements ListChange<T> {

	private final int index;

	public RemoveElement(int index) {
		this.index = index;
	}

	@Override
	public void accept(ListChangeVisitor<T> visitor) {
		visitor.onRemoveElement(index);
	}

}
