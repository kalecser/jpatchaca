package basic;

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

}
