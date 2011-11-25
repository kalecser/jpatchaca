package newAndNoteworthy;

import java.io.IOException;

import newAndNoteworthy.mock.EventsConsumerMock;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewAndNoteworthyTest {

	private NewAndNoteworthy subject;

	@Test
	public void getText_willReturnNewAndNoteworthyResource() throws IOException{		
		Assert.assertTrue(subject.hasUnreadNewAndNoteworthy().currentValue());
		Assert.assertEquals(readFormResource(),subject.getTextAndMarkAsRead());
		Assert.assertFalse(subject.hasUnreadNewAndNoteworthy().currentValue());
	}
	
	@Before
	public void before(){
		EventsConsumerMock eventsConsumerMock = new EventsConsumerMock();
		subject = new NewAndNoteworthyImpl(eventsConsumerMock);
		eventsConsumerMock.newAndNoteworthy = (NewAndNoteworthyImpl) subject;
	}

	private static String readFormResource() throws IOException {
		return IOUtils.toString(NewAndNoteworthyTest.class.getResourceAsStream("/newAndNoteworthy.txt"));
	}
	
}
