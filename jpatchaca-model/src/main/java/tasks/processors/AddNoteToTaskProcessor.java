package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import tasks.notes.NoteView;
import tasks.notes.NotesHome;
import events.AddNoteToTaskEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class AddNoteToTaskProcessor implements Processor<AddNoteToTaskEvent> {

	private final TasksHome tasksHome;
	private final NotesHome notesHome;

	public AddNoteToTaskProcessor(TasksHome tasksHome, NotesHome notesHome){
		this.tasksHome = tasksHome;
		this.notesHome = notesHome;
	}

	@Override
	public void execute(AddNoteToTaskEvent eventObj) throws MustBeCalledInsideATransaction {
		final NoteView note = notesHome.createNote(eventObj.getText());
		tasksHome.addNoteToTask(eventObj.getIdOfTask(), note);
	}
	
	@Override
	public Class<? extends Serializable> eventType() {
		return AddNoteToTaskEvent.class;
	}

}
