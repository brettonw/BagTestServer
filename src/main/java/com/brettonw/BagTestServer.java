package com.brettonw;

import com.brettonw.bag.Bag;
import com.brettonw.bag.BagArray;
import com.brettonw.bag.BagObject;
import com.brettonw.servlet.ServletBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BagTestServer extends ServletBase {
    private static final Logger log = LogManager.getLogger (BagTestServer.class);

    public static final String ECHO_KEY = "echo";
    public static final String IP_KEY = "ip";

    public void handleCommand (String command, BagObject query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (command) {
            case ECHO_KEY:
                makeJsonResponse (response, query);
                break;
            case IP_KEY:
                makeJsonResponse (response, new BagObject ().put (IP_KEY, request.getRemoteAddr ().toString ()));
                break;
            case POST_DATA_KEY:
                if (query.has (POST_DATA_KEY)) {
                    // get the post data
                    Bag postData = query.getBagArray (POST_DATA_KEY);
                    if (postData == null) {
                        postData = query.getBagObject (POST_DATA_KEY);
                        if (postData != null) {
                            postData = new BagArray ().add (postData);
                        }
                    }

                    // if we got post data...
                    if (postData != null) {
                        makeJsonResponse (response, postData);
                    } else {
                        makeErrorResponse (query, response, "Invalid post data");
                    }
                } else {
                    makeErrorResponse (query, response, "No post data");
                }
                break;
        }
    }
}
