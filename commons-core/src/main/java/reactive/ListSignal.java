package reactive;

import lang.Maybe;

import org.reactive.Signal;

public interface ListSignal<T> {

	public Signal<Integer> size();

	public void add(T value);

	public Signal<Maybe<T>> get(Signal<Integer> source);

	public Maybe<T> currentGet(int i);

	public void remove(int i);

	public int currentSize();

	public void remove(T value);

}