package ui.swing.mainScreen;

import java.util.LinkedHashSet;
import java.util.Set;

public class Delegate<T> {

	private Set<Listener<T>> listeners = new LinkedHashSet<Listener<T>>();
	
	public interface Listener<T> {
		public void execute(T object);
	}


	public void addListener(Listener<T> listener){
		listeners.add(listener);
	}
	
	public void removeListener(Listener<T> listener) {
		listeners.remove(listener);		
	}
	
	protected void execute(T object){
		for (Listener<T> listener : listeners)
			listener.execute(object);
	}
	
}
