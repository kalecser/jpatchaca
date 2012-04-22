package ui.commandLine;

import junit.framework.Assert;

import org.junit.Test;

public class HttpFilterCommandLIneInterfaceTest {

	@Test
	public void onHttpCommand_FilterHttpNoise(){
		String httpCommand = "GET /startTask%20%5BBRUNDLE-1632%5D HTTP/1.1";
		CommandLineInterfaceMock delegate = new CommandLineInterfaceMock();
		CommandLineInterface subject = new HttpFilterCommandLineInterface(delegate) ;
		subject.command(httpCommand);
		Assert.assertEquals("startTask [BRUNDLE-1632]", delegate.getCommand());
	}
	
}
