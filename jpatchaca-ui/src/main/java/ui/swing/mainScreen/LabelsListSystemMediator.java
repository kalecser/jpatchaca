package ui.swing.mainScreen;

import labels.LabelsSystem;

import org.picocontainer.Startable;

import ui.swing.tasks.SelectedTaskSource;
import ui.swing.users.LabelsUser;
import basic.Subscriber;

public class LabelsListSystemMediator implements Startable {

	public LabelsListSystemMediator(final LabelsSystem labelsSystem,
			final LabelsList list, final SelectedTaskSource selectedTask,
			final LabelsUser labelsUser) {

		list.assignTaskToLabelAlert().subscribe(new Subscriber() {

			@Override
			public void fire() {
				labelsSystem.setLabelToTask(selectedTask.currentValue(),
						labelsUser.getLabelToAssignTaskTo());
			}
		});

	}

	@Override
	public void stop() {
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
