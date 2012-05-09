package keyboardRotation;

import java.io.OutputStream;
import java.net.Socket;

import net.PatchacaConstants;

class NetworkImpl implements Network {

	@Override
	public void sendTo(final String command, final String peer) {
		new Thread("Remote command"){ @Override public void run() {
				sendCommand(command, peer);
			} }.start();
	}

	private void sendCommand(final String command, final String peer) {
		try {
			Socket socket = new Socket(peer, PatchacaConstants.SERVER_PORT);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(command.getBytes());
			outputStream.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
