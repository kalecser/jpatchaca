package ui.swing.mainScreen.periods.mock;

import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.Border;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTableOperator;

public class PeriodsListOperator {

	private JDialogOperator frame;
	private JButtonOperator mergePeriodsButon;
	private JTableOperator periodsTable;

	public PeriodsListOperator(){
		frame = new JDialogOperator();
		mergePeriodsButon = new JButtonOperator(frame, 2);
		periodsTable = new JTableOperator(frame);
	}
	
	public void assertMergeperiodsButtonWithEmptyBorder() {
		mergePeriodsButon.waitState(new ComponentChooser() {
			
			@Override
			public String getDescription() {
				return "Button border is empty";
			}
			
			@Override
			public boolean checkComponent(Component comp) {
				return isButtonBorderEqualsTo(comp, BorderFactory.createEmptyBorder());
			}
		});
	}
	
	public void assertMergeperiodsButtonWithRegularBorder() {
		mergePeriodsButon.waitState(new ComponentChooser() {
			
			@Override
			public String getDescription() {
				return "Default border";
			}
			
			@Override
			public boolean checkComponent(Component comp) {
				final Border expectedBorder = new JButton().getBorder();
				return isButtonBorderEqualsTo(comp, expectedBorder);
			}
		});
		
	}

	public void selectRows(List<Integer> rowsToselect) {
		Integer car = rowsToselect.get(0);
		Integer last = rowsToselect.get(rowsToselect.size() - 1);
		periodsTable.getSelectionModel().setSelectionInterval(car.intValue(), last.intValue());		
	}

	boolean isButtonBorderEqualsTo(Component comp,
			Border expectedBorder) {
		final Border actualBorder = ((JComponent)comp).getBorder();
		return actualBorder.equals(expectedBorder);
	}


}
