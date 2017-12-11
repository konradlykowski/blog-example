package com.konrad.examples.blog;

import com.amazonaws.services.lambda.runtime.Context;
import com.konrad.examples.blog.posts.filter.FilterFields;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class SearchPostsFromRequestHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public SearchPostsFromRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public SearchPostsFromRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        JSONObject responseJson = new JSONObject();
        int from = 0;
        String searchBy = "";
        String query = "";
        try {
            JSONObject event = (JSONObject) parser.parse(reader);
            if (event.get("queryStringParameters") != null) {
                JSONObject qps = (JSONObject) event.get("queryStringParameters");
                if (qps.get("from") != null) {
                    from = Integer.valueOf(qps.get("from").toString());
                }
            }

            if (event.get("body") != null) {
                JSONObject body = (JSONObject) parser.parse((String) event.get("body"));
                if (body.get("searchBy") != null) {
                    searchBy = (String) body.get("searchBy");
                }
                if (body.get("query") != null) {
                    query = (String) body.get("query");
                }
            }
            JSONObject responseBody = new JSONObject();
            responseBody.put("posts", "getting from" + from);
            responseJson.put("body", responseBody.toString());

        } catch (ParseException pex) {
            responseJson.put("statusCode", "400");
            responseJson.put("exception", pex);
        } finally {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }
}
