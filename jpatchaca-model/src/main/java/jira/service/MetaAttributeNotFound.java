package jira.service;

public class MetaAttributeNotFound extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public MetaAttributeNotFound(Throwable e) {
        super(e);
    }

    public MetaAttributeNotFound() {
        super();
    }
}
