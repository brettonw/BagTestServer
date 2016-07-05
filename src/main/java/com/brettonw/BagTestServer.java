package com.brettonw;

import com.brettonw.bag.BagObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BagTestServer extends HttpServlet {
    private static final Logger log = LogManager.getLogger (BagTestServer.class);

    @Override
    public void init (ServletConfig config) throws ServletException {
        super.init (config);
    }

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BagObject query = parseQuery (request);
        doRequest (query, null, request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BagObject query = parseQuery (request);

        // extract the parameters
        // XXX this will change as soon as I can do a new release of Bag, which is why I'm writing this...
        BagObject parameters = new BagObject (request.getInputStream ());

        doRequest (query, parameters, request, response);
    }

    private BagObject parseQuery (HttpServletRequest request) {
        BagObject query = new BagObject ();
        String queryString = request.getQueryString ();
        String[] queryParameters = queryString.split ("&");
        for (String queryParameter : queryParameters) {
            String[] pair = queryParameter.split ("=");
            query.put (pair[0], pair[1]);
        }
        return query;
    }

    private void doRequest (BagObject query, BagObject parameters, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // set the response to be JSON
        response.setContentType ("application/json;charset=UTF-8");
        response.setCharacterEncoding ("UTF-8");

        String responseString = "BOGUS";
        switch (query.getString ("command", () -> "ip")) {
            case "ip":
                responseString = new BagObject ().put ("ip", request.getRemoteAddr ()).toString ();
                break;
            case "echo":
                if (parameters != null) {
                    responseString = parameters.toString ();
                } else {
                    responseString = query.toString ();
                }
                break;
        }

        PrintWriter out = response.getWriter ();
        out.println (responseString);
        //out.flush ();
        out.close ();
    }
}