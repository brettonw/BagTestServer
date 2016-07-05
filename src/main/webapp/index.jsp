<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bag Test Server</title>
</head>
<body>
<h2>Commands</h2>
<ul>
    <li><em>ip</em>: return a JSON object with the IP address of the requestor.</li>
    <li><em>echo</em>: return a JSON object representing the parameters passed in. If it's a GET request,
        the response is the URL query parameters. If it's a POST request, the response is the posted JSON.</li>
    <li><em>data</em>: return a JSON array as an echo of the "data" value in the POST data.</li>
</ul>
</body>
</html>
