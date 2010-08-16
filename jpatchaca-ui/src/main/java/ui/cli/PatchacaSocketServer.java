package ui.cli;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.picocontainer.Startable;

import ui.commandLine.CommandLineInterface;

import basic.SocketUtils;

public class PatchacaSocketServer implements Startable{


	private final CommandLineInterface cli;

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
					final ServerSocket serverSocket = new ServerSocket(port);
					Socket socket = serverSocket.accept();
					final String command = SocketUtils.readLine(socket);
					SocketUtils.writeLine(cli.command(command), socket);
				} catch (IOException e) {
					throw new RuntimeException("Error openind ServerSocket for PatchacaSocketServer", e);
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		
	}

	@Override
	public void stop() {}

	
}
