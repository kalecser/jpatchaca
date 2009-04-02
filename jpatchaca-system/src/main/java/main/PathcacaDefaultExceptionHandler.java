package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

import org.picocontainer.Startable;

import wheel.io.files.Directory;

public class PathcacaDefaultExceptionHandler implements Startable {

	private final Directory directory;

	public PathcacaDefaultExceptionHandler(Directory directory){
		this.directory = directory;
		
	}
	
	@Override
	public void start() {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
		
			@Override
			public synchronized void uncaughtException(Thread t, Throwable e) {
				logExceptionToErrorLog(e);
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "An error has occured, see ~/.jpatchaca/error.log for further information. It's recomended to restart the application.");
			}

			private void logExceptionToErrorLog(Throwable e) {
				OutputStream log = null;
				try {
					log = directory.openFileForAppend("error.log");
				} catch (IOException e1) {
					e.printStackTrace();
					e1.printStackTrace();
				}
				
				try {					
					PrintWriter s = new PrintWriter(log);
					e.printStackTrace(s);
					s.flush();
				} finally {
					close(log);
				}
			}

			private void close(OutputStream log) {
				try {
					log.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});

	}

	@Override
	public void stop() {
	

	}

}
