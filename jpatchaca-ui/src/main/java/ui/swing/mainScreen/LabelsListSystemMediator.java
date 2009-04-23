package ui.swing.mainScreen;

import labels.LabelsSystem;

import org.picocontainer.Startable;

import ui.swing.users.LabelsUser;
import ui.swing.users.SwingTasksUser;
import basic.Subscriber;

public class LabelsListSystemMediator implements Startable {

	public LabelsListSystemMediator(final LabelsSystem labelsSystem,
			final LabelsList list, final SwingTasksUser tasksUser,
			final LabelsUser labelsUser) {

		list.assignTaskToLabelAlert().subscribe(new Subscriber() {

			public void fire() {
				labelsSystem.setLabelToTask(tasksUser.getSelectedTask(),
						labelsUser.getLabelToAssignTaskTo());
			}
		});

	}

	public void stop() {
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
