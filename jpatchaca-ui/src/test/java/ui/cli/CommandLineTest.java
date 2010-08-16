package ui.cli;

import org.junit.Test;

import ui.cli.mock.JPatchacaCommandLineSystemOperator;

public class CommandLineTest {

	JPatchacaCommandLineSystemOperator operator = new JPatchacaCommandLineSystemOperator();
	
	@Test
	public void testLogWork(){	
		operator.sendCommandLine("sendWorklog");
		operator.assertWorklogSent();
		operator.assertCommandResponse("Worklog sent");
	}
	
	@Test
	public void testInvalidCommand(){	
		operator.sendCommandLine("invalidCommand");
		operator.assertInvalidCommandMessageIsShown();
	}
	
	@Test
	public void testLogWorkWillFail(){	
		operator.setWorklogSynchronizationToFail();
		operator.sendCommandLine("sendWorklog");
		operator.assertCommandResponse("Worklog not sent. Error: Any exception will do.");
	}	
}
