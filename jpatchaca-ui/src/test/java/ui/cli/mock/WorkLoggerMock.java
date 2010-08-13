package ui.cli.mock;

import ui.commandLine.WorkLogger;

public class WorkLoggerMock implements WorkLogger {

	private boolean workLogged = false;
	private boolean toFail = false;

	@Override
	public void logWork() {
		
		if (toFail){
			throw new RuntimeException("Any exception will do.");
		}
		
		workLogged = true;
	}

	public boolean isWorkLogged() {
		return workLogged;
	}

	public void setToFail() {
		toFail  = true;
	}

}
