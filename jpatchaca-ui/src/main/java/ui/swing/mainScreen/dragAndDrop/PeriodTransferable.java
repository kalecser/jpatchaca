package ui.swing.mainScreen.dragAndDrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;

public class PeriodTransferable implements Transferable {

	
	private final int periodIndex;

	public PeriodTransferable(int periodIndex){
		this.periodIndex = periodIndex;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{DataFlavor.stringFlavor};
	}



	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return ArrayUtils.contains(getTransferDataFlavors(), flavor);
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return "period - " + periodIndex ;
	}

}
