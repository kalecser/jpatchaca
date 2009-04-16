package labels.tests;

import java.util.List;

import labels.LabelsSystem;
import main.TransientNonUIContainer;

import org.jmock.MockObjectTestCase;

import tasks.TasksSystem;
import tasks.delegates.CreateTaskDelegate;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.TasksView;
import basic.NonEmptyString;
import basic.Subscriber;
import basic.mock.MockIdProvider;
import core.ObjectIdentity;
import events.CreateTaskEvent;
import events.DeprecatedEvent;
import events.EventsSystem;

public class LabelsTest extends MockObjectTestCase {

	private LabelsSystem labelsSystem;
	private TasksSystem tasksSystem;
	private EventsSystem eventsSystem;
	private TasksView tasks;
	private CreateTaskDelegate createTaskDelegate;
	private MockIdProvider mockidProvider;
	

	@Override
	protected void setUp() throws Exception {
		
		final TransientNonUIContainer container = new TransientNonUIContainer();
		
		labelsSystem = container.getComponent(LabelsSystem.class);
		tasksSystem = container.getComponent(TasksSystem.class);
		createTaskDelegate = container.getComponent(CreateTaskDelegate.class);
		tasks = container.getComponent(TasksView.class);
		mockidProvider = container.getComponent(MockIdProvider.class);
		eventsSystem = container.getComponent(EventsSystem.class);
	}
	
	public void testLabelsContainsAll(){
		assertEquals(labelsSystem.allLabelName(), labelsSystem.labels().get(0));
	}
	
	public void testAssignLabelToTask(){
		final String firstLabelName = "test";
		
		final StringBuffer alertOut = new StringBuffer();
		labelsSystem.labelsListChangedAlert().subscribe(new Subscriber() {
			public void fire() {
				alertOut.append("changed");		
			}
		});
		
		final ObjectIdentity taskId = new ObjectIdentity("1");
		final TaskView task = createTask("task name", taskId.getId());
		labelsSystem.setNewLabelToTask(tasks.get(taskId), firstLabelName);
		assertEquals(task, labelsSystem.tasksInlabel(firstLabelName).get(0));
		assertEquals(1, labelsSystem.tasksInlabel(firstLabelName).size());
		assertEquals(1, labelsSystem.assignableLabels().size());
		assertEquals(firstLabelName, labelsSystem.assignableLabels().get(0));
		assertEquals("changedchangedchanged", alertOut.toString());
		
		
		final ObjectIdentity taskTwoId = new ObjectIdentity("2");
		
		final TaskView taskTwo = createTask("task name", taskTwoId.getId());
		assertEquals(firstLabelName, labelsSystem.assignableLabels().get(0));
		labelsSystem.setLabelToTask(tasks.get(taskTwoId), firstLabelName);
		assertEquals(taskTwo, labelsSystem.tasksInlabel(firstLabelName).get(1));
		assertEquals(2, labelsSystem.tasksInlabel(firstLabelName).size());
		
		final String secondLabelName = "test 2";
		labelsSystem.setNewLabelToTask(tasks.get(taskTwoId), secondLabelName);
		final List<String> taskTwoLabels = labelsSystem.getLabelsFor(taskTwo);
		assertEquals(firstLabelName, taskTwoLabels.get(0));
		assertEquals(secondLabelName, taskTwoLabels.get(1));
		
	}

	public void testRemoveLabelFromTask(){
		final String labelName = "test";
		final ObjectIdentity taskId = new ObjectIdentity("1");
		final TaskView task = createTask("task name", taskId.getId());
		
		labelsSystem.setNewLabelToTask(tasks.get(taskId), labelName);
		
		labelsSystem.removeLabelFromTask(task, labelName);
		assertEquals(0, labelsSystem.assignableLabels().size());
		
	}
	
	public void testCreatedTasksGoToAllLabel(){
		final TaskView task = createTask("task name", "1");
		assertEquals(task, labelsSystem.tasksInlabel(labelsSystem.allLabelName()).get(0));
	}
	
	public void testRemovedTasksAreRemovedFromLabels(){
		final TaskView task = createTask("task name", "1");
		labelsSystem.setLabelToTask(task, "label");
		
		tasksSystem.removeTask(task);
		
		assertEquals(0, labelsSystem.tasksInlabel(labelsSystem.allLabelName()).size());
		
		final boolean labelsSystemOnlyContainsAllLabel = 1 == labelsSystem.labels().size();
		assertTrue(labelsSystemOnlyContainsAllLabel);		
		
	}
	
	public void testOldCreatedTasksGoToAllLabel() throws DeprecatedEvent{
		final String taskName = "test";
		eventsSystem.writeEvent(new CreateTaskEvent(new ObjectIdentity("1"), taskName));
		assertEquals(taskName, labelsSystem.tasksInlabel(labelsSystem.allLabelName()).get(0).name());
	}
	
	
	private TaskView createTask(String taskName, String taskId) {	
		mockidProvider.setNextId(taskId);
		createTaskDelegate.createTask(new TaskData(new NonEmptyString(taskName), 0.0));
		return tasks.get(new ObjectIdentity(taskId));
	}	
	
}
