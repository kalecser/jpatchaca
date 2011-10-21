package wheel.io.files.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import wheel.io.files.Directory;
import wheel.io.files.impl.Closeable.Listener;

public abstract class AbstractDirectory implements Directory {

	@Override
	public void createFile(String fileName, String contents) throws IOException {
		OutputStream output = null;
		try {
			output = createFile(fileName);
			IOUtils.write(contents, output);
		} finally {
			if (output != null) output.close();
		}
		
	}

	@Override
	public String contentsAsString(String fileName) throws IOException {
		InputStream input = null;
		try {
			input = openFile(fileName);
			return IOUtils.toString(input);
		} finally {
			if (input != null) input.close();
		}
	}
	
	@Override
	public OutputStream openFileForAppendOrCry(String fileName) {
		OutputStream out = null; 
		try {
			out = this.openFileForAppend(fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out;
	}
	
	@Override
	public void renameOrCry(String oldName, String newName) {
		try {
			renameFile(oldName, newName);
		} catch (IOException e) {
			String message = String.format("Error renaming %s to %s", oldName, newName);
			throw new IllegalStateException(message , e);
		}
	}

	private final Map<String, Collection<Closeable>> _openStreamsByFilename = new HashMap<String, Collection<Closeable>>();
	protected boolean _isClosed = false;
	protected abstract String getPath(String filename);

	protected void throwFileAlreadyExists(String name) throws IOException {
		throwIO("File already exists", name);
	}

	protected void throwFileNotFound(String name) throws FileNotFoundException {
		throw new FileNotFoundException("File not found: " + getPath(name));
	}

	protected void throwUnableToDelete(String name) throws IOException {
		throwIO("Unable to delete file", name);
	}

	protected void throwUnableToRename(String oldName, String newName) throws IOException {
		throwIO("Unable to rename file: " + getPath(oldName) + " to", newName);
	}

	private void throwIO(String string, String name) throws IOException {
		throw new IOException(string + ": " + getPath(name));
	}



	protected synchronized void mindOpenStream(Closeable stream, final String filename) {
		stream.notifyOnClose(new Listener() {
				@Override
				public void streamClosed(Closeable closedStream) {
					forgetStream(closedStream, filename);
				}
		});

		openStreamsFor(filename).add(stream);
	}

	@Override
	public synchronized void close() {
		closeStreams(allOpenStreams());

		_isClosed = true;
	}

	private ArrayList<Closeable> allOpenStreams() {
		final ArrayList<Closeable> result = new ArrayList<Closeable>();

		for (final String filename : _openStreamsByFilename.keySet())
			result.addAll(openStreamsFor(filename));

		return result;
	}

	private Collection<Closeable> openStreamsFor(String filename) {
		Collection<Closeable> result = _openStreamsByFilename.get(filename);
		if (result == null) {
			result = new HashSet<Closeable>();
			_openStreamsByFilename.put(filename, result);
		}
		return result;
	}

	synchronized void forgetStream(Object closedStream, String filename) {
		final Collection<?> openStreams = openStreamsFor(filename);
		openStreams.remove(closedStream);
		if (openStreams.isEmpty()) _openStreamsByFilename.remove(filename);
	}

	private void closeStreams(List<Closeable> streams) {
		for (Closeable closeable : streams) {
			closeQuietly(closeable);
		}
	}

	/**
	 * @see IOUtils#closeQuietly(InputStream)
	 */
	private void closeQuietly(Closeable closeable) {
		try {
			closeable.close();
		} catch (final IOException ignored) {
			// ignore
		}
	}

	protected boolean hasOpenStreamsFor(String filename) {
		return !openStreamsFor(filename).isEmpty();
	}

	protected void assertNotClosed() throws IllegalStateException {
		if (_isClosed) throw new IllegalStateException("" + getClass() + " already closed.");
	}

	@Override
	public synchronized void deleteFile(String name) throws IOException {
		assertNotClosed();

		if (!fileExists(name)) throwFileNotFound(name);
		if (hasOpenStreamsFor(name)) throwUnableToDelete(name);

		physicalDelete(name);
	}

	protected abstract void physicalDelete(String name) throws IOException;

	@Override
	public synchronized void renameFile(String oldName, String newName) throws IOException {
		assertNotClosed();

		if (fileExists(newName)) throwFileAlreadyExists(newName);
		if (hasOpenStreamsFor(oldName)) throwUnableToDelete(oldName);

		physicalRenameFile(oldName, newName);
	}

	protected abstract void physicalRenameFile(String oldName, String newName) throws IOException;


}
