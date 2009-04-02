package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class EditTaskEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final ObjectIdentity taskId;
	private final String newNameForTask;
	private final Double newBudgetForTask;

	public EditTaskEvent(final ObjectIdentity taskId, final String newNameForTask, final Double newBudgetForTask) {
		Validate.notNull(taskId);
		Validate.notNull(newNameForTask);
		
		this.taskId = taskId;
		this.newNameForTask = newNameForTask;
		this.newBudgetForTask = newBudgetForTask;
	}	

	public final Double newBudgetForTask() {
		return this.newBudgetForTask;
	}

	public final String newNameForTask() {
		return this.newNameForTask;
	}

	public final ObjectIdentity taskId() {
		return this.taskId;
	}

}
