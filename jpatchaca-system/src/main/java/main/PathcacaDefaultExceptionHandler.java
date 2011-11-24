package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import jira.exception.JiraException;

import main.singleInstance.AlreadyRunningApplicationException;

import org.picocontainer.Startable;

import ui.swing.mainScreen.StatusBar;
import ui.swing.utils.PatchacaUncaughtExceptionHandler;
import wheel.io.files.Directory;

public class PathcacaDefaultExceptionHandler implements Startable,
		PatchacaUncaughtExceptionHandler {

	private final Directory directory;
	private final StatusBar statusBar;

	public PathcacaDefaultExceptionHandler(final Directory directory,
			final StatusBar statusBar) {
		this.directory = directory;
		this.statusBar = statusBar;
	}

	@Override
	public void start() {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void stop() {
		// Nothing special.
	}

	@Override
	public synchronized void uncaughtException(final Thread t, final Throwable e) {

		if (e instanceof AlreadyRunningApplicationException)
			System.exit(0);

		logExceptionToErrorLog(e);
		e.printStackTrace();
		if (e instanceof JiraException) {
			statusBar.setErrorMessage(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Jira Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane
				.showMessageDialog(
						null,
						"An error has occured, see ~/.jpatchaca/error.log for further information. It's recomended to restart the application.");
	}

	private void logExceptionToErrorLog(final Throwable e) {
		OutputStream log = null;
		try {
			log = directory.openFileForAppend("error.log");
		} catch (final IOException e1) {
			e.printStackTrace();
			e1.printStackTrace();
		}

		try {
			final PrintWriter s = new PrintWriter(log);
			e.printStackTrace(s);
			s.flush();
		} finally {
			if (log != null) {
				close(log);
			}
		}
	}

	private void close(final OutputStream log) {
		try {
			log.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
