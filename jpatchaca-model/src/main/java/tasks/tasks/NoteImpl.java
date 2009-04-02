package tasks.tasks;

import java.util.Date;

class NoteImpl implements NoteView {

	private final Date time;
	private final String text;

	public NoteImpl(Date time, String text) {
		this.time = time;
		this.text = text;
	}

	public String text() {
		return text;
	}

	public Date timeStamp() {
		return time;
	}

}
