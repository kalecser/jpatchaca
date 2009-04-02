package swing.test;

import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTableOperator;

public class JXtableImprovedTest {

	private JXTableImprovedTestFrame frame;

	@Test
	public void testJXtableImporved(){
		frame = new JXTableImprovedTestFrame(
				new String[]{"column 1", 	"column2"}, 
				new String[][]{
				            {"foo",			"bar"},
				            {"baz",			"bas"}});
		
		JTableOperator tableOperator = new JTableOperator(new JFrameOperator());
		
		tableOperator.selectCell(0, 0);
		tableOperator.typeKey('b');
		tableOperator.typeKey('a');
		tableOperator.typeKey('r');
		tableOperator.pressKey(KeyEvent.VK_ENTER);
		
		Assert.assertEquals("bar", tableOperator.getValueAt(0, 0));
		
	}
	
	@After
	public void tearDown(){
		frame.dispose();	
	}
}
