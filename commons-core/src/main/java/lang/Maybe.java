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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Maybe other = (Maybe) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	

}
