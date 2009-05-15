package reactive;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

public class ListElementSignal<T> {

	private Source<Maybe<T>> output = new Source<Maybe<T>>(null);
	private Integer lastIndex = null;
	private Redirect<Maybe<T>> redirector = null;
	
	public ListElementSignal(Signal<Integer> index,
			final ListSourceElementsByIndex<T> signalByIndex) {
		
		index.addReceiver(new Receiver<Integer>() {
			@Override
			public void receive(Integer value) {
				
				if (lastIndex != null)
					signalByIndex.get(lastIndex).removeReceiver(redirector);
					
				redirector = new Redirect<Maybe<T>>(output);
				
				if (signalByIndex.get(value) == null)
					throw new IllegalStateException("SignalByIndex must not be null");
				
				signalByIndex.get(value).addReceiver(redirector);
				lastIndex = value;
			}
		});
		
	}

	public Signal<Maybe<T>> output() {
		return output;
	}

	
	
	
}
