package ui.swing.mainScreen.periods;

import org.apache.commons.lang.Validate;
import org.reactive.Signal;

import reactive.Functor;

public class TableModelColumn<T> {

	private final String name;
	private final Functor<T, Signal<?>> functor;

	public TableModelColumn(final String name,
			final Functor<T, Signal<?>> functor) {
		Validate.notNull(name, "name");
		Validate.notNull(functor, "functor");

		this.name = name;
		this.functor = functor;
	}

	public String name() {
		return name;
	}

	public Functor<T, Signal<?>> getFunctor() {
		return functor;
	}

}
