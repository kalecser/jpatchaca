package tasks.notes;

import basic.SystemClock;

public class NotesHomeImpl implements NotesHome {

	private final SystemClock clock;

	public NotesHomeImpl(final SystemClock clock) {
		this.clock = clock;
	}

	@Override
	public NoteView createNote(final String text) {
		return new NoteImpl(clock.getDate(), text);
	}

}
