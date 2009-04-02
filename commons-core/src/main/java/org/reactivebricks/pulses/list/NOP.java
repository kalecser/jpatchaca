package org.reactivebricks.pulses.list;

public class NOP<T> implements ListChange<T> {

	@Override
	public void accept(ListChangeVisitor<T> visitor) {
		// NOP
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof NOP); 
	}

}
