package tasks.tasks;

import basic.BasicSystem;

public class NotesHomeImpl implements NotesHome {

	private final BasicSystem basicSystem;

	public NotesHomeImpl(BasicSystem basicSystem) {
		this.basicSystem = basicSystem;
	}

	public NoteView createNote(String text) {
		return new NoteImpl(basicSystem.getHardwareTime(), text);
	}

}
