package keyboardRotation;

import events.persistence.MustBeCalledInsideATransaction;

public class KeyboardRotationOptions {

	private boolean supressDialogs = false;

	public void setSupressShakingDialog(boolean b) throws MustBeCalledInsideATransaction {
		this.supressDialogs = b;
	}
	
	public boolean supressShakingDialog(){
		return supressDialogs;
	}

}
