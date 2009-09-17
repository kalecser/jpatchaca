/**
 * 
 */
package reactive;

import org.reactive.Receiver;
import org.reactive.Source;

final class Redirect<T> extends Source<T> implements Receiver<T>{
	

	public Redirect(){
		super(null);
	}
	
	public Redirect(T value) {
		super(value);
	}

	@Override
	public void receive(T value) {
		if (value == null){
			supply(null);
			return;
		}
		supply(value);
	}
}