package com.damon.example.invoke;

import java.lang.reflect.Field;

/**
 * Created by damon on 2017/6/16.
 */
public class SimpleTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
        PrivateObject privateObject = new PrivateObject("The Private Value");
        Field privateStringField = PrivateObject.class.getDeclaredField("privateString");

        privateStringField.setAccessible(true);
        String fieldValue = (String) privateStringField.get(privateObject);
        System.out.println("fieldValue = " + fieldValue);

        privateStringField.set(privateObject, "As you see,privateString's value is changed!");
        String fieldValue1 = (String) privateStringField.get(privateObject);
        System.out.println("fieldValue = " + fieldValue1);

        privateStringField.setAccessible(false);
        privateStringField.set(privateObject, "As you see,privateString's value is changed!2");
        String fieldValue2 = (String) privateStringField.get(privateObject);
        System.out.println("fieldValue = " + fieldValue2);
    }

}