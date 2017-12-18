package com.wojustme.llama.core.helper.serializer;

import com.dyuproject.protostuff.Tag;

import java.io.Serializable;
import java.util.List;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public class Person implements Serializable {
    @Tag(1)
    private String name;
    @Tag(2)
    private int age;
    @Tag(3)
    List<Parent> parentList;
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

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", parentList=" + parentList +
                '}';
    }
}
