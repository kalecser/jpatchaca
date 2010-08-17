package ui.cli;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.picocontainer.Startable;

import ui.commandLine.CommandLineInterface;

import basic.SocketUtils;

public class PatchacaSocketServer implements Startable{


	private final CommandLineInterface cli;
	private ServerSocket serverSocket;

	public PatchacaSocketServer(CommandLineInterface cli){
		this.cli = cli;
	}
	
	@Override
	public void start() {
		
		Thread thread = new Thread(){

			@Override
			public void run() {
				try {
					int port = 48625;
					serverSocket = new ServerSocket(port);
					Socket socket = serverSocket.accept();
					readCommandsWhileUniverseExists(socket);
				} catch (IOException e) {
					throw new RuntimeException("Error openind ServerSocket for PatchacaSocketServer", e);
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		
	}
	
	private void readCommandsWhileUniverseExists(Socket socket) throws IOException {
		final boolean universeExists = true;
		while(universeExists){
			try {
				final String command = SocketUtils.readLine(socket);
				SocketUtils.writeLine(cli.command(command), socket);				
			} catch (SocketException ex){
				socket = serverSocket.accept();
			}
		}
	}

	@Override
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			throw new IllegalStateException("Error closing PatchacaSocketServer", e);
		}
	}

	
}
