package ui.swing.options;

import lang.Maybe;

public interface OptionsScreenModel {

	Data readDataFromSystem();

	void writeDataIntoSystem(Data data);

	public static class Data {
		public boolean supressShakingDialog;
		public boolean issueStatusManagementEnabled;
		public Maybe<String> jiraUrl;
		public Maybe<String> jiraUserName;
		public Maybe<String> jiraPassword;
		public boolean isToUseOldIcons;

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Data other = (Data) obj;
			if (isToUseOldIcons != other.isToUseOldIcons)
				return false;
			if (issueStatusManagementEnabled != other.issueStatusManagementEnabled)
				return false;
			if (jiraPassword != null && other.jiraPassword != null) {
				if (!jiraPassword.unbox().equals(other.jiraPassword.unbox()))
					return false;
			} else if (jiraPassword != other.jiraPassword)
				return false;
			if (jiraUrl != null && other.jiraUrl != null) {
				if (!jiraUrl.unbox().equals(other.jiraUrl.unbox()))
					return false;
			} else if (jiraUrl != other.jiraUrl)
				return false;
			if (jiraUserName != null && other.jiraUserName != null) {
				if (!jiraUserName.unbox().equals(other.jiraUserName.unbox()))
					return false;
			} else if (jiraUserName != other.jiraUserName)
				return false;
			if (supressShakingDialog != other.supressShakingDialog)
				return false;
			return true;
		}
	}
}