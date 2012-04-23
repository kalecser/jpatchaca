// ==UserScript==
// @name           start task
// @namespace      org.jpatchaca
// @description    Start Task
// @include        https://jira.objective.com.br/browse/*
// @require       http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js
// @require       https://raw.github.com/taksan/objective-monkey-utils/master/jiraToolbar.js
// ==/UserScript==

main();

function main (){
        issueId = $('[id^=issue_key]').text();

//        $('body').prepend(
			addToToolbar(        
                $('<input type="button" value="Start task" style="position:fixed;top:5px;left:5px;">').click(function() {
					$.get("http://127.0.0.1:48625/startTask ["+issueId+ "]")
	        }))
//        )
}
