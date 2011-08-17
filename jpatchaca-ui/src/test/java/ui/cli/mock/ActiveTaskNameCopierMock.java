package ui.cli.mock;

import ui.common.ActiveTaskNameCopier;

public class ActiveTaskNameCopierMock implements ActiveTaskNameCopier {

	boolean copied = false;
	@Override
	public void copyActiveTaskNameToClipboard() {
		copied = true;
	}

	public boolean hasCopiedTasknameToClipboard() {
		return copied;
	}

}
