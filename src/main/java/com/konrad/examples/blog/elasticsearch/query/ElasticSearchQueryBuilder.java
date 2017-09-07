package com.konrad.examples.blog.elasticsearch.query;

public class ElasticSearchQueryBuilder {

    private int from = 0;
    private String[] searchBy;
    private String searchWith;

    public ElasticSearchQueryBuilder(int from) {
        this.from = from;
    }

    public ElasticSearchQueryBuilder(int from, String[] searchBy, String searchWith) {
        this.from = from;
        this.searchBy = searchBy;
        this.searchWith = searchWith;
    }

    public String getQuery() {
        return "{" + "\"from\":" + from + ",\"size\":7," + "\"query\":" + "{" +
                getMatchers(searchBy, searchWith) +
                "}," + "\"sort\":{\"date\":{\"order\":\"desc\"}}" + "}";
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
