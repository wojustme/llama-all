package com.wojustme.llama.core.util.serializer;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public class Person implements Serializable {
    private String name;
    private int age;
    private Map<String, String> classmates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, String> getClassmates() {
        return classmates;
    }

    public void setClassmates(Map<String, String> classmates) {
        this.classmates = classmates;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", classmates=" + classmates +
                '}';
    }
}
