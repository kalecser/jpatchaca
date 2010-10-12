package ui.swing.mainScreen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import labels.LabelsSystem;

import org.picocontainer.Startable;

import tasks.TaskView;
import tasks.TasksListener;
import tasks.TasksSystem;
import tasks.tasks.Tasks;
import ui.swing.users.SwingTasksUser;
import ui.swing.utils.UIEventsExecutor;
import basic.Subscriber;
import core.ObjectIdentity;

public class TaskListSystemMediator implements Startable {

	private final TaskList list;
	private final LabelsList labelsList;
	private final LabelsSystem labelsSystem;

	public TaskListSystemMediator(final TaskList list,
			final LabelsList labelsList, final TasksSystem tasksSystem, final Tasks tasks, 
			final LabelsSystem labelsSystem,
			final SwingTasksUser tasksUser, final UIEventsExecutor executor) {
		this.list = list;
		this.labelsList = labelsList;
		this.labelsSystem = labelsSystem;

		tasksSystem.taskListChangedAlert().subscribe(new Subscriber() {

			public void fire() {
				updateTasks();
			}
		});

		this.labelsSystem.tasksInLabelChangedAlert().subscribe(
				new Subscriber() {

					public void fire() {
						updateTasks();
					}
				});

		this.labelsSystem.labelsListChangedAlert().subscribe(new Subscriber() {

			public void fire() {
				updateLabels();
				updateTasks();
			}
		});

		this.list.selectedLabelChanged().subscribe(new Subscriber() {

			public void fire() {
				updateTasks();
			}
		});

		this.list.movePeriodAlert().subscribe(new Subscriber() {

			public void fire() {
				final TaskView periodMovingTarget = tasksUser.getPeriodMovingTarget();
				
				String movePeriodData = list.getMovePerioData();
				
				Matcher matcher = getMatcher(movePeriodData);
				
				final String taskId = matcher.group(1);
				final Integer selectedPeriodIndex = Integer.valueOf(matcher.group(2));
				
				if (selectedPeriodIndex < 0){
					throwErrorBecauseTransferHandlerKillRuntimeExceptions();
				}
				
				executor.execute(new Runnable() {
					
					@Override
					public void run() {
						tasksSystem.movePeriod(tasks.get(new ObjectIdentity(taskId)),
								periodMovingTarget, selectedPeriodIndex);						
					}
				});
				
			}

			private Matcher getMatcher(String movePeriodData) {
				Pattern pattern = Pattern.compile("task: (\\w+) period: ([0-9]+)");
				Matcher matcher = pattern.matcher(movePeriodData);
				if (!matcher.matches()){
					throw new IllegalStateException(String.format("Unsupported move period data: %s", movePeriodData));
				}
				return matcher;
			}

			private void throwErrorBecauseTransferHandlerKillRuntimeExceptions()
					throws Error {
				throw new Error("Invalid selected period");
			}
		});

		tasksSystem.addTasksListener(new TasksListener() {

			@Override
			public void removedTask(final TaskView task) {
				//Ignored

			}

			@Override
			public void createdTask(final TaskView task) {
				list.setSelectedTask(task);
			}

		});

		updateLabels();
	}

	void updateLabels() {
		this.labelsList.setLabels(this.labelsSystem.labels());
	}

	void updateTasks() {
		final String selectedLabel = this.list.selectedLabel();
		if (selectedLabel == null) {
			return;
		}
		this.list.setTasks(this.labelsSystem.tasksInlabel(selectedLabel));
	}

	public void stop() {
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
}
