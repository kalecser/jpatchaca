package ui.swing.mainScreen.tasks.day;

enum DayTaskTableModelCellValue {
	TaskName("Task", false) {
		@Override
		public Object getValue(final Pair item) {
			return item.taskName();
		}
	},

	WorklogStatus("Worklog status", false) {
		@Override
		public Object getValue(final Pair item) {
			return item.worklogStatus();
		}
	},
	Start("Start", true) {
		@Override
		public Object getValue(final Pair item) {
			return item.formatedStartTime();
		}
	},
	End("End", true) {
		@Override
		public Object getValue(final Pair item) {
			return item.formatedEndTime();
		}
	},
	Total("Total", false) {
		@Override
		public Object getValue(final Pair item) {
			return item.formatedTotalTime();
		}
	},

	ToSend("To send", true) {
		@Override
		public Object getValue(final Pair item) {
			return item.timeToSend();
		}
	};

	private String label;
	private boolean editable;

	private DayTaskTableModelCellValue(String label, boolean editable) {
		this.label = label;
		this.editable = editable;
	}

	public abstract Object getValue(Pair item);

	public String getLabel() {
		return label;
	}

	public boolean isEditable() {
		return editable;
	}
}