package wheel.lang.exceptions;

/** The checked counterpart of java.lang.IllegalArgumentException
 * It is not called simply IllegalArgument because it is easy to confuse them and throw the unchecked IllegalArgumentException causing bugs.
 */
public class IllegalParameter extends Exception {

	public IllegalParameter(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
