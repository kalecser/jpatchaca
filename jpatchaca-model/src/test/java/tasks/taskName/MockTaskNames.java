package tasks.taskName;

import java.util.ArrayList;
import java.util.List;

public class MockTaskNames implements TaskNames {

	private final List<String> names = new ArrayList<String>();;

	@Override
	public boolean containsName(final String name) {
		return names.contains(name);
	}

	public void add(final String name) {
		names.add(name);
	}

}
