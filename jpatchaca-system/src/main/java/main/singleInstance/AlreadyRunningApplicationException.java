package main.singleInstance;

public class AlreadyRunningApplicationException extends Exception {

	public AlreadyRunningApplicationException() {
		super("This program is already running.");
	}
}
