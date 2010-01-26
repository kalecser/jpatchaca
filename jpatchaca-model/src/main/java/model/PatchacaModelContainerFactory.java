package model;

import labels.LabelsSystem;
import labels.LabelsSystemImpl;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import periods.impl.PeriodsFactoryImpl;
import periodsInTasks.impl.PeriodsInTasksSystemImpl;
import tasks.ActiveTask;
import tasks.TasksSystemImpl;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskDataParser;
import tasks.delegates.StartTaskDelegate;
import tasks.home.TasksHomeImpl;
import tasks.persistence.CreateAndStartTaskRegister;
import tasks.persistence.CreateTaskPersistence;
import tasks.persistence.CreateTaskProcessorRegister;
import tasks.persistence.StartTaskPersistence;
import tasks.persistence.StartTaskProcessorRegister;
import tasks.processors.CreateAndStartTaskProcessor;
import tasks.processors.CreateTaskProcessor;
import tasks.processors.CreateTaskProcessor2;
import tasks.processors.CreateTaskProcessor2Register;
import tasks.processors.StartTaskProcessor;
import tasks.processors.StartTaskProcessor2;
import tasks.processors.StartTaskProcessor3;
import tasks.taskName.ActiveTaskName;
import tasks.taskName.TaskNameFactory;
import tasks.tasks.Tasks;
import twitter.TwitterLogger;
import twitter.TwitterOptions;
import twitter.processors.SetTwitterConfigProcessor;
import basic.HardwareClock;
import basic.PatchacaDirectory;
import basic.SystemClockImpl;
import basic.durable.DoubleIdProvider;
import events.EventsSystemImpl;
import events.eventslist.CensorFromFile;
import events.eventslist.EventListImpl;
import events.persistence.FileAppenderPersistence;

public class PatchacaModelContainerFactory {

	public MutablePicoContainer create(final HardwareClock hardwareClock) {
		final MutablePicoContainer container = new PicoBuilder()
				.withLifecycle().withCaching().build();

		container.addComponent(hardwareClock);
		container.addComponent(DoubleIdProvider.class);
		container.addComponent(PatchacaDirectory.class);
		container.addComponent(SystemClockImpl.class);

		container.addComponent(PeriodsFactoryImpl.class);
		container.addComponent(FileAppenderPersistence.class);
		container.addComponent(CensorFromFile.class);
		container.addComponent(EventListImpl.class);
		container.addComponent(EventsSystemImpl.class);
		container.addComponent(ActiveTask.class);
		container.addComponent(ActiveTaskName.class);
		container.addComponent(TaskNameFactory.class);
		container.addComponent(CreateTaskProcessor2.class);
		container.addComponent(CreateTaskProcessor2Register.class);
		container.addComponent(CreateAndStartTaskRegister.class);
		container.addComponent(CreateAndStartTaskProcessor.class);
		container.addComponent(CreateTaskProcessorRegister.class);
		container.addComponent(CreateTaskProcessor.class);
		container.addComponent(StartTaskProcessor3.class);
		container.addComponent(StartTaskProcessorRegister.class);
		container.addComponent(StartTaskProcessor.class);
		container.addComponent(StartTaskDataParser.class);
		container.addComponent(Tasks.class);
		container.addComponent(TasksHomeImpl.class);
		container.addComponent(TasksSystemImpl.class);
		container.addComponent(StartTaskDelegate.class);
		container.addComponent(CreateTaskDelegate.class);
		container.addComponent(StartTaskProcessor2.class);
		container.addComponent(StartTaskPersistence.class);
		container.addComponent(CreateTaskPersistence.class);

		container.addComponent(TwitterOptions.class);
		container.addComponent(TwitterLogger.class);
		container.addComponent(SetTwitterConfigProcessor.class);

		container.addComponent(PeriodsInTasksSystemImpl.class);
		container.addComponent(LabelsSystem.class, LabelsSystemImpl.class);

		return container;
	}

}
