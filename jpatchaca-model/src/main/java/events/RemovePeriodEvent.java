package events;

import java.io.Serializable;

import core.ObjectIdentity;

public class RemovePeriodEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final ObjectIdentity taskId;
	private final int periodIndex;

	public RemovePeriodEvent(ObjectIdentity taskId, int periodIndex){
		this.taskId = taskId;
		this.periodIndex = periodIndex;
	}

	public final int getPeriodIndex() {
		return periodIndex;
	}

	public final ObjectIdentity getTaskId() {
		return taskId;
	}
	
	
}
