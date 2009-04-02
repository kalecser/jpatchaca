package events;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class AddPeriodEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ObjectIdentity taskId;
	private final Date begin;
	private final Date end;
	
	public AddPeriodEvent(final ObjectIdentity taskId, final Date begin, final Date end) {
		Validate.notNull(taskId);
		Validate.notNull(begin);
		
		this.taskId = taskId;
		this.begin = begin;
		this.end = end;
		
	}

	public final Date getBegin() {
		return this.begin;
	}

	public final Date getEnd() {
		return this.end;
	}

	public final ObjectIdentity getTaskId() {
		return this.taskId;
	}
}
