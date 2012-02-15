package tasks.persistence;

import jira.events.JiraEventFactory;
import jira.events.SetJiraIssueToTask;

import org.picocontainer.Startable;

import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;
import basic.Delegate;
import basic.IdProvider;
import core.ObjectIdentity;
import events.CreateTaskEvent3;
import events.EventsConsumer;

public class CreateTaskPersistence implements Startable {

    private final CreateTaskDelegate delegate;
    private final EventsConsumer consumer;
    private final IdProvider provider;
    private final JiraEventFactory jiraEventFactory;

    public CreateTaskPersistence(final CreateTaskDelegate delegate, final EventsConsumer consumer,
            final IdProvider provider, JiraEventFactory jiraEventFactory) {
        this.delegate = delegate;
        this.consumer = consumer;
        this.provider = provider;
        this.jiraEventFactory = jiraEventFactory;
    }

    @Override
    public void start() {
        delegate.addListener(new Delegate.Listener<TaskData>() {
            @Override
            public void execute(final TaskData object) {
                onTaskData(object);
            }
        });
    }

    @Override
    public void stop() {
        // Nothing to do.
    }

    void onTaskData(final TaskData object) {

        final ObjectIdentity taskId = provider.provideId();

        CreateTaskEvent3 createTaskEvent = new CreateTaskEvent3(taskId, object.getTaskName(),
                object.getBudget(), object.getLabel());

        SetJiraIssueToTask setIssueEvent = null;
        if (object.getJiraIssue() != null)
            setIssueEvent = jiraEventFactory.createSetIssueToTaskEvent(taskId,
                    object.getJiraIssue());

        consumer.consume(createTaskEvent);
        
        if(setIssueEvent != null)
            consumer.consume(setIssueEvent);
    }

}
