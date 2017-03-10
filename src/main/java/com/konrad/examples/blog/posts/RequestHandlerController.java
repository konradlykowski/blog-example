package com.konrad.examples.blog.posts;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.response.writer.ResponseHeaderCorsDecorated;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.spec.ECField;
import java.util.Map;

public class RequestHandlerController implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public RequestHandlerController() {
        restTemplate = new RestTemplate();
    }

    public RequestHandlerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler of ProxyWithStream");


        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();
        String name = "you";
        String city = "World";
        String time = "day";
        String day = null;
        String responseCode = "200";
        String pathh = "blank";
        String requestt = "blank";

        try {
            JSONObject event = (JSONObject) parser.parse(reader);
            if (event.get("queryStringParameters") != null) {
                JSONObject qps = (JSONObject) event.get("queryStringParameters");
                if (qps.get("time") != null) {
                    time = (String) qps.get("time");
                }
            }

            if (event.get("pathParameters") != null) {
                JSONObject pps = (JSONObject) event.get("pathParameters");
                if (pps.get("postId") != null) {
                    city = (String) pps.get("proxy");
                }
            }

            if (event.get("path") != null) {
                try {
                    JSONObject path = (JSONObject) parser.parse((String) event.get("path"));
                    if (path.get("path") != null) {
                        pathh = (String) path.get("path");
                    }
                } catch (Exception e) {
                    pathh = ((String) event.get("path"));
                }
            }

            if (event.get("requestContext") != null) {
                try {
                    JSONObject request = (JSONObject) parser.parse((String) event.get("requestContext"));
                    JSONObject identity = (JSONObject) parser.parse((String) request.get("identity"));
                    requestt = identity.toString();
                } catch (Exception e) {
                    requestt = requestt + "--" + event.get("requestContext").toString();
                }
            }

            if (event.get("headers") != null) {
                JSONObject hps = (JSONObject) event.get("headers");
                if (hps.get("day") != null) {
                    day = (String) hps.get("header");
                }
            }

            if (event.get("body") != null) {
                JSONObject body = (JSONObject) parser.parse((String) event.get("body"));
                if (body.get("callerName") != null) {
                    name = (String) body.get("callerName");
                }
            }

            String greeting = "Good ";
            if (day != null || day != "") greeting += "Happy " + day + "!";


            JSONObject responseBody = new JSONObject();
            responseBody.put("input", event.toJSONString());
            responseBody.put("message", greeting);
            responseBody.put("message2", pathh);
            responseBody.put("message3", requestt);


            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-response-header", "my custom response header value");

            responseJson.put("statusCode", responseCode);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());

        } catch (ParseException pex) {
            responseJson.put("statusCode", "400");
            responseJson.put("exception", pex);
        }

        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        responseJson.put("headers", (new ResponseHeaderCorsDecorated()).getHeaders());
        writer.write(responseJson.toJSONString());
        writer.close();
    }


}
