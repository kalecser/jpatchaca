package wheel.lang.exceptions;

public class UnexpectedThrowCatcher implements Catcher {

	public void catchThis(Throwable t) {
		throw new RuntimeException("Unexpected " + t.getClass() + ": " + t.getMessage(), t);
	}

}
