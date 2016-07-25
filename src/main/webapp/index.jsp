<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bag Test Server</title>
</head>
<body>
<h2>Description</h2>
<p>Bag Test Server is a simple JSON repeater designed to be part of the test suite for com.brettonw.bag.</p>
<p>Online JSON test servers were proving very unreliable, substantially affecting the software
development process as tests randomly failed. We developed this very simple application to run on a
default Tomcat installation on localhost to provide a more reliable test service.</p>
<p>Some people might find it to be a useful application for themselves, so we are sharing it as a
separate project from the bag library.</p>
<h2>URL</h2>
http://localhost:8080/bag-test-server/api?command=echo&word=hello
<h2>Commands</h2>
<ul>
    <li><em>ip</em>: return a JSON object with the IP address of the requestor.</li>
    <li><em>echo</em>: return a JSON object representing the URL query parameters, with the tag "post-data" containing the JSON from a POST request (if present).</li>
    <li><em>post-data</em>: return a JSON array or object as an echo of the POST data.</li>
</ul>
</body>
</html>
