package jira;

import org.reactive.Signal;

import reactive.ListSignal;

public interface JiraIssuesRetriever {

	ListSignal<String> issues();

	Signal<Boolean> isConfigured();
}