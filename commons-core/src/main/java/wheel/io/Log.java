package wheel.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

//Log
public class Log {

	static private PrintWriter _log = new PrintWriter(System.out);

	
	static public void log(String entry) {
		logHeader(entry);
		logSeparator();
		flush();
	}
	
	
	static public void log(Throwable throwable) {
		try {
			logHeader("");
			throwable.printStackTrace();
			throwable.printStackTrace(_log);
			logSeparator();
		} finally {
			flush();
		}
	}
	

	static public boolean hasError() {
		return _log.checkError();
	}


	private static void flush() {
		_log.flush();
	}
	
	
	private static void logHeader(String entry) {
		_log.println("" + new Date() + "  " + entry);
		System.out.println("" + new Date() + "  " + entry);
	}

	
	private static void logSeparator() {
		_log.println();
		_log.println();
		_log.flush();
	}

	
	private static void redirectTo(OutputStream outputStream) {
		_log = new PrintWriter(outputStream);
	}

	
	public static void redirectTo(File file) throws FileNotFoundException {
		redirectTo(new FileOutputStream(file, true));
	}

}
