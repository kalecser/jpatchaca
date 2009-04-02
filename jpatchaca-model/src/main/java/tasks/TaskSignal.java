package tasks;

import org.reactivebricks.pulses.list.ListSignal;

import tasks.tasks.TaskView;
import wheel.lang.Omnivore;

public interface TaskSignal extends Omnivore<TaskView>{

	ListSignal<Integer> years();

}
