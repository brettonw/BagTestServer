package com.brettonw.bagtest;

import com.brettonw.bag.BagObject;
import com.brettonw.bag.BagObjectFrom;
import com.brettonw.servlet.ServletTester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

import static com.brettonw.bagtest.Keys.*;
import static com.brettonw.servlet.Keys.*;
import static org.junit.Assert.assertTrue;

public class Server_Test extends Server {
    private static final Logger log = LogManager.getLogger (Server_Test.class);

    ServletTester servletTester;

    public Server_Test () {
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
