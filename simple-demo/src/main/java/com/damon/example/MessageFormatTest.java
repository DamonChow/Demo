package com.damon.example;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-25 10:27
 */
public class MessageFormatTest {

    @Test
    public void format() {
        String patt = "我是{0}sdfsdfsdf{1}";
        System.out.println(MessageFormat.format(patt, 1,23));
    }
}
