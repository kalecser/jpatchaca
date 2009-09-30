package reactive;

public interface Functor<IN, OUT> {

	public OUT evaluate(IN value);
}
