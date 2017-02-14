package com.brettonw.bagtest;

import com.brettonw.bag.Bag;
import com.brettonw.bag.BagObject;
import com.brettonw.servlet.Base;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static com.brettonw.bagtest.Keys.ECHO;
import static com.brettonw.bagtest.Keys.HEADERS;
import static com.brettonw.bagtest.Keys.IP;
import static com.brettonw.servlet.Keys.OK;
import static com.brettonw.servlet.Keys.POST_DATA;

public class Server extends Base {
    private static final Logger log = LogManager.getLogger (Server.class);

    public Server () {
        onEvent (OK, event -> event.ok ());

        onEvent (ECHO, event -> event.respondJson (event.getQuery ()));

        onEvent (IP, event -> {
            HttpServletRequest request = event.getRequest ();
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
            event.ok (BagObject.open (IP, ip));
        });

        onEvent (POST_DATA, event -> {
            BagObject query = event.getQuery ();
            if (query.has (POST_DATA)) {
                // get the post data
                Bag postData = query.getBagArray (POST_DATA);
                if (postData == null) {
                    postData = query.getBagObject (POST_DATA);
                }

                // if we got post data...
                if (postData != null) {
                    event.respondJson (postData);
                } else {
                    event.error ("Invalid post data");
                }
            } else {
                event.error ("No post data");
            }
        });

        onEvent (HEADERS, event -> {
            HttpServletRequest request = event.getRequest ();
            BagObject responseBagObject = new BagObject ();
            Enumeration headerNames = request.getHeaderNames ();
            while (headerNames.hasMoreElements ()) {
                String headerName = (String) headerNames.nextElement ();
                String headerValue = request.getHeader (headerName);
                responseBagObject.put (headerName, headerValue);
            }
            event.ok (responseBagObject);
        });
    }
}
