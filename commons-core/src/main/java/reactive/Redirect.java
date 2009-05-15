/**
 * 
 */
package reactive;

import org.reactive.Receiver;
import org.reactive.Source;

final class Redirect<T> implements Receiver<T> {
	
	private final Source<T> target;

	public Redirect(Source<T> target){
		this.target = target;
	}
	
	@Override
	public void receive(T value) {
		if (value == null){
			target.supply(null);
			return;
		}
		target.supply(value);
	}
}