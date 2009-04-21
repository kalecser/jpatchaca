package ui.swing.mainScreen;

import java.util.LinkedHashSet;
import java.util.Set;

public class Delegate<T> {

	private final Set<Listener<T>> listeners = new LinkedHashSet<Listener<T>>();

	public interface Listener<T> {
		public void execute(T object);
	}

	public void addListener(final Listener<T> listener) {
		listeners.add(listener);
	}

	public void removeListener(final Listener<T> listener) {
		listeners.remove(listener);
	}

	protected void execute(final T object) {
		for (final Listener<T> listener : listeners) {
			listener.execute(object);
		}
	}

}
