package com.konrad.examples.blog.posts.input.validators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultInputValidatorTest {

    @Test
    public void validateInput() {
        Assert.assertTrue("asd&#39;a&quot;s&lt;d".equals((new DefaultInputValidator()).validate("asd'a\"s<d")));
    }

}