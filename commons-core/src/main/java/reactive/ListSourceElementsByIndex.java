package reactive;

import java.util.LinkedHashMap;
import java.util.Map;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;


class ListSourceElementsByIndex<T> {

	public Map<Integer, Source<Maybe<T>>> sourceByIndex = new LinkedHashMap<Integer, Source<Maybe<T>>>();
	
	public synchronized Signal<Maybe<T>> get(Integer index) {
		
		if (!sourceByIndex.containsKey(index))
			sourceByIndex.put(index, new Source<Maybe<T>>(null));
		
		return sourceByIndex.get(index);
	}
	
	public synchronized int indexOf(T value){
		int i = 0;
		for (Map.Entry<Integer, Source<Maybe<T>>> entry : sourceByIndex.entrySet()){
			Maybe<T> entryValue = entry.getValue().currentValue();
			if ((entryValue != null) && entryValue.unbox().equals(value)){
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	public synchronized void supply(int index, T value){
		if (!sourceByIndex.containsKey(index))
			sourceByIndex.put(index, new Source<Maybe<T>>(null));
		
		Source<Maybe<T>> source = sourceByIndex.get(index);
		
		if (value == null){
			source.supply(null);
			return;
		}
		
		source.supply(Maybe.wrap(value));
	}

}
