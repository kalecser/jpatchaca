package jira.issue;

import java.util.ArrayList;
import java.util.List;

public class JiraAction {

	private final String name;
	private final String id;
	private List<JiraField> fields;
	
	
	public JiraAction(String id, String name)
	{
		this.id = id;
		this.name = name;
		fields = new ArrayList<JiraField>();
	}
	
	
	public void addField(JiraField field) {
		fields.add(field);
	}	
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}	
	
	@Override
	public String toString() {
		return name;
	}
}
