package wheel.lang;

import java.io.Serializable;

public class Id implements Serializable {

	private final long _id;

	public Id(long id) {
		_id = id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) return true;
		if (!(other instanceof Id)) return false;
		return ((Id)other)._id == _id;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(_id).hashCode(); //Optimize Do not create the Long. Just copy the logic from there.
	}

	private static final long serialVersionUID = 1L;
	
}
