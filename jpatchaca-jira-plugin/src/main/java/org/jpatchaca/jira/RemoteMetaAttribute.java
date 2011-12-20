package org.jpatchaca.jira;

public class RemoteMetaAttribute implements Comparable<RemoteMetaAttribute>{

	private final String name;
	private final String value;

	public RemoteMetaAttribute(String name, String value) {
		this.name = name;
		this.value = value;		
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public int compareTo(RemoteMetaAttribute other) {
		return name.compareTo(other.name);
	}	
}
