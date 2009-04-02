package org.reactivebricks.pulses.list;

public class AddElement<T> implements ListChange<T>{

	private final T value;
	
	public AddElement(T value){
		this.value = value;		
	}

	@Override
	public void accept(ListChangeVisitor<T> visitor) {
		visitor.onAddElement(value);		
	}

}
