package ui.swing.tasks;

import java.awt.BorderLayout;
import java.util.concurrent.Callable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.swing.presenter.ActionPane;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.PresenterImpl;
import ui.swing.presenter.UIAction;
import ui.swing.presenter.ValidationException;
import ui.swing.utils.SwingUtils;

import commons.swing.JAutoCompleteTextField;
import commons.swing.JAutoCompleteTextFieldModel;

public class StartTaskScreen implements ActionPane {
	private static final long serialVersionUID = 1L;

	private final JPanel panel;
	private final StartTaskScreenModel model;

	private JAutoCompleteTextField taskName;

	public StartTaskScreen(final StartTaskScreenModel model) {
		this.model = model;
		panel = new JPanel();
		initialize();
	}

	protected void initialize() {
		panel.add(new JLabel("Start task"), BorderLayout.WEST);
		final JAutoCompleteTextFieldModel taskNameModel = new JAutoCompleteTextFieldModel(
				model.taskNames());
		taskName = new JAutoCompleteTextField(30, taskNameModel);
		panel.add(taskName, BorderLayout.CENTER);
	}

	@Override
	public UIAction action() {
		return new UIAction() {
			@Override
			public void run() throws ValidationException {
				model.startTask(getTaskName());
			}
		};
	}

	protected String getTaskName() {
		return SwingUtils.getOrCry(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return taskName.getText();
			}
		});
	}

	@Override
	public JPanel getPanel() {
		taskName.setText("");
		return panel;
	}

	public static void main(final String[] args) {
		final Presenter presenter = new PresenterImpl(null);
		final StartTaskScreen screen = new StartTaskScreen(
				new MockStartTaskScreenModel());
		presenter.showOkCancelDialog(screen, "Start Task");
	}

}
