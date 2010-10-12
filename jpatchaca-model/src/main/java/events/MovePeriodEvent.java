package events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import core.ObjectIdentity;




public final class MovePeriodEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ObjectIdentity selectedTask;
	private final int selectedPeriod;
	private final ObjectIdentity targetTask;
	
	public MovePeriodEvent(final ObjectIdentity selectedTask, final int selectedPeriod, final ObjectIdentity movePeriodDestinationTask) {
		Validate.notNull(selectedTask);
		Validate.notNull(selectedPeriod);
		Validate.notNull(movePeriodDestinationTask);
		
		Validate.isTrue(selectedPeriod > -1);
		
		this.selectedTask = selectedTask;
		this.selectedPeriod = selectedPeriod;
		this.targetTask = movePeriodDestinationTask;
	}

	public final ObjectIdentity getTargetTask() {
		return targetTask;
	}

	public final int getSelectedPeriod() {
		return selectedPeriod;
	}

	public final ObjectIdentity getSelectedTask() {
		return selectedTask;
	}



}
