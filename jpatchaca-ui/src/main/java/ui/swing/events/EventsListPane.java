package ui.swing.events;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ui.swing.presenter.ActionPane;
import ui.swing.presenter.UIAction;
import ui.swing.presenter.ValidationException;

public class EventsListPane implements ActionPane {

	private final EventsListPaneModel model;

	public EventsListPane(final EventsListPaneModel model) {
		this.model = model;
	}

	@Override
	public UIAction action() {
		return new UIAction() {
			@Override
			public void run() throws ValidationException {
				// just close
			}
		};
	}

	@Override
	public JPanel getPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final JScrollPane comp = new JScrollPane(new JTextArea(model
				.getEventsAsString()));
		comp.setPreferredSize(new Dimension(650, 300));
		panel.add(comp, BorderLayout.CENTER);

		return panel;
	}

}
