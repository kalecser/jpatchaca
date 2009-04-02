package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;

public class CreateTaskEvent3 implements Serializable{

	private static final long serialVersionUID = 1L;
	private final ObjectIdentity objectIdentity;
	private final String taskName;
	private final Double budget;
	private final String labelName;
	
	
	public CreateTaskEvent3(final ObjectIdentity objectIdentity, final String taskName, final Double budget, final String labelName) {
		Validate.notNull(objectIdentity);
		Validate.notNull(taskName);
		
		this.objectIdentity = objectIdentity;
		this.taskName = taskName;
		this.budget = budget;
		this.labelName = labelName;
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

	public String getLabel() {
		return labelName;
	}
}
