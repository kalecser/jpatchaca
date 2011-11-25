package labels.labels.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import labels.labels.LabelsHome;
import labels.labels.LabelsHomeImpl;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import tasks.TaskView;

public class LabelsHomeTest {

	private final Mockery context = new JUnit4Mockery();
	
	@Test
	public void testLabelAll(){

		final LabelsHome home = new LabelsHomeImpl();
		assertEquals("All", home.labels().get(0));
		
		final TaskView mockTask = context.mock(TaskView.class);
		home.setLabelToTask(mockTask, "label1");
		
		assertEquals("All", home.labels().get(0));
		assertEquals("label1", home.labels().get(1));

	}
	
	@Test
	public void testAddTheSameTaskTwiceToLabel() {		
		final LabelsHome home = new LabelsHomeImpl();

		final TaskView mockTask = context.mock(TaskView.class);
		home.setLabelToTask(mockTask, "label1");
		home.setLabelToTask(mockTask, "label1");
		
		assertEquals(1, home.getTasksInLabel("label1").size());
	}
	
	@Test
	public void testALabelShouldBeRemovedWhenItsLastTasksHasBeenRemoved() {		
		final LabelsHome home = new LabelsHomeImpl( );

		final TaskView mockTask = context.mock(TaskView.class);
		home.setLabelToTask(mockTask, "label1");
		home.removeTaskFromLabel(mockTask, "label1");
		
		assertFalse(home.labels().contains("label1"));
	}
}
