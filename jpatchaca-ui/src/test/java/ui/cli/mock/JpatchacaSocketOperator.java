package ui.cli.mock;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import basic.SocketUtils;

import ui.cli.PatchacaSocketServer;

public class JpatchacaSocketOperator {
	
	private final PatchacaSocketServer patchacaSocketServer;
	private final Socket socket;


	public JpatchacaSocketOperator() throws UnknownHostException, IOException{
		patchacaSocketServer = new PatchacaSocketServer(new MockCLI());
		patchacaSocketServer.start();		
		socket = new Socket("127.0.0.1", 48625 );
	}


	public String sendCommand(String command) throws UnknownHostException, IOException {
		SocketUtils.writeLine(command, socket);
		return SocketUtils.readLine(socket);		
	}

}
