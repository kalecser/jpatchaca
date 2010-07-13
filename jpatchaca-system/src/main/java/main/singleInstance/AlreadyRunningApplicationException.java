package main.singleInstance;

public class AlreadyRunningApplicationException extends Exception {
	private static final long serialVersionUID = 1L;

	public AlreadyRunningApplicationException() {
		super("This program is already running.");
	}
}
