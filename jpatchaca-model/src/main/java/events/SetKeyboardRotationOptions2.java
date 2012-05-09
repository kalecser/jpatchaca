package events;

import java.io.Serializable;

public class SetKeyboardRotationOptions2 implements Serializable {
	
	public final boolean supressDialogs;
	public final boolean remoteSystemIntegration;

	public SetKeyboardRotationOptions2(boolean supressDialogs,
			boolean remoteSystemIntegration) {
				this.supressDialogs = supressDialogs;
				this.remoteSystemIntegration = remoteSystemIntegration;
	
	}

	private static final long serialVersionUID = 1L;

}
