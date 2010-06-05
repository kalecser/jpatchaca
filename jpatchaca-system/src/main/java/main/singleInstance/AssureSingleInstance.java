package main.singleInstance;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.apache.commons.lang.UnhandledException;

public class AssureSingleInstance {

	private static final int PORT = 18123;
	public static ServerSocket socket;

	public static void registerAsRunning()
			throws AlreadyRunningApplicationException {
		try {
			final int backlog = 0;
			socket = new ServerSocket(PORT, backlog, InetAddress
					.getByAddress(new byte[] { 127, backlog, backlog, 1 }));
		} catch (final BindException e) {
			throw new AlreadyRunningApplicationException();
		} catch (final IOException e) {
			throw new UnhandledException(e);
		}
	}
}
