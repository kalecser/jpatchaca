package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class RemoveTaskEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final ObjectIdentity taskId;

	public RemoveTaskEvent(final ObjectIdentity taskId) {
		Validate.notNull(taskId);
		this.taskId = taskId;
	}

	public final ObjectIdentity getTaskId() {
		return this.taskId;
	}


}
