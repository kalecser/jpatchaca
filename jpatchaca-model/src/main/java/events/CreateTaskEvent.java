package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class CreateTaskEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final ObjectIdentity identity;
	private final String taskName;
	
	public CreateTaskEvent(final ObjectIdentity identity, final String taskName) throws DeprecatedEvent {
		Validate.notNull(identity);
		Validate.notNull(taskName);
		
		this.identity = identity;
		this.taskName = taskName;

	}

	public final ObjectIdentity getIdentity() {
		return this.identity;
	}

	public final String getTaskName() {
		return this.taskName;
	}
	

}
