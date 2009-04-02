package basic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.SystemUtils;

import wheel.io.files.Directory;
import wheel.io.files.impl.DurableDirectory;

public final class PatchacaDirectory implements Directory {

	private final DurableDirectory _subject;

	public PatchacaDirectory() {
		File workingDir = new File(SystemUtils.getUserHome(), ".jpatchaca");
		String workingDirPath = workingDir.getAbsolutePath();
		if (!workingDir.exists())
			if (!workingDir.mkdir())
				throw new RuntimeException("Unable to create dir " + workingDirPath);
		
		try {
			_subject = new DurableDirectory(workingDir.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException("Unable to create durable directory for: '" + workingDir +"'", e);
		}
	}

	@Override
	public void close() {
		_subject.close();		
	}

	@Override
	public String contentsAsString(String fileName) throws IOException {
		return _subject.contentsAsString(fileName);
	}

	public void createFile(String fileName, String contents) throws IOException {
		_subject.createFile(fileName, contents);
	}

	public OutputStream createFile(String name) throws IOException {
		return _subject.createFile(name);
	}

	public void deleteAllContents() throws IOException {
		_subject.deleteAllContents();
	}

	public void deleteFile(String name) throws IOException {
		_subject.deleteFile(name);
	}

	public boolean equals(Object obj) {
		return _subject.equals(obj);
	}

	public boolean fileExists(String fileName) {
		return _subject.fileExists(fileName);
	}

	public String[] fileNames() {
		return _subject.fileNames();
	}

	public int hashCode() {
		return _subject.hashCode();
	}

	public InputStream openFile(String name) throws IOException {
		return _subject.openFile(name);
	}

	public void renameFile(String oldName, String newName) throws IOException {
		_subject.renameFile(oldName, newName);
	}

	public String toString() {
		return _subject.toString();
	}

	@Override
	public String getPath() {
		return _subject.getPath();
	}

	@Override
	public OutputStream openFileForAppend(String name) throws IOException {
		return _subject.openFileForAppend(name); 
	}

	
}
