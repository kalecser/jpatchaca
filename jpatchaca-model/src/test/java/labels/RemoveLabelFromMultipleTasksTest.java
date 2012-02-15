package labels;

import java.util.LinkedHashSet;
import java.util.Set;

import labels.labels.LabelsHome;
import labels.labels.LabelsHomeImpl;
import labels.processors.RemoveLabelFromMultipleTasksProcessor;

import org.junit.Assert;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.TaskView;
import tasks.tasks.Tasks;
import core.ObjectIdentity;
import events.Processor;
import events.RemoveLabelFromMultipleTasks;
import events.persistence.MustBeCalledInsideATransaction;

public class RemoveLabelFromMultipleTasksTest {

	private final Tasks tasks = new Tasks();
	
	@Test
	public void onExecute_ShouldRemoveMultipleTasksFromLabel() throws MustBeCalledInsideATransaction{
		LabelsHome labelsHome = new LabelsHomeImpl();

		Set<TaskView> taskToRemove = new LinkedHashSet<TaskView>();
		taskToRemove.add(createTask("foo"));
		taskToRemove.add(createTask("bar"));
		
		labelsHome.setLabelToMultipleTasks("labelName", 
				taskToRemove);
		Assert.assertEquals("[foo, bar]", labelsHome.getTasksInLabel("labelName").toString());
		
		Processor<RemoveLabelFromMultipleTasks> subject = new RemoveLabelFromMultipleTasksProcessor(labelsHome, tasks);
		RemoveLabelFromMultipleTasks event = new RemoveLabelFromMultipleTasks("labelName", taskToRemove);
		subject.execute(event);
		Assert.assertEquals("[]", labelsHome.getTasksInLabel("labelName").toString());
	}

	private MockTask createTask(String name) throws MustBeCalledInsideATransaction {
		MockTask result = new MockTask(name);
		tasks.add(new ObjectIdentity(name), result);
		return result;
	}
	
}
