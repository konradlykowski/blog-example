package com.konrad.examples.blog.posts.filter.adapter;

import com.konrad.examples.blog.posts.filter.FilterFields;

public class FilterFieldsToESFieldsAdapter {

    private final String searchBy;
    private final static String[] esFields = {"tag", "category", "content", "location", "description"};

    public FilterFieldsToESFieldsAdapter(String searchBy) {
        this.searchBy = searchBy;
    }

    public String[] getESFields() {
        if (FilterFields.ALL.equals(searchBy)) {
            return esFields;
        }
        String[] oneField = {searchBy};
        return oneField;
    }
}
