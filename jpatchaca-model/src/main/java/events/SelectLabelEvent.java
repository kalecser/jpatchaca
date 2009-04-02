package events;

import java.io.Serializable;

public class SelectLabelEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String selectedLabel;

	public SelectLabelEvent(String selectedLabel) {
		this.selectedLabel = selectedLabel;
	}

	public final String selectedLabel() {
		return selectedLabel;
	}

	
}
