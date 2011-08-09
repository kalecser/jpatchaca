package jira.processors;

import java.io.Serializable;

import org.picocontainer.Startable;

import jira.JiraOptions;
import jira.events.SetJiraConfig;
import events.EventsSystem;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class SetJiraConfigProcessor implements Processor<SetJiraConfig>, Startable {

	private JiraOptions jiraOptions;	
	
	public SetJiraConfigProcessor(JiraOptions jiraOptions, EventsSystem eventSystem)
	{
		this.jiraOptions = jiraOptions;
		eventSystem.addProcessor(this);		
	}
	
	@Override
	public Class<? extends Serializable> eventType() {
		return SetJiraConfig.class;
	}

	@Override
	public void execute(SetJiraConfig eventObj)
			throws MustBeCalledInsideATransaction {
		jiraOptions.setURL(eventObj.getUrl());
		jiraOptions.setUserName(eventObj.getUsername());
		jiraOptions.setPassword(eventObj.getPassword());
		jiraOptions.setIssueStatusManagementEnabled(eventObj.isIssueStatusManagementEnabled());
	}

	@Override
	public void start() {
		
	}
	
	@Override
	public void stop() {
		
	}
	
}
