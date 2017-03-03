package com.konrad.examples.blog.posts.filter.adapter;

import com.konrad.examples.blog.posts.filter.FilterFields;

public class FilterFieldsToESFieldsAdapter {

    private final String searchBy;

    public FilterFieldsToESFieldsAdapter(String searchBy) {
        this.searchBy = searchBy;
    }

    public FilterFields[] getESFields() {
        if (FilterFields.all.getFilterName().equals(searchBy)) {
            return FilterFields.values();
        }
        FilterFields[] oneField = {FilterFields.valueOf(searchBy)};
        return oneField;
    }
}
