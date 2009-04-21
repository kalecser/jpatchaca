package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;

public class StartTaskEvent implements Serializable {

	private final ObjectIdentity taskId;
	private static final long serialVersionUID = 1L;

	@Deprecated
	public StartTaskEvent(final ObjectIdentity taskId) {
		Validate.notNull(taskId);
		this.taskId = taskId;
	}

	public final ObjectIdentity getTaskId() {
		return this.taskId;
	}

}
