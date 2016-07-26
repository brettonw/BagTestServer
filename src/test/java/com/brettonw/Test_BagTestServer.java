package com.brettonw;

import com.brettonw.bag.BagObject;
import com.brettonw.bag.BagObjectFrom;
import com.brettonw.servlet.ServletTester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class Test_BagTestServer extends BagTestServer {
    private static final Logger log = LogManager.getLogger (Test_BagTestServer.class);

    ServletTester servletTester;

    public Test_BagTestServer () {
        servletTester = new ServletTester (this);
    }


    @Test
    public void testGetIP () throws IOException {
        BagObject query = new BagObject ().put (COMMAND_KEY, IP_KEY);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.getString (STATUS_KEY).equals (OK_KEY));
        String ip = response.getBagObject (RESPONSE_KEY).getString (IP_KEY);
        assertTrue (ip != null);
        log.info (IP_KEY + ": " + ip);
    }

    @Test
    public void testGetEcho () throws IOException {
        BagObject query = new BagObject ().put (COMMAND_KEY, ECHO_KEY);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.equals (query));
    }

    @Test
    public void testGetPostData () throws IOException {
        BagObject query = new BagObject ().put (COMMAND_KEY, POST_DATA_KEY);
        BagObject response = servletTester.bagObjectFromGet (query);
        assertTrue (response.getString (STATUS_KEY).equals (ERROR_KEY));
    }

    @Test
    public void testPostEcho () throws IOException {
        BagObject query = new BagObject ().put (COMMAND_KEY, ECHO_KEY);
        BagObject postData = BagObjectFrom.resource (getClass (), "/testPost.json");
        BagObject response = servletTester.bagObjectFromPost (query, postData);
        query.put (POST_DATA_KEY, postData);
        assertTrue (response.equals (query));
    }

    @Test
    public void testPostPostData () throws IOException {
        BagObject query = new BagObject ().put (COMMAND_KEY, POST_DATA_KEY);
        BagObject postData = BagObjectFrom.resource (getClass (), "/testPost.json");
        BagObject response = servletTester.bagObjectFromPost (query, postData);
        assertTrue (response.equals (postData));
    }

    @Test
    public void testEmptyGet () throws IOException {
        BagObject response = servletTester.bagObjectFromGet ("");
        assertTrue (response.getString (STATUS_KEY).equals (ERROR_KEY));
    }
}
