package events;

import java.io.Serializable;

public class NewAndNoteworthyConsumed implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final int hash;

	public NewAndNoteworthyConsumed(int hash) {
		this.hash = hash;
	}

}
