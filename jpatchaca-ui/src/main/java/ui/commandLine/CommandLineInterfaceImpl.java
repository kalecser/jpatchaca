package ui.commandLine;

import jira.WorkLogger;


public class CommandLineInterfaceImpl implements CommandLineInterface {

	private final WorkLogger workLogger;
	
	public CommandLineInterfaceImpl(WorkLogger workLogger) {
		this.workLogger = workLogger;
	}

	/* (non-Javadoc)
	 * @see ui.commandLine.CommandLineInterface#command(java.lang.String)
	 */
	public String command(final String command){
		
		if(command.equals("sendWorklog")){
			return sendWorklog();
		}
		
		return "Invalid command: "+command;
	}

	private String sendWorklog() {
		try{
			workLogger.logWork();
		}catch (Exception e) {
			e.printStackTrace();
			return "Worklog not sent. Error: "+ e.getMessage();
		}
		
		return "Worklog sent";
	}
}
