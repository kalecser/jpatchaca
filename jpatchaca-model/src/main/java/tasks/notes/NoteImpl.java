package tasks.notes;

import java.util.Date;

class NoteImpl implements NoteView {

	private final Date time;
	private final String text;

	public NoteImpl(Date time, String text) {
		this.time = time;
		this.text = text;
	}

	@Override
	public String text() {
		return text;
	}

	@Override
	public Date timeStamp() {
		return time;
	}

}
