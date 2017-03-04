package com.konrad.examples.blog.posts.filter;

public enum FilterFields {
    postId("postId"), content("content"), description("description"), category("category"), location("location"), tags("tags"), all("all"), carousel("carousel");

    private final String filterName;

    FilterFields(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return this.filterName;
    }

}
