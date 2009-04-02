package labels.tests;

import java.util.List;

import labels.LabelsSystem;
import labels.LabelsSystemImpl;

import org.jmock.MockObjectTestCase;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import periods.impl.PeriodsFactoryImpl;

import tasks.TasksSystem;
import tasks.TasksSystemImpl;
import tasks.delegates.StartTaskDelegate;
import tasks.persistence.StartTaskPersistence;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.TasksHomeImpl;
import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.Subscriber;
import basic.mock.MockBasicSystem;
import core.ObjectIdentity;
import events.CreateTaskEvent;
import events.DeprecatedEvent;
import events.EventsSystem;
import events.EventsSystemImpl;

public class LabelsTest extends MockObjectTestCase {

	private LabelsSystem labelsSystem;
	private TasksSystem tasksSystem;
	private MockBasicSystem basicSystem;
	private EventsSystem eventsSystem;
	

	@Override
	protected void setUp() throws Exception {
		
		final MutablePicoContainer container = new PicoBuilder()
			.withCaching()
			.withHiddenImplementations()
			.withLifecycle()
		.build();
		
		container.addComponent(TransientDirectory.class);
		container.addComponent(MockBasicSystem.class);
		container.addComponent(EventsSystemImpl.class);
		container.addComponent(TasksSystemImpl.class);
		container.addComponent(TasksHomeImpl.class);
		container.addComponent(StartTaskDelegate.class);
		container.addComponent(StartTaskPersistence.class);
		container.addComponent(LabelsSystemImpl.class);
		container.addComponent(PeriodsFactoryImpl.class);
		
		container.start();
		
		labelsSystem = container.getComponent(LabelsSystem.class);
		tasksSystem = container.getComponent(TasksSystem.class);
		basicSystem = container.getComponent(MockBasicSystem.class);
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
		labelsSystem.setNewLabelToTask(tasksSystem.getTaskView(taskId), firstLabelName);
		assertEquals(task, labelsSystem.tasksInlabel(firstLabelName).get(0));
		assertEquals(1, labelsSystem.tasksInlabel(firstLabelName).size());
		assertEquals(1, labelsSystem.assignableLabels().size());
		assertEquals(firstLabelName, labelsSystem.assignableLabels().get(0));
		assertEquals("changedchangedchanged", alertOut.toString());
		
		
		final ObjectIdentity taskTwoId = new ObjectIdentity("2");
		
		final TaskView taskTwo = createTask("task name", taskTwoId.getId());
		assertEquals(firstLabelName, labelsSystem.assignableLabels().get(0));
		labelsSystem.setLabelToTask(tasksSystem.getTaskView(taskTwoId), firstLabelName);
		assertEquals(taskTwo, labelsSystem.tasksInlabel(firstLabelName).get(1));
		assertEquals(2, labelsSystem.tasksInlabel(firstLabelName).size());
		
		final String secondLabelName = "test 2";
		labelsSystem.setNewLabelToTask(tasksSystem.getTaskView(taskTwoId), secondLabelName);
		final List<String> taskTwoLabels = labelsSystem.getLabelsFor(taskTwo);
		assertEquals(firstLabelName, taskTwoLabels.get(0));
		assertEquals(secondLabelName, taskTwoLabels.get(1));
		
	}

	public void testRemoveLabelFromTask(){
		final String labelName = "test";
		final ObjectIdentity taskId = new ObjectIdentity("1");
		final TaskView task = createTask("task name", taskId.getId());
		
		labelsSystem.setNewLabelToTask(tasksSystem.getTaskView(taskId), labelName);
		
		labelsSystem.removeLabelFromTask(task, labelName);
		assertEquals(0, labelsSystem.assignableLabels().size());
		
	}
	
	public void testCreatedTasksGoToAllLabel(){
		final TaskView task = createTask("task name", "1");
		assertEquals(task, labelsSystem.tasksInlabel(labelsSystem.allLabelName()).get(0));
	}
	
	public void testRemovedTasksAreRemovedFromLabels(){
		final TaskView task = createTask("task name", "1");
		labelsSystem.setLabelToTask(tasksSystem.getTaskView(tasksSystem.getIdOfTask(task)), "label");
		
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
		basicSystem.setNextId(taskId);
		tasksSystem.createTask(new TaskData(taskName, 0.0));
		return tasksSystem.getTaskView(new ObjectIdentity(taskId));
	}	
	
}
