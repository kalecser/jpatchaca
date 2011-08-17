package ui.commandLine;

import ui.common.ActiveTaskNameCopier;
import jira.WorkLogger;


public class CommandLineInterfaceImpl implements CommandLineInterface {

	private final WorkLogger workLogger;
	private final ActiveTaskNameCopier copier;
	
	public CommandLineInterfaceImpl(WorkLogger workLogger, ActiveTaskNameCopier copier) {
		this.workLogger = workLogger;
		this.copier = copier;
	}

	public String command(final String command){
		
		final String sendWorklog = "sendWorklog";
		if(command.equals(sendWorklog)){
			return sendWorklog();
		}
		
		final String copy = "copyActiveTaskName";
		if(command.equals(copy)){
			return copyActiveTaskName();
		}
		
		return String.format("Invalid command: %s, valid commands are: %s", command, sendWorklog, copy );
	}

	private String copyActiveTaskName() {
		copier.copyActiveTaskNameToClipboard();
		return  "Active task name copied to clipboard";
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
