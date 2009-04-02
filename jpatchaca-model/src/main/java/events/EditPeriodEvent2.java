package events;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public class EditPeriodEvent2 implements Serializable{

	private static final long serialVersionUID = 1L;
	private final ObjectIdentity taskId;
	private final int selectedPeriod;
	private final Date start;
	private final Date end;

	public EditPeriodEvent2(final ObjectIdentity taskId, final int selectedPeriod, final Date start, final Date end) {
		Validate.notNull(taskId);
		Validate.notNull(start);

		
		this.taskId = taskId;
		this.selectedPeriod = selectedPeriod;
		this.start = start;
		this.end = end;
	}

	public Date getStop() {
		return this.end;
	}

	public int getSelectedPeriod() {
		return this.selectedPeriod;
	}

	public Date getStart() {
		return this.start;
	}

	public ObjectIdentity getTaskId() {
		return this.taskId;
	}

	
}
