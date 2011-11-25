package ui.swing.mainScreen.dragAndDrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;

public class PeriodTransferable implements Transferable {

	
	private final int periodIndex;
	private final String taskId;

	public PeriodTransferable(String taskId, int periodIndex){
		this.taskId = taskId;
		this.periodIndex = periodIndex;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{DataFlavor.stringFlavor};
	}



	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return ArrayUtils.contains(getTransferDataFlavors(), flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return String.format("task: %s period: %s", taskId, periodIndex) ;
	}

}
