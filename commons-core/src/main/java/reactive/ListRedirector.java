package reactive;

import java.util.LinkedHashMap;
import java.util.Map;

import lang.Maybe;

import org.reactive.Signal;

@SuppressWarnings("boxing")
public class ListRedirector<T> {

	Redirect<Integer> size = new Redirect<Integer>(0);
	Map<Integer, Redirect<Maybe<T>>> rows = new LinkedHashMap<Integer, Redirect<Maybe<T>>>();
	
	private ListSignal<T> sourceList = null;
	
	public synchronized void redirect(ListSignal<T> newSourceList) {
		
		if (sourceList != null){
			for (Map.Entry<Integer, Redirect<Maybe<T>>> row : rows.entrySet()){
				Integer index = row.getKey();
				Redirect<Maybe<T>> redirect = row.getValue();
				sourceList.get(index).removeReceiver(redirect);
				newSourceList.get(index).addReceiver(row.getValue());
			}
			sourceList.size().removeReceiver(size);
		}
			
		this.sourceList = newSourceList;
		newSourceList.size().addReceiver(size);
		
	}

	public synchronized Signal<Integer> size() {
		return size;
	}

	public synchronized int currentSize() {
		return size().currentValue();
	}

	public synchronized Signal<Maybe<T>> get(int index) {
		if (!rows.containsKey(index)){
			bindRow(index);
		}	
		
		return rows.get(index);
	}

	private void bindRow(int index) {
		rows.put(index, new Redirect<Maybe<T>>());

		if (sourceList != null)
			sourceList.get(index).addReceiver(rows.get(index));
	}

}
