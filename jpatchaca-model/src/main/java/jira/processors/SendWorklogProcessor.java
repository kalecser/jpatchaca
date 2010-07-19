package jira.processors;

import java.io.Serializable;

import jira.events.SendWorklog;

import org.picocontainer.Startable;

import tasks.tasks.Tasks;
import events.EventsSystem;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class SendWorklogProcessor implements Processor<SendWorklog>, Startable {

	private final Tasks tasks;

	public SendWorklogProcessor(final EventsSystem eventsSystem,
			final Tasks tasks) {
		eventsSystem.addProcessor(this);
		this.tasks = tasks;
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SendWorklog.class;
	}

	@Override
	public void execute(final SendWorklog eventObj)
			throws MustBeCalledInsideATransaction {

		tasks.get(eventObj.getTaskId()).getPeriod(eventObj.getPeriodIndex())
				.setWorklogSent(true);
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
