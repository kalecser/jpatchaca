package events;

import java.io.Serializable;

public class SetKeyboardRotationOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final boolean supressShakingDialog;

	public SetKeyboardRotationOptions(boolean supressShakingDialog) {
		this.supressShakingDialog = supressShakingDialog;
	}

	public boolean supressShakingDialog() {
		return supressShakingDialog;
	}

}
