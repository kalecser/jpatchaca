package main.singleInstance;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import lang.Maybe;

import org.apache.log4j.Logger;
import org.picocontainer.Startable;

import basic.AlertImpl;
import basic.Subscriber;

public class AssureSingleInstance implements Startable{

	private static final int PORT = 18123;
	
	private final  AlertImpl _tryedToCreateAnotherInstance;

	private Maybe<Thread> alertOnNewconnectionThread;
	
	public static Maybe<ServerSocket> socket;
	
	public AssureSingleInstance(){
		_tryedToCreateAnotherInstance = new AlertImpl();
	}
	
	private void registerAsRunning(){
		try {
			final int backlog = 0;
			socket = Maybe.wrap(new ServerSocket(PORT, backlog, InetAddress
					.getByAddress(new byte[] { 127, 0, 0 , 1 })));
			
			alertOnNewConnection();
		} catch (final Exception e) {
			//ok, will pass
			Logger.getLogger(AssureSingleInstance.class).error(e);
		}
	}

	private void alertOnNewConnection() {
		Thread thread = new Thread(){
			public void run() {
				while (true){
					try {
						if (socket == null){
							return;
						}
						
						socket.unbox().accept().close();
						if( this.isInterrupted()){
							return;
						}
						_tryedToCreateAnotherInstance.fire();
					} catch (IOException e) {
						//ignore and return;
						return;
					}
					
				}
			};
		};
		
		thread.start();
		alertOnNewconnectionThread = Maybe.wrap(thread);
		
	}
	
	public void subscribeTryedToCreateAnotherInstance(Subscriber subscriber){
		_tryedToCreateAnotherInstance.subscribe(subscriber);
	}

	@Override
	public void start() {
	
		if (isRunning()){
			throw new AlreadyRunningApplicationException();
		}
		
		registerAsRunning();
	
	}

	private boolean isRunning() {
		try {
			Socket socket = new Socket();	
			try{
				socket.connect(new InetSocketAddress("127.0.0.1", PORT));
			} finally {
				socket.close();
			}
			
		} catch (IOException e) {
			return false;
		}
		
		return true;
		
	}

	@Override
	public void stop() {
		try {
			if (alertOnNewconnectionThread != null){
				alertOnNewconnectionThread.unbox().interrupt();				
			}
			
			if (socket != null){
				socket.unbox().close();				
			}
		} catch (IOException e) {
			throw new IllegalStateException("Error closing " + AssureSingleInstance.class.getName() + " socket");
		}
	}
}
