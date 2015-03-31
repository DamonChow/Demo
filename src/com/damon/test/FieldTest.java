package com.damon.test;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/1/29 9:33.
 */
public class FieldTest {

    public static void main(String[] args) {
        Teacher t = new Teacher("22");
        Field[] fields = t.getClass().getDeclaredFields();
        for(Field field : fields) {
            Class<?> type = field.getType();
            if (type == String.class) {
                Field.setAccessible(fields, true);
                try {
                    System.out.println(field.getType() + "--" + field.getName() + "---" + field.get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

class Teacher {
    private String id;
    private Date data;
    private int age;

    public void setId(String id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getData() {

        return data;
    }

    public int getAge() {
        return age;
    }

    public String getId() {

        return id;
    }

    public Teacher(String id) {
        this.id = id;
    }
}
