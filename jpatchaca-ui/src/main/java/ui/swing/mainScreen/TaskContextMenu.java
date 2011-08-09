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

		super.show(invoker, x, y);
	}

	private void addNoteItem() {
		final JMenuItem item = new JMenuItem("add note");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TaskContextMenu.this.addNoteAlert.fire();
			}
		});
		add(item);
	}

	private void addEditTaskItem() {
		final JMenuItem item = new JMenuItem("edit");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TaskContextMenu.this.editTaskAlert.fire();
			}
		});
		add(item);

	}

	private void addRemoveTaskItem() {
		final JMenuItem item = new JMenuItem("remove");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TaskContextMenu.this.removeTaskAlert.fire();
			}
		});
		add(item);
	}

	private void addStopTaskMenuItem() {
		final JMenuItem item = new JMenuItem("start");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TaskContextMenu.this.startCurrentTaskAlert.fire();
			}
		});
		add(item);
	}

	private void addStartTaskMenuItem() {
		final JMenuItem item = new JMenuItem("stop");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TaskContextMenu.this.stopCurrentTaskAlert.fire();
			}
		});
		add(item);
	}

	private void addRemoveFromLabelMenu(final TaskView selectedTask) {
		final JMenu removeFromMenu = new JMenu("remove from...");
		for (final String label : model.getLabelsFor(selectedTask)) {
			final JMenuItem item = removeFromMenu.add(label);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					TaskContextMenu.this.selectedLabelName = label;
					TaskContextMenu.this.removeFromLabelAlert.fire();
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
				public void actionPerformed(final ActionEvent e) {
					TaskContextMenu.this.selectedLabelName = label;
					TaskContextMenu.this.assignToLabelAlert.fire();
				}
			});
		}

		labelsMenu.addSeparator();
		final JMenuItem createLabelMenuItem = labelsMenu.add("new label");
		createLabelMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TaskContextMenu.this.createLabelAlert.fire();
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
}
