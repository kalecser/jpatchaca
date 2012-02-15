package jira.issue;

import java.io.Serializable;

public class JiraIssue implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String summary;
	private String key;
	private String id;

	public JiraIssue(JiraIssueData data) {
		this.summary = data.getSummary();
		this.key = data.getKey();
		this.id = data.getId();
	}

	public String getSummary() {
		return summary;
	}

	public String getKey() {
		return key;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass())
			return false;
		
		return ((JiraIssue) obj).getKey().equals(this.getKey());
	}	
	
	@Override
	public int hashCode() {
	    return key.hashCode() * 127;
	}
}
