package events;

import java.io.Serializable;

import basic.NonEmptyString;

public class StartTaskEvent2 implements Serializable {
	private static final long serialVersionUID = 1L;
	private final long millisecondsAgo;
	private final String taskName;

	public StartTaskEvent2(final NonEmptyString taskName,
			final long millisecondsAgo) {

		this.taskName = taskName.unbox();
		this.millisecondsAgo = millisecondsAgo;
	}

	public static final long getSerialVersionUID() {
		return serialVersionUID;
	}

	public final long getMillisecondsAgo() {
		return millisecondsAgo;
	}

	public NonEmptyString getName() {
		return new NonEmptyString(taskName);
	}

}
