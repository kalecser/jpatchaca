package wheel.lang.exceptions;

import java.io.PrintStream;

public class PrintStackTracer implements Catcher {

	public PrintStackTracer(PrintStream printStream) {
		_printStream = printStream;
	}

	public PrintStackTracer() {
		this(System.err);
	}

	private final PrintStream _printStream;

	public void catchThis(Throwable throwable) {
		throwable.printStackTrace(_printStream);
	}
	
}
