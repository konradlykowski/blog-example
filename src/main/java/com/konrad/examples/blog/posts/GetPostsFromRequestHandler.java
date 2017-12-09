package com.konrad.examples.blog.posts;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.repository.PostsRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class GetPostsFromRequestHandler implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public GetPostsFromRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public GetPostsFromRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        try {
            int from = getFromParam(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
            JSONObject responseBody = new JSONObject();
            responseBody.put("posts", (new PostsRepository(new ElasticSearchClient(), new RestTemplate()).getAllPosts(from)));
            responseJson.put("body", responseBody.toString());
        } catch (ParseException | IOException e) {
            responseJson.put("statusCode", "400");
        } finally {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }

    private int getFromParam(BufferedReader reader) throws IOException, ParseException {
        int from = 0;
        JSONObject event = (JSONObject) parser.parse(reader);
        if (event.get("queryStringParameters") != null) {
            JSONObject qps = (JSONObject) event.get("queryStringParameters");
            if (qps.get("from") != null) {
                from = Integer.valueOf(qps.get("from").toString());
            }
        }
        return from;
    }


}
