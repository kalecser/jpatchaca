package reactive;

import org.reactive.Signal;

public interface ListSignal<T> {

	Signal<Integer> size();

	int currentSize();

	

}
