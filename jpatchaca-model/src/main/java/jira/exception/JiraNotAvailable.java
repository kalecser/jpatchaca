package jira.exception;


public class JiraNotAvailable extends JiraException {


    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Jira not available";

    public JiraNotAvailable(Throwable cause) {
        super(MESSAGE, cause);
    }
    
    public JiraNotAvailable(){
        super(MESSAGE);
    }
}
