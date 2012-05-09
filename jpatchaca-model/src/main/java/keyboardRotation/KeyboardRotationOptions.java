package keyboardRotation;

import events.persistence.MustBeCalledInsideATransaction;

public class KeyboardRotationOptions {

	private boolean supressDialogs = false;
	private boolean remoteIntegrationActive = false;

	public void setSupressShakingDialog(boolean b) throws MustBeCalledInsideATransaction {
		this.supressDialogs = b;
	}
	
	public void setRemoteIntegration(boolean b) throws MustBeCalledInsideATransaction {
		this.remoteIntegrationActive = b;
	}
	
	public boolean supressShakingDialog(){
		return supressDialogs;
	}

	public boolean isRemoteIntegrationActive() {
		return remoteIntegrationActive;
	}

}
