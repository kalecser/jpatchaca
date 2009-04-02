package wheel.io.files.impl.tests;

import java.io.File;
import java.io.IOException;

import wheel.io.files.Directory;
import wheel.io.files.impl.DurableDirectory;
import wheel.io.files.tests.DirectoryTest;

public class DurableDirectoryTest extends DirectoryTest {

	private static final File TEST_DIRECTORY = new File("tmpTestDirectory");

	@Override
	protected Directory subject() throws IOException {
		return new DurableDirectory(TEST_DIRECTORY.getAbsolutePath());
	}

	@Override
	protected String absoluteFileName(String filename) {
		final File directory = TEST_DIRECTORY;
		return new File(directory, filename).getAbsolutePath();
	}

	@Override
	protected void tearDown() throws IOException {
		_subject.deleteAllContents();
		assertTrue(TEST_DIRECTORY.delete());
	}

}
