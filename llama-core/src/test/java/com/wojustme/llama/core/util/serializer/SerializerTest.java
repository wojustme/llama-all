package com.wojustme.llama.core.util.serializer;

import org.junit.Test;

/**
 * @author xurenhe
 * @date 2017/11/29
 */
public class SerializerTest {

    @Test
    public void test_protoObj() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        byte[] serializer = ProtoStuffUtils.serializer(person);
        System.out.println(serializer.length);
        Person deserializer = ProtoStuffUtils.deserializer(serializer, Person.class);
        System.out.println(deserializer.getName());
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
}
