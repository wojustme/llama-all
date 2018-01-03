package com.wojustme.llama.core.helper.serializer;

import com.dyuproject.protostuff.Tag;
import com.google.gson.Gson;
import com.wojustme.llama.core.util.ProtoStuffUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public class Person implements Serializable {

    private int status;
    private String name;
    private int age;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static void main(String[] args) throws Exception {
//        Person person = new Person();
//        person.setName("hello");
//        person.setAge(10);
//        byte[] bytes = ProtoStuffUtils.toByteArr(person);
//        System.out.println(new Gson().toJson(bytes));
//
        byte[] bytes = {10,5,104,101,108,108,111,16,10};
        Person person = ProtoStuffUtils.toBeanObj(bytes, Person.class);
        System.out.println(person);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", status=" + status +
                '}';
    }
}
