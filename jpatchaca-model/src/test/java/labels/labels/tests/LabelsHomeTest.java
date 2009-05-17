package labels.labels.tests;

import labels.labels.LabelsHome;
import labels.labels.LabelsHomeImpl;

import org.jmock.MockObjectTestCase;

import tasks.TaskView;

public class LabelsHomeTest extends MockObjectTestCase {

	public void testLabelAll(){

		final LabelsHome home = new LabelsHomeImpl();
		assertEquals("All", home.labels().get(0));
		
		final TaskView mockTask = (TaskView) mock(TaskView.class).proxy();
		home.setLabelToTask(mockTask, "label1");
		
		assertEquals("All", home.labels().get(0));
		assertEquals("label1", home.labels().get(1));

	}
	
	public void testAddTheSameTaskTwiceToLabel() {		
		final LabelsHome home = new LabelsHomeImpl();

		final TaskView mockTask = (TaskView) mock(TaskView.class).proxy();
		home.setLabelToTask(mockTask, "label1");
		home.setLabelToTask(mockTask, "label1");
		
		assertEquals(1, home.getTasksInLabel("label1").size());
	}
	
	public void testALabelShouldBeRemovedWhenItsLastTasksHasBeenRemoved() {		
		final LabelsHome home = new LabelsHomeImpl( );

		final TaskView mockTask = (TaskView) mock(TaskView.class).proxy();
		home.setLabelToTask(mockTask, "label1");
		home.removeTaskFromLabel(mockTask, "label1");
		
		assertFalse(home.labels().contains("label1"));
	}
}
