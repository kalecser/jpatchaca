package tasks.tasks.tests;

import tasks.tasks.taskName.TaskName;
import basic.NonEmptyString;

public class MockTaskName extends TaskName {

	public MockTaskName(final String taskName) {
		super(new NonEmptyString(taskName));
	}

}
