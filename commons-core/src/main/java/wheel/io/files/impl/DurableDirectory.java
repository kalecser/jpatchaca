package wheel.io.files.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DurableDirectory extends AbstractDirectory {

	static private class DurableFileOutputStream extends FileOutputStream implements Closeable {

		private Listener _closeListener;

		private DurableFileOutputStream(File file) throws FileNotFoundException {
			super(file);
		}

		public DurableFileOutputStream(File file, boolean append) throws FileNotFoundException {
			super(file, append);
		}

		@Override
		public void flush() throws IOException {
			super.flush();
			getFD().sync();
		}

		@Override
		public void close() throws IOException {
			try {
				flush();
			} finally {
				_closeListener.streamClosed(this);
				super.close();
			}
		}

		public void notifyOnClose(Listener listener) {
			_closeListener = listener;
		}

	}

	static private class CloseListenableFileInputStream extends FileInputStream implements Closeable {

		private Listener _closeListener;

		private CloseListenableFileInputStream(File file) throws FileNotFoundException {
			super(file);
		}

		@Override
		public void close() throws IOException {
			_closeListener.streamClosed(this);
			super.close();
		}

		public void notifyOnClose(Listener listener) {
			_closeListener = listener;
		}

	}


	public DurableDirectory(String path) throws IOException {
		_delegate = new File(path);
		if (!_delegate.exists() && !_delegate.mkdirs()) throw new IOException("Unable to create directory " + _delegate);
	}


	private final File _delegate;


	public OutputStream createFile(String name) throws IOException {
		assertNotClosed();

		if (fileExists(name)) throwFileAlreadyExists(name);
		final DurableFileOutputStream result = new DurableFileOutputStream(file(name));
		mindOpenStream(result, name);
		return result;
	}

	public InputStream openFile(String name) throws IOException {
		assertNotClosed();

		CloseListenableFileInputStream result = null;
		try {
			result = new CloseListenableFileInputStream(file(name));
		} catch (final FileNotFoundException e) {
			throwFileNotFound(name);
		}

		mindOpenStream(result, name);
		return result;
	}

	@Override
	protected void physicalDelete(String name) throws IOException {
		if (file(name).delete()) return;
		throwUnableToDelete(name);
	}

	public boolean fileExists(String fileName) {
		assertNotClosed();

		return file(fileName).exists();
	}

	@Override
	protected void physicalRenameFile(String oldName, String newName) throws IOException {
		if (file(oldName).renameTo(file(newName))) return;
		throwUnableToRename(oldName, newName);
	}

	public void deleteAllContents() throws IOException {
		assertNotClosed();

		final String[] fileNames = fileNames();
		for (final String fileName : fileNames)
			deleteFile(fileName);
	}

	public String[] fileNames() {
		assertNotClosed();

		final File[] files = _delegate.listFiles();
		final String[] result = new String[files.length];
		for (int i = 0; i < files.length; i++)
			result[i] = files[i].getName();
		return result;
	}



	File file(String fileName) {
		return new File(_delegate, fileName);
	}

	@Override
	protected String getPath(String name) {
		return file(name).getAbsolutePath();
	}

	@Override
	public String getPath() {
		return _delegate.getAbsolutePath();
	}

	@Override
	public OutputStream openFileForAppend(String name) throws IOException {
		assertNotClosed();

		final DurableFileOutputStream result = new DurableFileOutputStream(file(name), true);
		mindOpenStream(result, name);
		return result;
	}

}
