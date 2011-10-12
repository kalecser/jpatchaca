package events;

import java.io.Serializable;

public class SetKeyboardRotationOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final boolean supressDialogs;

	public SetKeyboardRotationOptions(boolean supressDialogs) {
		this.supressDialogs = supressDialogs;
	}

	public boolean supressDialogs() {
		return supressDialogs;
	}

}
