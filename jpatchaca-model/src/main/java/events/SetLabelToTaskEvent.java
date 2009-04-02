package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;

import core.ObjectIdentity;




public class SetLabelToTaskEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private final ObjectIdentity taskId;
	private final String labelName;

	public SetLabelToTaskEvent(final ObjectIdentity taskId, final String labelName) {
		Validate.notNull(taskId);
		Validate.notNull(labelName);
		
		this.taskId = taskId;
		this.labelName = labelName;
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return 42;
	}

	public final String labelName() {
		return this.labelName;
	}

	public final ObjectIdentity taskId() {
		return this.taskId;
	}
}
