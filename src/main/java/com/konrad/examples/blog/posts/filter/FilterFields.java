package com.konrad.examples.blog.posts.filter;

public enum FilterFields {
    CATEGORY("category"), LOCATION("location"), TAG("tag"), ALL("all");

    private final String filterName;

    FilterFields(String filterName) {
        this.filterName = filterName;
    }


}
