// ==UserScript==
// @name           JiraPatchacaIntegration
// @namespace      taksan
// @description    Intercept issue update
// @include        https://jira.objective.com.br/secure/TaskBoard.jspa*
// @include        https://jira.objective.com.br/secure/GetBoardForIssue.jspa*
// @require			http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js
// ==/UserScript==

main();

function main()
{
	addStartButtonsOnTaskBoardIssues();
	interceptProgressToStartIssue();
}

function addStartButtonsOnTaskBoardIssues()
{
	$("[class^='iKey']").after(function(){
		issueKey = this.title.replace(/.*JIRA -/,"");
		cp = $("<br/><input type='button' value='&gt;' title='Time tracker on "+issueKey+"'>").click(function(){
			$.get("http://127.0.0.1:48625/startTask ["+issueKey+ "]");
		})
		return cp;
	})
}

function getAssignee(key)
{
	return	$("#issueDisp_"+key+" div[title^='Assignee']").text().trim()
}

function getCurrentUser()
{
	return $('#header-details-user a[class="lnk"][href="/secure/ViewProfile.jspa"]').text().trim();
}

function interceptProgressToStartIssue() {
	unsafeWindow.Issue.prototype.updatingStatus_old = unsafeWindow.Issue.prototype.updatingStatus;

	unsafeWindow.Issue.prototype.updatingStatus = function(A) {
		if (A == 'gh.boards.inprog_orphan') {
			var assignee = getAssignee(this.key);
			if (getCurrentUser() == assignee) {

				$.get("http://127.0.0.1:48625/startTask ["+this.key+ "]");
			}
		}
		this.updatingStatus_old(A);
	}
}
