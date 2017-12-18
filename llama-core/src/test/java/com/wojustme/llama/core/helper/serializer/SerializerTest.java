package com.wojustme.llama.core.helper.serializer;

import com.wojustme.llama.core.util.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public class SerializerTest {

    @Test
    public void test_protoObj() throws Exception {

        Person person1 = new Person();
        person1.setName("xurenhe");
        person1.setAge(10);
        Person person2 = new Person();
        person2.setName("xu");
        person2.setAge(5);
        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);

        byte[] serializer = ProtoStuffUtils.toByteArr(personList);
        System.out.println(serializer.length);
        List deserializer = ProtoStuffUtils.toBeanObj(serializer, ArrayList.class);
        System.out.println(deserializer);
    }

    @Test
    public void test_doJson() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        String jsonStr = JsonUtils.toJsonStr(person);
        System.out.println(jsonStr.getBytes().length);
        Person p = JsonUtils.toBeanObj(jsonStr, Person.class);
        System.out.println(p.getName());
    }

    @Test
    public void test_getMapFromYaml() throws Exception{
        String path = Thread.currentThread().getContextClassLoader().getResource("hello.yaml").getPath();
        Person person = YamlUtils.getPropsFromYamlFile(path, Person.class);
        System.out.println(person);
    }

    @Test
    public void test_doDefaultSerializer() throws Exception {
        Person person = new Person();
        byte[] bytes = DefaultSerializerUtils.toByteArray(person);
        Stu rsPerson = DefaultSerializerUtils.toObject(bytes, Stu.class);
        System.out.println(rsPerson);
    }

    @Test
    public void test_serializerMsgPack() throws Exception {
        Person person1 = new Person();
        person1.setName("xurenhe");
        person1.setAge(10);
        Person person2 = new Person();
        person2.setName("xu");
        person2.setAge(5);
        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
        byte[] bytes = MsgPackUtils.serializer(personList);
        List p = MsgPackUtils.deserializer(bytes, List.class);
        System.out.println(p);
    }
}
