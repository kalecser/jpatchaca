package ui.swing.tasks;

import java.util.Arrays;
import java.util.List;

public class MockStartTaskScreenModel implements StartTaskScreenModel {

	@Override
	public void startTask(String taskName) {

	}

	@Override
	public List<String> taskNames() {

		return Arrays.asList("foo", "fuz", "far");
	}

}
