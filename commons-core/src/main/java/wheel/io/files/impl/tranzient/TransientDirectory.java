package wheel.io.files.impl.tranzient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wheel.io.files.impl.AbstractDirectory;

public class TransientDirectory extends AbstractDirectory {

	private final Map<String, List<Byte>> _fileContentsByName = new HashMap<String, List<Byte>>();

	@Override
	public synchronized OutputStream createFile(String name) throws IOException {
		assertNotClosed();

		if (fileExists(name)) throwFileAlreadyExists(name);

		final List<Byte> contents = new ArrayList<Byte>();
		_fileContentsByName.put(name, contents);

		return createOutputStream(name, contents);
	}


	private OutputStream createOutputStream(final String filename, List<Byte> contents) {
		final ByteListOutputStream result = new ByteListOutputStream(contents);
		mindOpenStream(result, filename);
		return result;
	}


	@Override
	public synchronized InputStream openFile(String name) throws FileNotFoundException {
		assertNotClosed();

		if (!fileExists(name)) throwFileNotFound(name);

		final List<Byte> contents = _fileContentsByName.get(name);
		final ByteListInputStream result = new ByteListInputStream(contents);
		mindOpenStream(result, name);
		return result;
	}

	@Override
	protected void physicalDelete(String name) {
		_fileContentsByName.remove(name);
	}


	@Override
	public synchronized boolean fileExists(String fileName) {
		assertNotClosed();
		return _fileContentsByName.containsKey(fileName);
	}

	@Override
	protected void physicalRenameFile(String oldName, String newName) throws IOException {
		final List<Byte> contents = _fileContentsByName.get(oldName);
		deleteFile(oldName);
		_fileContentsByName.put(newName, contents);
	}

	@Override
	public synchronized void deleteAllContents() throws IOException {
		assertNotClosed();

		final Iterator<String> it = _fileContentsByName.keySet().iterator();
		while (it.hasNext()) {
			final String filename = it.next();
			deleteFile(filename);
		}
	}

	@Override
	public synchronized String[] fileNames() {
		assertNotClosed();
		return _fileContentsByName.keySet().toArray(new String[0]);
	}

	@Override
	public synchronized void close() {
		_fileContentsByName.clear();
		super.close();
	}



	@Override
	protected String getPath(String filename) {
		return filename;
	}


	@Override
	public String getPath() {
		return ".";
	}


	@Override
	public OutputStream openFileForAppend(String name) throws IOException {
		assertNotClosed();

		if (!_fileContentsByName.containsKey(name))
			_fileContentsByName.put(name, new ArrayList<Byte>());

		return createOutputStream(name, _fileContentsByName.get(name));
	}



}
