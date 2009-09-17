/**
 * 
 */
package ui.swing.mainScreen;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.TransferHandler;

import ui.swing.utils.SwingUtils;
import basic.Alert;
import basic.AlertImpl;

@SuppressWarnings("serial")
public class LabelsList extends JList {

	private final DefaultListModel model;

	private final AlertImpl assignTaskToLabelAlert;
	private String dropToLabel;
	private final LabelTooltipProvider tipProvider;

	private int preferredIndex;

	public LabelsList(final LabelsListModel labelsModel) {
		super();

		this.tipProvider = labelsModel.getTooltips();

		this.model = new DefaultListModel();
		this.assignTaskToLabelAlert = new AlertImpl();
		setModel(this.model);
		setDropMode(DropMode.ON);

		setTransferHandler(new TransferHandler() {

			@Override
			public boolean importData(final TransferSupport info) {
				try {
					final int dropIndex = getDropIndex(info);

					dropToLabel = (String) model.get(dropIndex);
					assignTaskToLabelAlert.fire();

				} catch (final Exception e) {
					Logger.getLogger(LabelsList.class.getName()).severe(
							e.getMessage());
				}
				return super.importData(info);
			}

			@Override
			public boolean canImport(final TransferSupport info) {

				try {
					final String transferData = (String) info.getTransferable()
							.getTransferData(DataFlavor.stringFlavor);
					final boolean isTaskDrop = transferData
							.startsWith("task - ");

					final int dropIndex = getDropIndex(info);

					if (isTaskDrop && info.isDrop() && dropIndex != -1) {
						return true;
					}

				} catch (final Exception ex) {
				}

				return false;
			}

			private int getDropIndex(final TransferSupport info) {
				final DropLocation location = info.getDropLocation();
				final Point pt = location.getDropPoint();
				final int dropIndex = locationToIndex(pt);
				return dropIndex;
			}

		});

	}

	@Override
	public String getToolTipText(final MouseEvent event) {
		final int index = locationToIndex(event.getPoint());
		final String item = (String) getModel().getElementAt(index);

		if (tipProvider != null) {
			return tipProvider.getTipFor(item);
		}

		return "No tips...";
	}

	public void setLabels(final List<String> labels) {

		if (EventQueue.isDispatchThread()) {
			internalSetLabels(labels);
		} else {
			SwingUtils.invokeAndWaitOrCry(new Runnable() {
				@Override
				public void run() {
					internalSetLabels(labels);
				}
			});
		}
	}

	private void internalSetLabels(final List<String> labels) {
		int selectedLabel = preferredIndex;
		this.model.clear();
		int i = 0;
		for (final String label : labels) {
			this.model.add(i++, label);
		}

		final boolean hasElements = this.model.getSize() > 0;
		final boolean noneSelected = (selectedLabel == -1);
		if (noneSelected && hasElements) {
			selectedLabel = 0;
		}
		setSelectedIndex(selectedLabel);
	}

	public Alert assignTaskToLabelAlert() {
		return assignTaskToLabelAlert;
	}

	public final String getDropToLabel() {
		final String tempDropToLabel = dropToLabel;
		dropToLabel = null;
		return tempDropToLabel;
	}

	public String selectedLabel() {
		return (String) getSelectedValue();
	}

	public void setPreferredIndex(final int preferredIndex) {
		this.preferredIndex = preferredIndex;

	}
}