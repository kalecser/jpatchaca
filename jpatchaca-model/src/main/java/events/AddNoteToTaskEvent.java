package events;

import java.io.Serializable;

import core.ObjectIdentity;

public class AddNoteToTaskEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final ObjectIdentity idOfTask;
	private final String text;

	public AddNoteToTaskEvent(ObjectIdentity idOfTask, String text) {
		this.idOfTask = idOfTask;
		this.text = text;
	}

	public final ObjectIdentity getIdOfTask() {
		return idOfTask;
	}

	public final String getText() {
		return text;
	}
	

}
