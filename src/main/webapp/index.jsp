<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bag Test Server</title>
</head>
<body style="font-family:sans-serif;">
<h2>Description</h2>
<p>The Bag Test Server is a simple JSON repeater designed to be used in test suites requiring JSON
    responses from a remote host.</p>
<h2>URL</h2>
<a href="api?command=echo&word=hello">http://(host)/bag-test-server/api?command=echo&word=hello</a>
<h2>Commands</h2>
<ul>
    <li><em><a href="api?command=ip">ip</a></em>: return a JSON object with the IP address of the requestor.</li>
    <li><em><a href="api?command=echo&param1=1&param2=2">echo</a></em>: return a JSON object representing the URL query parameters, with the tag "post-data" containing the JSON from a POST request (if present).</li>
    <li><em>post-data</em>: return a JSON array or object as an echo of the POST data.</li>
    <li><em><a href="api?command=headers">headers</a></em>: return a JSON array of the headers supplied in the request.</li>
</ul>
</body>
</html>
