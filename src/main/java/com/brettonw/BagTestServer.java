package com.brettonw;

import com.brettonw.bag.Bag;
import com.brettonw.bag.BagObject;
import com.brettonw.servlet.ServletBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class BagTestServer extends ServletBase {
    private static final Logger log = LogManager.getLogger (BagTestServer.class);

    public static final String ECHO_KEY = "echo";
    public static final String IP_KEY = "ip";
    public static final String HEADERS_KEY = "headers";

    public void handleCommand (BagObject query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info ("Command: " + query.getString (COMMAND_KEY));
        switch (query.getString (COMMAND_KEY)) {
            case ECHO_KEY:
                makeJsonResponse (response, query);
                break;
            case IP_KEY: {
                String ip = request.getRemoteAddr ();
                if (ip.startsWith ("127") || ip.startsWith ("0")) {
                    // try to get the x-forwarded header, the last one...
                    String forwarding = request.getHeader ("x-forwarded-for");
                    if (forwarding != null) {
                        String[] forwards = forwarding.split (",");
                        for (String forward : forwards) {
                            forward = forward.trim ();
                            ip = forward.split (":")[0];
                        }
                    }
                }
                makeSuccessResponse (query, response, new BagObject ().put (IP_KEY, ip));
                break;
            }
            case POST_DATA_KEY:
                if (query.has (POST_DATA_KEY)) {
                    // get the post data
                    Bag postData = query.getBagArray (POST_DATA_KEY);
                    if (postData == null) {
                        postData = query.getBagObject (POST_DATA_KEY);
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
            case HEADERS_KEY: {
                BagObject responseBagObject = new BagObject ();
                Enumeration headerNames = request.getHeaderNames ();
                while (headerNames.hasMoreElements ()) {
                    String headerName = (String) headerNames.nextElement ();
                    String headerValue = request.getHeader (headerName);
                    responseBagObject.put (headerName, headerValue);
                }
                makeSuccessResponse (query, response, responseBagObject);
                break;
            }
            default:
                makeErrorResponse (query, response, "Unknown command");
                break;
        }
    }
}
