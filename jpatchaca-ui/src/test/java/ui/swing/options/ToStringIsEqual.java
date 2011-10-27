/*
 * Created on 2011-10-27
 */
package ui.swing.options;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public final class ToStringIsEqual<T> extends BaseMatcher<T> {

	public static <T> ToStringIsEqual<T> toStringEqualTo(final T out) {
		return new ToStringIsEqual<T>(out);
	}
	
	public ToStringIsEqual(T equalArg) {
		object = equalArg;
	}

	@Override
	public boolean matches(Object arg) {
        return String.valueOf(arg).equals(String.valueOf(object)); 
	}

	@Override
	public void describeTo(Description description) {
		description.appendValue(object);
	}
	
    private final Object object;
    
}