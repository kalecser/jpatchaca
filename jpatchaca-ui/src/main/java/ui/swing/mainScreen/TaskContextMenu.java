package ui.swing.mainScreen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import tasks.TaskView;
import basic.Alert;
import basic.AlertImpl;

@SuppressWarnings("serial")
public class TaskContextMenu extends JPopupMenu {

	private final AlertImpl createLabelAlert;
	private final AlertImpl assignToLabelAlert;
	private final AlertImpl removeFromLabelAlert;
	private final AlertImpl startCurrentTaskAlert;
	private final AlertImpl stopCurrentTaskAlert;
	private final AlertImpl removeTaskAlert;
	private final AlertImpl editTaskAlert;
	private final AlertImpl addNoteAlert;

	protected String selectedLabelName;
	private TaskContextMenuModel model;

	public Alert createLabelAlert() {
		return this.createLabelAlert;
	}

	public TaskContextMenu() {
		this.createLabelAlert = new AlertImpl();
		this.assignToLabelAlert = new AlertImpl();
		this.removeFromLabelAlert = new AlertImpl();
		this.startCurrentTaskAlert = new AlertImpl();
		this.stopCurrentTaskAlert = new AlertImpl();
		this.removeTaskAlert = new AlertImpl();
		this.editTaskAlert = new AlertImpl();
		this.addNoteAlert = new AlertImpl();

	}

	public void show(final Component invoker, final int x, final int y,
			final TaskView selectedTask) {

		removeAll();

		if (selectedTask.isActive())
			addStartTaskMenuItem();
		else
			addStopTaskMenuItem();

		addSeparator();
		addNoteItem();
		addEditTaskItem();
		addRemoveTaskItem();
		addSeparator();
		addLabelsMenu();
		addRemoveFromLabelMenu(selectedTask);
		addSeparator();
		addShowInBrowser(selectedTask);

		super.show(invoker, x, y);
	}

	private void addShowInBrowser(final TaskView selectedTask) {
		final JMenuItem item = new JMenuItem("open in browser");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.openInBrowser(selectedTask);
			}
		});
		add(item);
	}

	private void addNoteItem() {
		final JMenuItem item = new JMenuItem("add note");
		add(item);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireAddNoteAlert();
			}
		});
	}

	private void addEditTaskItem() {
		final JMenuItem item = new JMenuItem("edit");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireEditTaskAlert();
			}
		});
		add(item);

	}

	private void addRemoveTaskItem() {
		final JMenuItem item = new JMenuItem("remove");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireRemoveTaskAlert();
			}
		});
		add(item);
	}

	private void addStopTaskMenuItem() {
		final JMenuItem item = new JMenuItem("start");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireStartCurrentTaskAlert();
			}
		});
		add(item);
	}

	private void addStartTaskMenuItem() {
		final JMenuItem item = new JMenuItem("stop");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireStopCurrentTaskAlert();
			}
		});
		add(item);
	}

	private void addRemoveFromLabelMenu(final TaskView selectedTask) {
		final JMenu removeFromMenu = new JMenu("remove from...");
		for (final String label : model.getLabelsFor(selectedTask)) {
			final JMenuItem item = removeFromMenu.add(label);
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					fireRemoveFromLabelAlert(label);
				}
			});
		}

		add(removeFromMenu);
	}

	private void addLabelsMenu() {
		final JMenu labelsMenu = new JMenu("set label to...");

		for (final String label : model.assignableLabels()) {
			final JMenuItem item = labelsMenu.add(label);
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					fireAssignToLabelAlert(label);
				}
			});
		}

		labelsMenu.addSeparator();
		final JMenuItem createLabelMenuItem = labelsMenu.add("new label");
		createLabelMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireCreateLabelAlert();
			}
		});

		add(labelsMenu);
	}

	public final String selectedLabelName() {
		final String tempSelectedLabelName = this.selectedLabelName;
		this.selectedLabelName = null;
		return tempSelectedLabelName;
	}

	public Alert assignToLabelAlert() {
		return this.assignToLabelAlert;
	}

	public Alert removeFromLabelAlert() {
		return this.removeFromLabelAlert;
	}

	public final Alert startCurrentTaskAlert() {
		return this.startCurrentTaskAlert;
	}

	public final Alert stopCurrentTaskAlert() {
		return this.stopCurrentTaskAlert;
	}

	public final Alert removeTaskAlert() {
		return this.removeTaskAlert;
	}

	public final AlertImpl editTaskAlert() {
		return editTaskAlert;
	}

	public void setModel(final TaskContextMenuModel model) {
		this.model = model;
	}

	public void clickAddNoteToTask() throws InterruptedException,
			InvocationTargetException {
		for (final Component component : getComponents()) {
			if (component instanceof JMenuItem) {
				final JMenuItem item = (JMenuItem) component;
				if (item.getText().equals("add note")) {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							item.doClick();
						}
					});

					return;
				}
			}
		}

		throw new RuntimeException("Menu item " + "add note" + "not found.");
	}

	public final AlertImpl addNoteAlert() {
		return addNoteAlert;
	}

	void fireAddNoteAlert() {
		this.addNoteAlert.fire();
	}

	void fireEditTaskAlert() {
		this.editTaskAlert.fire();
	}

	void fireRemoveTaskAlert() {
		this.removeTaskAlert.fire();
	}

	void fireStartCurrentTaskAlert() {
		this.startCurrentTaskAlert.fire();
	}

	void fireStopCurrentTaskAlert() {
		this.stopCurrentTaskAlert.fire();
	}

	void fireRemoveFromLabelAlert(final String label) {
		this.selectedLabelName = label;
		this.removeFromLabelAlert.fire();
	}

	void fireAssignToLabelAlert(final String label) {
		this.selectedLabelName = label;
		this.assignToLabelAlert.fire();
	}

	void fireCreateLabelAlert() {
		this.createLabelAlert.fire();
	}
}
