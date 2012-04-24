// ==UserScript==
// @name           InterceptUpdate
// @namespace      taksan
// @description    Intercept issue update
// @include        https://jira.objective.com.br/secure/TaskBoard.jspa*
// @include        https://jira.objective.com.br/secure/GetBoardForIssue.jspa*
// @require			http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js
// ==/UserScript==

$("[class^='iKey']").after(function(){
	var issueKey = this.title.replace(/.*JIRA -/,"");
	var nl='<br/>';
	if ($(this).parent().attr('class') != 'list-subheader'){
		nl = '';
	}

	cp = $(nl+"<input type='button' value='&gt;' title='Time tracker on "+issueKey+"'>");
	cp.click(function(){
		$.get("http://127.0.0.1:48625/startTask ["+issueKey+ "]");
	})
    return cp;
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
			$.get("http://127.0.0.1:48625/startTask ["+this.key+"]");
		}
	}
	this.updatingStatus_old(A);
}
