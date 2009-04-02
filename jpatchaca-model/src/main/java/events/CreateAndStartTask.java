package events;

import java.io.Serializable;

public class CreateAndStartTask implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final CreateTaskEvent3 createTaskEvent;
	private final long in;

	public CreateAndStartTask(CreateTaskEvent3 produceCreateTaskEvent, long in) {
		this.createTaskEvent = produceCreateTaskEvent;
		this.in = in;
	}
	
	public long in(){
		return in;
	}

	public final CreateTaskEvent3 getCreateTaskEvent() {
		return createTaskEvent;
	}
	
	
}
