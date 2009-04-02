package org.reactivebricks.pulses;

public class Pulse<T> {

	private final int id;
	private final T value;

	public Pulse(int id, T value) {
		this.id = id;
		this.value = value;
	}

	public T value() {
		return value;
	}

	public int id() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pulse)) 
			return false;
			
		Pulse<?> other = (Pulse<?>) obj;
		if (this.id != other.id)
			return false;
		
		if (!this.value.equals(other.value))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return 42;
	}
	
	@Override
	public String toString() {
	
		return value.toString() + " at: " + id ;
	}
}
