## Description

Bag Test Server is a simple JSON repeater designed to be part of the test suite for com.brettonw.bag.

Online JSON test servers were proving very unreliable, substantially affecting the software development process as tests randomly failed. We developed this very simple application to run on a default Tomcat installation on localhost to provide a more reliable test service.

Some people might find it to be a useful application for themselves, so we are sharing it as a separate project from the bag library.

## URL

http://localhost:8080/bag-test-server/api?command=echo&word=hello

## Commands

- **ip:** return a JSON object with the IP address of the requestor.
- **echo:** return a JSON object representing the parameters passed in. If it's a GET request, the response is the URL query parameters. If it's a POST request, the response is the posted JSON.
- **data:** return a JSON array as an echo of the "data" value in the POST data.
