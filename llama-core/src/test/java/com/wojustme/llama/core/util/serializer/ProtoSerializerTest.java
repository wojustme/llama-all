package com.wojustme.llama.core.util.serializer;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xurenhe
 * @date 2017/12/12
 */
public class ProtoSerializerTest {

    @Test
    public void test_checkObj() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        byte[] bytes = ProtoStuffUtils.serializer(person);
        Person rsPerson = ProtoStuffUtils.deserializer(bytes, Person.class);
        Assert.assertEquals("xurenhe", rsPerson.getName());
    }

    @Test
    public void test_checkMtliObj() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        Map<String, String>map = new HashMap<>();
        map.put("xiaoming", "good");
        map.put("xiaohong", "bad");
        person.setClassmates(map);
        byte[] bytes = ProtoStuffUtils.serializer(person);
        Person rsPerson = ProtoStuffUtils.deserializer(bytes, Person.class);
        Assert.assertEquals("good", rsPerson.getClassmates().get("xiaoming"));
    }

    @Test
    public void test_checkList() throws Exception {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person person = new Person();
            person.setName("xurenhe" + i);
            person.setAge(i);
            personList.add(person);
        }
        byte[] bytes = ProtoStuffUtils.serializer(personList);
        System.out.println();
    }
}
