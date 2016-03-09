package com.damon.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/3/11 10:44.
 */
public class StringTest {

    private static final Logger log = LoggerFactory.getLogger(StringTest.class);

	static String msg = "ValidationException: The following exception occured while validating field 'header'" +
			" of class 'com.construct.jsjjgpt.model.req.ReqPacket';" +
			"   - location of error: XPATH: /ReqPacket/Header\n" +
			"The field 'requestId' (whose xml name is 'requestId') is a required field" +
			" of class 'com.construct.jsjjgpt.model.req.Header'";
    public static void main(String[] args) {
        System.out.println("20010052".substring(4,8));
        System.out.println(4>>>2);
        System.out.println(4<<2);
        System.out.println(4<<1);
        System.out.println(4>>>1);
        System.out.println(4>>1);
        System.out.println(7>>>1);
        System.out.println(7>>1);
        //System.out.println(Integer.parseInt(""));   NumberFormatException
		String regEx = "of class '(.*)'";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(msg);
		while(mat.find()) {
			System.out.println(mat.group(1));//m.group(1)不包括这两个字符
		}

		/*for(int i=0;i<=mat.groupCount();i++){
			System.out.println(mat.group(i));
		}*/
        log.info("test");
	}

    @Test
    public void test() {
        System.out.println(StringUtils.join(new String[]{"ttt"}, ","));
    }
}
