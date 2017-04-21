package com.konrad.examples.blog.posts.filter;

public enum FilterFields {
    category("category"), location("location"), tag("tag"), all("all");

    private final String filterName;

    FilterFields(String filterName) {
        this.filterName = filterName;
    }


}
