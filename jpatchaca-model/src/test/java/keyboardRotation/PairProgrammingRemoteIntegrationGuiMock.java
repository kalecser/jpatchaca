package keyboardRotation;

class PairProgrammingRemoteIntegrationGuiMock implements PairProgrammingRemoteIntegrationGui {

	private String remotePeer = null;

	public void setRemotePeer(String remotePeer) {
		this.remotePeer = remotePeer;
	}

	@Override
	public void requestRemotePeer(PairProgrammingRemoteIntegrationGuiCallback callback) {
		if (remotePeer == null) throw new IllegalStateException();
		callback.onRemotePeer(remotePeer);
	}

}
