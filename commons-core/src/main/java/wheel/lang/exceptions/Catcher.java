package wheel.lang.exceptions;

public interface Catcher {

	/** This method is not simply called "catch" because "catch" is a reserved word. */
	void catchThis(Throwable throwable);

}
