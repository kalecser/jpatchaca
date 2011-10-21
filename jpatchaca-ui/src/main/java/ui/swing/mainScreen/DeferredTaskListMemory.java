package ui.swing.mainScreen;

import java.io.IOException;

import org.apache.commons.lang.UnhandledException;

import wheel.io.files.Directory;
import basic.DeferredExecutor;

public class DeferredTaskListMemory implements TaskListMemory {

	private static final String FILE_NAME = "tasksList.data";
	private final Directory directory;
	private final DeferredExecutor writer;
	private TasksListData tasksListData;

	public DeferredTaskListMemory(final Directory directory) {
		this.directory = directory;
		tasksListData = readTaskListDataFromDiskIFAvailable(directory);		
		writer = new DeferredExecutor(2000, new WriteDataToDisk());		
	}
	
	class WriteDataToDisk implements Runnable{
		
		@Override
		public void run() {
			try {
				if (directory.fileExists(FILE_NAME))
					directory.deleteFile(FILE_NAME);
				
				directory.createFile(FILE_NAME, tasksListData.encodeAsString());
			} catch (final IOException e) {
				throw new UnhandledException(e);
			}
		}
		
	}

	private TasksListData readTaskListDataFromDiskIFAvailable(final Directory directory) {
		if (!directory.fileExists(FILE_NAME))
			return new TasksListData();			
		
		try {
			return TasksListData.decode(directory.contentsAsString(FILE_NAME));
		} catch (final IOException e) {
			throw new UnhandledException(e);
		}
	}

	@Override
	public void mind(final TasksListData tasksListData) {
		this.tasksListData = tasksListData;
		writer.execute();
	}

	
	


	@Override
	public TasksListData retrieve() {
		return tasksListData;
	}

}
