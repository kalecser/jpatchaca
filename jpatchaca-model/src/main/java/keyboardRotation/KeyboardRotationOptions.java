package keyboardRotation;

import events.persistence.MustBeCalledInsideATransaction;

public class KeyboardRotationOptions {

	private boolean supressDialogs = false;

	public void setSupressDialogs(boolean b) throws MustBeCalledInsideATransaction {
		this.supressDialogs = b;
	}
	
	public boolean supressDialogs(){
		return supressDialogs;
	}

}
