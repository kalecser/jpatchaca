package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class CreateTaskEvent2 extends Object implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ObjectIdentity objectIdentity;
	private final String taskName;
	private final Double budget;

	public CreateTaskEvent2(final ObjectIdentity objectIdentity, final String taskName, final Double budget) throws DeprecatedEvent {
		Validate.notNull(objectIdentity);
		Validate.notNull(taskName);
		
		this.objectIdentity = objectIdentity;
		this.taskName = taskName;
		this.budget = budget;
	}

	public final Double getBudget() {
		return this.budget;
	}

	public final ObjectIdentity getObjectIdentity() {
		return this.objectIdentity;
	}

	public final String getTaskName() {
		return this.taskName;
	}
	
	
}
