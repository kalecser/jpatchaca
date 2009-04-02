package wheel.io.files.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import wheel.io.files.Directory;

public abstract class DirectoryTest extends TestCase {
	
	protected Directory _subject;
	
	protected abstract Directory subject() throws IOException;
	protected abstract String absoluteFileName(String name);

	@Override
	protected void setUp() throws IOException {
		_subject = subject();
	}

	public void testFileCreationAndDeletion() throws IOException{
		assertFalse(_subject.fileExists("test"));
		try {
			_subject.openFile("test");
			fail("Should raise IOException");
		} catch (final IOException e) {
			assertMessageContains(e, "File not found: ");
			assertMessageContains(e, "test");
		}		
		
		OutputStream stream = _subject.createFile("test");
		stream.close();
		assertTrue(_subject.fileExists("test"));

		try {
			_subject.createFile("test");
			fail("Should raise IOException");
		} catch (final IOException e) {
			assertEquals("File already exists: " + absoluteFileName("test"), e.getMessage());
		}
		
		_subject.deleteFile("test");
		assertFalse(_subject.fileExists("test"));
		
		try{
			_subject.deleteFile("test");
			fail("Should raise IOException");
		} catch (final IOException expected){}
		
	}
	
	private void assertMessageContains(Exception e, String part) {
		assertTrue(e.getMessage().indexOf(part) != -1);
	}

	public void testDeleteWithOpenStreams() throws IOException {
		final OutputStream out = _subject.createFile("log");
		try {
			_subject.deleteFile("log");
			fail("Should not be allowed to delete a file with a stream open on it.");
		} catch (final IOException expected) {}
		out.close();
		
		final InputStream in = _subject.openFile("log");
		try {
			_subject.deleteFile("log");
			fail("Should not be allowed to delete a file with a stream open on it.");
		} catch (final IOException expected) {}
		in.close();
	}

	
	public void testReadWrite() throws IOException, ClassNotFoundException {

		final String data = "this is test data";
		final Object data2 = new Double(0.0);
		
		final ObjectOutputStream objOut = new ObjectOutputStream(_subject.createFile("log"));
		
		objOut.writeObject(data);
		
		final InputStream in = _subject.openFile("log");
		final ObjectInputStream objIn = new ObjectInputStream(in);
		assertEquals(data, objIn.readObject());
		
		objOut.writeObject(data2);
		objOut.close();
		assertEquals(data2, objIn.readObject());
		assertEquals(-1, in.read());
		
		objIn.close();
	}

	public void testRename() throws IOException {
		_subject.createFile("log").close();
		_subject.renameFile("log", "log2");
		assertFalse(_subject.fileExists("log"));
		assertTrue(_subject.fileExists("log2"));

		try{
			_subject.renameFile("trash", "trash1" );
			fail("Should raise IOException");
		} catch (final IOException expected) {}
	}

	public void testRenameWithOpenStreams() throws IOException{
		
		final OutputStream out = _subject.createFile("log");
		try {
			_subject.renameFile("log", "log2");
			fail("Should not be allowed to rename a file with a stream open on it.");
		} catch (final IOException expected) {}
		out.close();
		
		final InputStream in = _subject.openFile("log");
		try {
			_subject.renameFile("log", "log2");
			fail("Should not be allowed to rename a file with a stream open on it.");
		} catch (final IOException expected) {}
		in.close();
	}

}
