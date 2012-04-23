// ==UserScript==
// @name           InterceptUpdate
// @namespace      taksan
// @description    Intercept issue update
// @include        https://jira.objective.com.br/secure/TaskBoard.jspa*
// @include        https://jira.objective.com.br/secure/GetBoardForIssue.jspa*
// @require			http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js
// ==/UserScript==

$("[class^='iKey']").after(function(){
	issueKey = this.title.replace(/.*JIRA -/,"");
    return "<br/><input type='button' value='&gt;' title='Time tracker on "+issueKey+"'>"
})
function getAssignee(key)
{
	return	$("#issueDisp_"+key+" div[title^='Assignee']").text().trim()
}

function getCurrentUser()
{
	return $('#header-details-user a[class="lnk"][href="/secure/ViewProfile.jspa"]').text().trim();
}

unsafeWindow.Issue.prototype.updatingStatus_old = unsafeWindow.Issue.prototype.updatingStatus;

unsafeWindow.Issue.prototype.updatingStatus = function(A) {
	if (A == 'gh.boards.inprog_orphan') {
		var assignee = getAssignee(this.key);
		if (getCurrentUser() == assignee) {
			alert("Issue " + this.key + " set in progress. Assignee: " + getAssignee(this.key) + '. Will start activity on time tracker');
		}
	}
//	this.updatingStatus_old(A);
}

