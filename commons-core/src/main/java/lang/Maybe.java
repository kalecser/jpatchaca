package lang;




public class Maybe<T> {

	private final T value;

	private Maybe(T value) {
		if (value == null)
			throw new IllegalArgumentException();
		this.value = value;
	}

	public T unbox() {
		return value;
	}

	public static <T> Maybe<T> wrap(T object) {
		if (object == null)
			return null;
		
		return new Maybe<T>(object);
	}
	
	@Override
	public String toString() {
		return "" + value;
	}

}
