// ==UserScript==
// @name           start task
// @namespace      org.jpatchaca
// @description    Start Task
// @include        https://jira.objective.com.br/browse/*
// @require       http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js
// ==/UserScript==

main();

function main (){
        issueId = $('[id^=issue_key]').text();

        $('body').prepend(
                $('<input type="button" value="Start task">').click(function() {
			$.get("http://127.0.0.1:48625/startTask ["+issueId+ "]");
        }))
}
