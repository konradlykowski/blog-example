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
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        JSONObject responseJson = new JSONObject();
        int from = 0;
        try {
            JSONObject event = (JSONObject) parser.parse(reader);
            if (event.get("queryStringParameters") != null) {
                JSONObject qps = (JSONObject) event.get("queryStringParameters");
                if (qps.get("from") != null) {
                    from = Integer.valueOf(qps.get("from").toString());
                }
            }
            JSONObject responseBody = new JSONObject();
            try {
                responseBody.put("posts", (new PostsRepository(new ElasticSearchClient(), new RestTemplate()).getAllPosts(from)));
            } catch (Exception e) {
                e.printStackTrace();
                context.getLogger().log(e.getMessage());
            }
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
