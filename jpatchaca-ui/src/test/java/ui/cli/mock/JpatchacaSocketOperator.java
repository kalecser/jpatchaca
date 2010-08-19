package ui.cli.mock;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import basic.SocketUtils;

import ui.commandLine.PatchacaSocketServer;

public class JpatchacaSocketOperator {
	
	private final PatchacaSocketServer patchacaSocketServer;
	private Socket socket;


	public JpatchacaSocketOperator() throws UnknownHostException, IOException{
		patchacaSocketServer = new PatchacaSocketServer(new MockCLI());
		patchacaSocketServer.start();		
		connect();
	}

	public String sendCommand(String command) throws UnknownHostException, IOException {
		SocketUtils.writeLine(command, socket);
		return SocketUtils.readLine(socket);		
	}

	public void reconnect() throws IOException {
		socket.close();	
		connect();
	}

	private void connect() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", 48625 );
	}

	public void close() {
		patchacaSocketServer.stop();	
	}



}
