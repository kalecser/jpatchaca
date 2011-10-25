package reactive;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;

@SuppressWarnings("boxing")
public class ListSource<T> implements ListSignal<T> {

	
	private Source<Integer> sizeSource = new Source<Integer>(0); 
	ListSourceElementsByIndex<T> signalByIndex = new ListSourceElementsByIndex<T>();

	@Override
	public synchronized Signal<Integer> size() {
		return sizeSource;
	}

	@Override
	public synchronized void add(T value) {
		
		if (value == null)
			throw new IllegalArgumentException("value must not be null");
		
		int size = sizeSource.currentValue().intValue() + 1;
		int index = size - 1;

		signalByIndex.supply(index, value);
		
		sizeSource.supply(size);
	}
	
	public synchronized void add(int index, T value) {
		
		if (value == null)
			throw new IllegalArgumentException("value must not be null");
		
		if (index >= size().currentValue()){
			add(value);
			return;
		}
			
		
		signalByIndex.supply(index, value);
		
	}

	@Override
	public synchronized Signal<Maybe<T>> get(int index) {
		
		return signalByIndex.get(index);

	}

	@Override
	public synchronized Maybe<T> currentGet(int i) {
		Signal<Maybe<T>> value = signalByIndex.get(i);
		return value.currentValue();
	}

	@Override
	public synchronized void remove(int index) {
		
		for (int i = index; i < currentSize() - 1; i++){
			signalByIndex.supply(i, currentGet(i + 1).unbox());				
		}
		
		
		sizeSource.supply(sizeSource.currentValue() - 1);
	}

	@Override
	public synchronized int currentSize() {
		return sizeSource.currentValue();
	}

	@Override
	public synchronized void remove(T value) {
		remove(signalByIndex.indexOf(value));
	}

	public synchronized void clear() {
		for (int i = 0; i < size().currentValue(); i++) {
			signalByIndex.supply(i, null);
		}
		sizeSource.supply(0);
	}
}
