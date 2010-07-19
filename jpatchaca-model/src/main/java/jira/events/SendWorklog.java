package jira.events;

import java.io.Serializable;

import core.ObjectIdentity;

public class SendWorklog implements Serializable {

	private static final long serialVersionUID = -7304981805143833855L;
	private final ObjectIdentity taskId;
	private final int periodIndex;

	public SendWorklog(final ObjectIdentity taskId, final int periodIndex) {
		this.taskId = taskId;
		this.periodIndex = periodIndex;
	}

	public ObjectIdentity getTaskId() {
		return taskId;
	}

	public int getPeriodIndex() {
		return periodIndex;
	}
}
