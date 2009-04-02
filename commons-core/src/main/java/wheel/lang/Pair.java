package wheel.lang;

import java.io.Serializable;

public class Pair<A, B> implements Serializable {

	public final A _a;
	public final B _b;

	public Pair(A a, B b) {
		_a = a;
		_b = b;
	}

	private static final long serialVersionUID = 1L;
}
