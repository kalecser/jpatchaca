package ui.cli.mock;

import org.junit.Assert;

import ui.commandLine.CommandLineInterface;
import ui.commandLine.CommandLineInterfaceImpl;

public class JPatchacaCommandLineSystemOperator {

	private final CommandLineInterface cli;
	private WorkLoggerMock mockWorkLogger;
	private String commandResponse;
	private ActiveTaskNameCopierMock mockCopier;

	public JPatchacaCommandLineSystemOperator() {
		mockWorkLogger = new WorkLoggerMock();
		mockCopier = new ActiveTaskNameCopierMock();
		cli = new CommandLineInterfaceImpl(mockWorkLogger, mockCopier);		
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

	public void assertTaskNameCopied() {
		Assert.assertTrue(mockCopier.hasCopiedTasknameToClipboard());
	}

}
