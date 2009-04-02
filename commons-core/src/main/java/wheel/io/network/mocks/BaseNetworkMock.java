package wheel.io.network.mocks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import wheel.io.network.ObjectServerSocket;
import wheel.io.network.ObjectSocket;
import wheel.lang.Threads;


public class BaseNetworkMock {
    protected final Map<Integer, ObjectServerSocket> _serverSocketByPort = new HashMap<Integer, ObjectServerSocket>();
    protected Permit _permit = new Permit();

    protected void crashIfNotLocal(String serverIpAddress) {
        if (!serverIpAddress.equals("localhost")) throw new IllegalArgumentException("Only localhost connections are supported by the NetworkMock.");
    }
    public void crash() {
        _permit.expire();
    }
    

    public void recover() {
        _permit = new Permit();
    }

    protected ObjectServerSocketMock findServer(int serverPort) {
        return (ObjectServerSocketMock) _serverSocketByPort.get(new Integer(serverPort));
    }
    
    protected ObjectSocket startClient(int serverPort) throws IOException {
        final ObjectServerSocketMock server = findServer(serverPort); 
        if (server == null) throw new IOException("No server is listening on this port.");
        try {
            return server.openClientSocket();
        } catch (final IOException e) {
            Threads.sleepWithoutInterruptions(5);
            return server.openClientSocket(); //TODO Eliminate this retry because client must try and reconnect anyway.
        }
    }

    protected ObjectServerSocket startServer(int serverPort) throws IOException {
        final ObjectServerSocketMock old = findServer(serverPort);
        if (old != null) throw new IOException("Port already in use.");

        final ObjectServerSocketMock result = new ObjectServerSocketMock(_permit);
        _serverSocketByPort.put(serverPort, result);
        
        return result;

    }
}
