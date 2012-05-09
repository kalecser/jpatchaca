package main;

import keyboardRotation.PairProgrammingRemoteIntegrationGui;
import keyboardRotation.PairProgrammingRemoteIntegrationGuiCallback;

class PairProgrammingRemoteIntegrationGuiMock implements PairProgrammingRemoteIntegrationGui{

	@Override
	public void requestRemotePeer(
			PairProgrammingRemoteIntegrationGuiCallback callback) {
		throw new RuntimeException("Not implemented");
	}

}
