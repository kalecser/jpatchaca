package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class RenameTaskEvent implements Serializable{

	private static final long serialVersionUID = 7911653181699090250L;
	private final ObjectIdentity taskId;
	private final String newNameForTask;

	
	public RenameTaskEvent(final ObjectIdentity taskId, final String newNameForTask) {
		Validate.notNull(taskId);
		Validate.notNull(newNameForTask);
		
		this.taskId = taskId;
		this.newNameForTask = newNameForTask;		
	}

	public final String getNewNameForTask() {
		return this.newNameForTask;
	}

	public final ObjectIdentity getTaskId() {
		return this.taskId;
	}

}
