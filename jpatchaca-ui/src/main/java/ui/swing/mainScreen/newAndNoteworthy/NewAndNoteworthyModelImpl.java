package ui.swing.mainScreen.newAndNoteworthy;

import newAndNoteworthy.NewAndNoteworthy;

import org.reactive.Signal;

public class NewAndNoteworthyModelImpl implements NewAndNoteworthyModel {

	private final NewAndNoteworthyPresenter newAndNoteworthyPresenter;
	private final NewAndNoteworthy newAndNoteworthy;

	public NewAndNoteworthyModelImpl(NewAndNoteworthy newAndNoteworthy, NewAndNoteworthyPresenter newAndNoteworthyPresenter){
		this.newAndNoteworthy = newAndNoteworthy;
		this.newAndNoteworthyPresenter = newAndNoteworthyPresenter;
	}
	
	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return newAndNoteworthy.hasUnreadNewAndNoteworthy();
	}

	@Override
	public void openNewAndNoteworthyScreen() {
		newAndNoteworthyPresenter.show();
	}

}
