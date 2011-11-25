//Copyright (C) 2004 Klaus Wuestefeld
//This is free software. It is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the license distributed along with this file for more details.
//Contributions: Alexandre Nodari.

package wheel.io.network.mocks;

import java.io.IOException;

import wheel.io.network.ObjectServerSocket;
import wheel.io.network.ObjectSocket;
import wheel.io.network.OldNetwork;


public class OldNetworkMock extends BaseNetworkMock 
                         implements OldNetwork {

	
	@Override
	public synchronized ObjectSocket openSocket(String serverIpAddress, int serverPort) throws IOException {
	    crashIfNotLocal(serverIpAddress);
        return startClient(serverPort);
		
	}

	@Override
	public synchronized ObjectServerSocket openObjectServerSocket(int serverPort) throws IOException {
	    return startServer(serverPort);
	}
}
