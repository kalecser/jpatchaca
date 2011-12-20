package jira.issue;

public class JiraField {

	private final String name;
	private final String id;

	public JiraField(String id, String name)
	{
		this.id = id;
		this.name = name;		
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}	
}
