package com.konrad.examples.blog.posts.input.validators.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InputFilterTest {

    @Test
    public void filterInput() {
        "asdasdas&quot;&quot;".equals((new InputFilter()).validate("asdasdas\"\""));
    }
}