package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class RemoveTaskFromLabelEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final ObjectIdentity taskId;
	private final String labelName;

	public RemoveTaskFromLabelEvent(final ObjectIdentity taskId, final String labelName) {
		Validate.notNull(taskId);
		Validate.notNull(labelName);
		this.taskId = taskId;
		this.labelName = labelName;
	}

	public final String getLabelName() {
		return this.labelName;
	}

	public final ObjectIdentity getTaskId() {
		return this.taskId;
	}


}
