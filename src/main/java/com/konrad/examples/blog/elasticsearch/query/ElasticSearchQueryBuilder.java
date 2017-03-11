package com.konrad.examples.blog.elasticsearch.query;

import com.konrad.examples.blog.comments.domain.Comment;
import com.konrad.examples.blog.posts.filter.FilterFields;

import java.util.Calendar;

public class ElasticSearchQueryBuilder {

    private int from = 0;
    private FilterFields[] searchBy;
    private String searchWith;
    private Comment comment;
    private int size;

    public ElasticSearchQueryBuilder(int from) {
        this.from = from;
        this.size = 7;
    }

    public ElasticSearchQueryBuilder(int from, FilterFields[] searchBy, String searchWith) {
        this.from = from;
        this.searchBy = searchBy;
        this.searchWith = searchWith;
        this.size = 7;
    }

    public ElasticSearchQueryBuilder(int from, FilterFields[] searchBy, String searchWith, int size) {
        this.from = from;
        this.searchBy = searchBy;
        this.searchWith = searchWith;
        this.size = size;
    }

    public ElasticSearchQueryBuilder(Comment comment) {
        this.comment = comment;
    }

    public String getQuery() {
        return "{" + "\"from\":" + from + ",\"size\":" + +size + "," + "\"query\":" + "{" +
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

    private String getMatchers(FilterFields[] searchBy, String searchWith) {
        StringBuilder matchersQuery = new StringBuilder();
        if (searchBy != null && searchBy.length > 0) {
            matchersQuery.append("\"bool\": {\"should\": [");
            for (FilterFields matcher : searchBy) {
                matchersQuery.append("{ \"match\": { \"" + matcher.getFilterName() + "\" : \"" + searchWith + "\"} },");
            }
            return matchersQuery.deleteCharAt(matchersQuery.length() - 1).append("]}").toString();
        }
        return matchersQuery.append("\"match_all\" : {}").toString();

    }
}
