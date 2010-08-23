package ui.cli.mock;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;

import basic.SocketUtils;

import ui.commandLine.PatchacaSocketServer;
import wheel.lang.Threads;

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
		
		long now = System.currentTimeMillis();
		
		long fiveSeconds = 5 * DateUtils.MILLIS_PER_SECOND;
		while (System.currentTimeMillis() - now < fiveSeconds){
			Threads.sleepWithoutInterruptions(100);
			try{
				socket = new Socket("127.0.0.1", 48625 );
				return;
			} catch (Exception ex){
				//do nothing
			}
		}
		
		Assert.fail("Failed to connect");
	}

	public void close() {
		patchacaSocketServer.stop();	
	}



}
