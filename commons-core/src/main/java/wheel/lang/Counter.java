package wheel.lang;

public class Counter {

	private int _next = 0;

	public synchronized long next() {
		return _next++;
	};

}
