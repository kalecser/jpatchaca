package wheel.lang;

public interface Functor<A, B> {

	B evaluate(A value);
	
}
