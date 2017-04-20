package com.brettonw.bag;

import com.brettonw.servlet.ServletTester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

import static com.brettonw.bag.service.Keys.*;
import static org.junit.Assert.assertTrue;

public class TestServer_Test extends TestServer {
    private static final Logger log = LogManager.getLogger (TestServer_Test.class);

    public static final String ECHO = "echo";
    public static final String HEADERS = "headers";

    ServletTester servletTester;

    public TestServer_Test () {
        servletTester = new ServletTester (this);
    }

    @Test
    public void testGetIP () throws IOException {
        BagObject query = BagObject.open (EVENT, IP);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.getString (STATUS).equals (OK));
        String ip = response.getBagObject (RESPONSE).getString (IP);
        assertTrue (ip != null);
        log.info (IP + ": " + ip);
    }

    @Test
    public void testGetOk () throws IOException {
        BagObject query = BagObject.open (EVENT, OK);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.getString (STATUS).equals (OK));
    }

    @Test
    public void testGetEcho () throws IOException {
        BagObject query = BagObject.open (EVENT, ECHO);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.equals (query));
    }

    @Test
    public void testGetPostData () throws IOException {
        BagObject query = BagObject.open (EVENT, POST_DATA);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.getString (STATUS).equals (ERROR));
    }

    @Test
    public void testPostEcho () throws IOException {
        BagObject query = BagObject.open (EVENT, ECHO);
        BagObject postData = BagObjectFrom.resource (getClass (), "/testPost.json");
        BagObject response = servletTester.bagObjectFromPost (query, postData);
        query.put (POST_DATA, postData);
        assertTrue (response.equals (query));
    }

    @Test
    public void testPostPostData () throws IOException {
        BagObject query = BagObject.open (EVENT, POST_DATA);
        BagObject postData = BagObjectFrom.resource (getClass (), "/testPost.json");
        BagObject response = servletTester.bagObjectFromPost (query, postData);
        assertTrue (response.equals (postData));
    }

    @Test
    public void testGetHeaders () throws IOException {
        BagObject query = BagObject.open (EVENT, HEADERS);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.getString (STATUS).equals (OK));
    }

    @Test
    public void testEmptyGet () throws IOException {
        BagObject response = servletTester.bagObjectFromGet ("");
        assertTrue (response.getString (STATUS).equals (ERROR));
    }
}
