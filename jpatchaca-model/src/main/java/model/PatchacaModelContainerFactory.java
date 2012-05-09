package model;


import jira.JiraOptions;
import jira.JiraSystemImpl;
import jira.JiraWorklogOverride;
import jira.SslManagerSetup;
import jira.events.JiraEventFactory;
import jira.processors.SendWorklogProcessor;
import jira.processors.SetJiraConfigProcessor;
import jira.processors.SetJiraIssueToTaskProcessor;
import jira.service.CachedJira;
import jira.service.Jira;
import jira.service.JiraImpl;
import jira.service.JiraServiceFacade;
import jira.service.JiraSoapServiceFactory;
import jira.service.TokenManager;
import keyboardRotation.KeyboardRotationOptions;
import keyboardRotation.SetKeyboardRotationOptions2Processor;
import keyboardRotation.SetKeyboardRotationOptionsProcessor;
import labels.LabelsSystem;
import labels.LabelsSystemImpl;
import labels.labels.SelectedLabel;
import main.singleInstance.AssureSingleInstance;
import newAndNoteworthy.NewAndNoteworthyConsumptionProcessor;
import newAndNoteworthy.NewAndNoteworthyImpl;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.parameters.ComponentParameter;

import periods.impl.PeriodsFactoryImpl;
import periodsInTasks.impl.PeriodsInTasksSystemImpl;
import tasks.ActiveTask;
import tasks.TasksSystemImpl;
import tasks.delegates.CreateTaskDelegateImpl;
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
import twitter.processors.SetTwitterConfigProcessor;
import basic.ErrorLog;
import basic.HardwareClock;
import basic.PatchacaDirectory;
import basic.SystemClockImpl;
import basic.durable.DoubleIdProvider;
import events.EventsSystemImpl;
import events.eventslist.CensorFromFile;
import events.eventslist.EventListImpl;
import events.persistence.JavaPersistenceManager;
import events.persistence.PersistenceConverter;
import events.persistence.XmlPersistenceManager;

public class PatchacaModelContainerFactory {

	public static MutablePicoContainer createNonUIContainer(
			final HardwareClock hardwareClock) {
		return new PatchacaModelContainerFactory().create(hardwareClock);
	}
	
	public MutablePicoContainer create(final HardwareClock hardwareClock) {
		final MutablePicoContainer container = new PicoBuilder()
				.withLifecycle().withCaching().build();

		container.addComponent(ErrorLog.class);
		container.addComponent(hardwareClock);
		container.addComponent(AssureSingleInstance.class);
		container.addComponent(DoubleIdProvider.class);
		container.addComponent(PatchacaDirectory.class);
		container.addComponent(SystemClockImpl.class);
		container.addComponent(SslManagerSetup.class);
		container.addComponent(PeriodsFactoryImpl.class);
		container.addComponent(JavaPersistenceManager.class);
		container.addComponent(XmlPersistenceManager.class);
		container.addComponent(PersistenceConverter.class);
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
		container.addComponent(CreateTaskDelegateImpl.class);
		container.addComponent(StartTaskProcessor2.class);
		container.addComponent(StartTaskPersistence.class);
		container.addComponent(CreateTaskPersistence.class);
		
		container.addComponent(NewAndNoteworthyImpl.class);
		container.addComponent(NewAndNoteworthyConsumptionProcessor.class);

		container.addComponent(KeyboardRotationOptions.class);
		container.addComponent(SetTwitterConfigProcessor.class);
		container.addComponent(SetKeyboardRotationOptionsProcessor.class);
		container.addComponent(SetKeyboardRotationOptions2Processor.class);
		container.addComponent(keyboardRotation.TurnOnKeyboardRotationAlert.class);

		container.addComponent(JiraOptions.class);
		container.addComponent(SetJiraConfigProcessor.class);
		container.addComponent("JiraImpl", JiraImpl.class);
		container.addComponent(Jira.class, CachedJira.class, new ComponentParameter("JiraImpl"));
		container.addComponent(JiraServiceFacade.class);
		container.addComponent(SetJiraIssueToTaskProcessor.class);
		container.addComponent(SendWorklogProcessor.class);
		container.addComponent(JiraSystemImpl.class);
		container.addComponent(JiraWorklogOverride.class);
		container.addComponent(JiraSoapServiceFactory.class);
		container.addComponent(TokenManager.class);
		container.addComponent(JiraEventFactory.class);

		container.addComponent(PeriodsInTasksSystemImpl.class);
		container.addComponent(LabelsSystem.class, LabelsSystemImpl.class);
		container.addComponent(SelectedLabel.class);

		return container;
	}

}
