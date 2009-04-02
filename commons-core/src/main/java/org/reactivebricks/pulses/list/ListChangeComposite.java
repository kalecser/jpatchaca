package org.reactivebricks.pulses.list;

public class ListChangeComposite<T> implements ListChange<T> {

	private final ListChange<T> car;
	private final ListChange<T> ret;

	public ListChangeComposite(ListChange<T> car, ListChange<T> ret) {
		this.car = car;
		this.ret = ret;		
	}

	@Override
	public void accept(ListChangeVisitor<T> visitor) {
		ret.accept(visitor);
		car.accept(visitor);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ListChangeComposite)) 
			return false;
			
		ListChangeComposite<?> other = (ListChangeComposite<?>) obj;
		if (this.car != other.car)
			return false;
		
		if (this.ret != other.ret)
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {		
		return 42;
	}

}
