package wheel.io.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public interface Directory {

	OutputStream createFile(String fileName) throws IOException;
	void createFile(String fileName, String contents) throws IOException;
	OutputStream openFileForAppend(String fileName) throws IOException;
	OutputStream openFileForAppendOrCry(String fileName);
	
	InputStream openFile(String fileName) throws IOException;
	String contentsAsString(String fileName) throws IOException;

	String[] fileNames();
	
	boolean fileExists(String fileName);
	void renameFile(String oldName, String newName) throws IOException;
	void deleteFile(String fileName) throws IOException;
		
	void deleteAllContents() throws IOException;

	String getPath();
	
	/** Closes all open Streams and does not allow opening of new Streams.*/
	void close();

}
