package ui.swing.mainScreen.newAndNoteworthy;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import newAndNoteworthy.NewAndNoteworthy;
import ui.swing.presenter.Presenter;

public class NewAndNoteworthyPresenter {
	
	private final Presenter presenter;
	private final NewAndNoteworthy newAndNoteworthy;

	public NewAndNoteworthyPresenter(Presenter presenter, NewAndNoteworthy newAndNoteworthy){
		this.presenter = presenter;
		this.newAndNoteworthy = newAndNoteworthy;
	}

	public void show() {
		JPanel newAndNoteworthyPanel = new JPanel();
		newAndNoteworthyPanel.setPreferredSize(new Dimension(640, 480));
		newAndNoteworthyPanel.setLayout(new BorderLayout());
		JTextArea newAndNoteworthyField = new JTextArea();
		newAndNoteworthyField.setText(newAndNoteworthy.getTextAndMarkAsRead());
		newAndNoteworthyPanel.add(new JScrollPane(newAndNoteworthyField), BorderLayout.CENTER);
		presenter.showPlainDialog(newAndNoteworthyPanel,"New and noteworthy");
	}

}
