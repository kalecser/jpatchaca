package tasks.tasks;

import events.persistence.MustBeCalledInsideATransaction;


public interface NotesHome {

	NoteView createNote(String text) throws MustBeCalledInsideATransaction;

}
