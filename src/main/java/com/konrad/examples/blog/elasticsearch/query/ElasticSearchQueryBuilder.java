package com.konrad.examples.blog.elasticsearch.query;

import com.konrad.examples.blog.comments.domain.Comment;

import java.util.Calendar;

public class ElasticSearchQueryBuilder {

    private int from = 0;
    private String[] searchBy;
    private String searchWith;
    private Comment comment;

    public ElasticSearchQueryBuilder(int from) {
        this.from = from;
    }

    public ElasticSearchQueryBuilder(int from, String[] searchBy, String searchWith) {
        this.from = from;
        this.searchBy = searchBy;
        this.searchWith = searchWith;
    }

    public ElasticSearchQueryBuilder(Comment comment) {
        this.comment = comment;
    }

    public String getQuery() {
        return "{" + "\"from\":" + from + ",\"size\":7," + "\"query\":" + "{" +
                getMatchers(searchBy, searchWith) +
                "}," + "\"sort\":{\"date\":{\"order\":\"desc\"}}" + "}";
    }

    public String getCommentQuery() {
        return "{\n" +
                "\"postId\": \"" + comment.getPostId() + "\",\n" +
                "\"name\": \"" + comment.getName() + "\",\n" +
                "\"date\": \"" + comment.getDate() + "\",\n" +
                "\"text\": \"" + comment.getText() + "\",\n" +
                "\"email\": \"" + comment.getEmail() + "\",\n" +
                "\"ip\": \"" + comment.getIp() + "\"\n" +
                "}";
    }

    private String getMatchers(String[] searchBy, String searchWith) {
        StringBuilder matchersQuery = new StringBuilder();
        if (searchBy != null && searchBy.length > 0) {
            matchersQuery.append("\"bool\": {\"should\": [");
            for (String matcher : searchBy) {
                matchersQuery.append("{ \"match\": { \"" + matcher + "\" : \"" + searchWith + "\"} },");
            }
            return matchersQuery.deleteCharAt(matchersQuery.length() - 1).append("]}").toString();
        }
        return matchersQuery.append("\"match_all\" : {}").toString();

    }
}
