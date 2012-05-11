package tasks.tasks.tests;

import tasks.taskName.TaskName;
import basic.NonEmptyString;

public class MockTaskName extends TaskName {

	private final String taskName2;

	public MockTaskName(final String taskName) {
		super(new NonEmptyString(taskName));
		taskName2 = taskName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((taskName2 == null) ? 0 : taskName2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MockTaskName other = (MockTaskName) obj;
		if (taskName2 == null) {
			if (other.taskName2 != null)
				return false;
		} else if (!taskName2.equals(other.taskName2))
			return false;
		return true;
	}
	
	

}
