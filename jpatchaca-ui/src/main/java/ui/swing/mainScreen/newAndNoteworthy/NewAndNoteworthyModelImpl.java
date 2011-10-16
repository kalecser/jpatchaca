package ui.swing.mainScreen.newAndNoteworthy;

import org.reactive.Signal;
import org.reactive.Source;

public class NewAndNoteworthyModelImpl implements NewAndNoteworthyModel {

	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return new Source<Boolean>(false);
	}

	@Override
	public void markNewAndNoteworthyAsRead() {
		throw new RuntimeException("Not implemented");
	}

}
