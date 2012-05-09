package ui.swing.keyboardRotation;

import java.io.IOException;

import javax.swing.JOptionPane;

import wheel.io.files.Directory;

import basic.PatchacaDirectory;

import keyboardRotation.PairProgrammingRemoteIntegrationGui;
import keyboardRotation.PairProgrammingRemoteIntegrationGuiCallback;

public class PairProgrammingRemoteIntegrationGuiSwing implements PairProgrammingRemoteIntegrationGui{

	protected static final String FILE = "peer.txt";
	private String lastPeer;
	private final Directory dir;
	
	public PairProgrammingRemoteIntegrationGuiSwing(Directory dir){
		this.dir = dir;
		lastPeer = readLastValue();
	}

	@Override
	public void requestRemotePeer(
			PairProgrammingRemoteIntegrationGuiCallback callback) {
		String peer = JOptionPane.showInputDialog("Digite ip/hostname da maquina de seu pair", lastPeer);
		store(peer);
		callback.onRemotePeer(peer);
	}
	
	private void store(String lastPeer) {
		this.lastPeer = lastPeer;
		try {
			dir.deleteFile(FILE);
			dir.createFile(FILE, lastPeer);
		} catch (IOException ignored) {}
	}

	private String readLastValue() {
		try {
			return dir.contentsAsString(FILE);
		} catch (IOException ignored) { return "";}
	}

}
