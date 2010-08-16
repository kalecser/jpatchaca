package ui.cli;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ui.cli.mock.JpatchacaSocketOperator;

public class SocketCommandLineTest {

	JpatchacaSocketOperator op;
	
	@Before
	public void setup() throws UnknownHostException, IOException{
		op = new JpatchacaSocketOperator();
	}
	
	@Ignore
	@Test(timeout=1000)
	public void testSendCommands() throws UnknownHostException, IOException{
		String commandResult = op.sendCommand("anyCommandWillDo");
		Assert.assertEquals("Command anyCommandWillDo received", commandResult);
		
		String secondCommandResult = op.sendCommand("anotherCommand");
		Assert.assertEquals("Command anotherCommand received", secondCommandResult);
	}
	
	
}
