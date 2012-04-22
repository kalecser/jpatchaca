package ui.commandLine;

import java.util.Arrays;

import ui.common.ActiveTaskNameCopier;
import ui.swing.tasks.StartTask;
import jira.WorkLogger;


public class CommandLineInterfaceImpl implements CommandLineInterface {

	private final WorkLogger workLogger;
	private final ActiveTaskNameCopier copier;
	private final StartTask startTask;
	
	public CommandLineInterfaceImpl(WorkLogger workLogger, ActiveTaskNameCopier copier, StartTask startTask) {
		this.workLogger = workLogger;
		this.copier = copier;
		this.startTask = startTask;
	}

	@Override
	public String command(final String command){
		
		final String sendWorklog = "sendWorklog";
		if(command.equals(sendWorklog)){
			return sendWorklog();
		}
		
		final String copy = "copyActiveTaskName";
		if(command.equals(copy)){
			return copyActiveTaskName();
		}
		
		final String startTask = "startTask";
		if(command.startsWith(startTask)){
			String task = command.substring(command.indexOf(" ") + 1);
			return startTask(task);
		}
		
		return String.format("Invalid command: %s, valid commands are: %s", command, Arrays.asList(sendWorklog, copy, startTask));
	}

	private String startTask(String task) {
		startTask.startTask(task);
		return "Task " + task  + " started";
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
