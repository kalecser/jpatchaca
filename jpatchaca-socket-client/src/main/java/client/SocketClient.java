package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;

import basic.SocketUtils;

public class SocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		final String arguments = StringUtils.join(args," ");
		final Socket socket = new Socket("127.0.0.1", 48625);
		try {
			SocketUtils.writeLine(arguments, socket);
			System.out.println(SocketUtils.readLine(socket));			
		} finally {
			socket.close();			
			System.out.println("Connection closed");
		}
	}
	
}
