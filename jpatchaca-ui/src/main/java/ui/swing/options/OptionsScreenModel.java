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
		public boolean isRemoteSystemIntegrationActive;
		@Override
		public String toString() {
			return "Data [supressShakingDialog=" + supressShakingDialog
					+ ", issueStatusManagementEnabled="
					+ issueStatusManagementEnabled + ", jiraUrl=" + jiraUrl
					+ ", jiraUserName=" + jiraUserName + ", jiraPassword="
					+ jiraPassword + " +" +
					", isRemoteSystemIntegrationActive="
					+ isRemoteSystemIntegrationActive + "]";
		}
	}
}