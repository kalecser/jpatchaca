package ui.cli.mock;

import org.junit.Assert;

import ui.commandLine.CommandLineInterface;
import ui.commandLine.CommandLineInterfaceImpl;

public class JPatchacaCommandLineSystemOperator {

	private final CommandLineInterface cli;
	private WorkLoggerMock mockWorkLogger;
	private String commandResponse;

	public JPatchacaCommandLineSystemOperator() {
		mockWorkLogger = new WorkLoggerMock();
		cli = new CommandLineInterfaceImpl(mockWorkLogger);		
	}
	
	public void sendCommandLine(String command) {
		commandResponse = cli.command(command);
	}

	public void assertWorklogSent() {
		Assert.assertTrue(mockWorkLogger.isWorkLogged());		
	}

	public void assertInvalidCommandMessageIsShown() {
		Assert.assertTrue(commandResponse.contains("Invalid command"));
	}

	public void assertCommandResponse(String response) {
		Assert.assertEquals(response, commandResponse);		
	}

	public void setWorklogSynchronizationToFail() {
		mockWorkLogger.setToFail();		
	}

}
