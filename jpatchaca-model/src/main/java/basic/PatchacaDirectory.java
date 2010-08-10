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
		final File workingDir = new File(SystemUtils.getUserHome(),
				".jpatchaca");
		final String workingDirPath = workingDir.getAbsolutePath();
		if (!workingDir.exists()) {
			if (!workingDir.mkdir()) {
				throw new RuntimeException("Unable to create dir "
						+ workingDirPath);
			}
		}

		try {
			_subject = new DurableDirectory(workingDir.getAbsolutePath());
		} catch (final IOException e) {
			throw new RuntimeException(
					"Unable to create durable directory for: '" + workingDir
							+ "'", e);
		}
	}

	@Override
	public void close() {
		_subject.close();
	}

	@Override
	public String contentsAsString(final String fileName) throws IOException {
		return _subject.contentsAsString(fileName);
	}

	public void createFile(final String fileName, final String contents)
			throws IOException {
		_subject.createFile(fileName, contents);
	}

	public OutputStream createFile(final String name) throws IOException {
		return _subject.createFile(name);
	}

	public void deleteAllContents() throws IOException {
		_subject.deleteAllContents();
	}

	public void deleteFile(final String name) throws IOException {
		_subject.deleteFile(name);
	}

	public boolean fileExists(final String fileName) {
		return _subject.fileExists(fileName);
	}

	public String[] fileNames() {
		return _subject.fileNames();
	}

	@Override
	public int hashCode() {
		return _subject.hashCode();
	}

	public InputStream openFile(final String name) throws IOException {
		return _subject.openFile(name);
	}

	public void renameFile(final String oldName, final String newName)
			throws IOException {
		_subject.renameFile(oldName, newName);
	}

	@Override
	public String toString() {
		return _subject.toString();
	}

	@Override
	public String getPath() {
		return _subject.getPath();
	}

	@Override
	public OutputStream openFileForAppend(final String name) throws IOException {
		return _subject.openFileForAppend(name);
	}

	@Override
	public OutputStream openFileForAppendOrCry(String fileName) {
		return _subject.openFileForAppendOrCry(fileName);
	}

	@Override
	public void renameOrCry(String oldName, String newName) {
		_subject.renameOrCry(oldName, newName);
	}

}
