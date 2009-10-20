package lang;

import org.apache.commons.lang.Validate;

public class NonEmptyString {

	private final String string;

	public NonEmptyString(String string){
		Validate.notNull(string);
		Validate.notEmpty(string);
		
		this.string = string;
	}
	
	public String unbox() {
		return string;
	}
	
	@Override
	public String toString() {
		return string;
	}

	@Override
	public int hashCode() {
		
		if (string == null)
			return 0;
		return string.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		NonEmptyString other = (NonEmptyString) obj;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string))
			return false;
		return true;
	}

}
