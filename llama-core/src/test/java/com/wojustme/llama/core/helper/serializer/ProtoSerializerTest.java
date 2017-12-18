package com.wojustme.llama.core.helper.serializer;

import com.wojustme.llama.core.util.ProtoStuffUtils;
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
        byte[] bytes = ProtoStuffUtils.toByteArr(person);
        Person rsPerson = ProtoStuffUtils.toBeanObj(bytes, Person.class);
        Assert.assertEquals("xurenhe", rsPerson.getName());
    }

    @Test
    public void test_checkMtliObj() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        Map<String, String>map = new HashMap<>();
        Parent mon = new Parent();
        mon.setName("zhu");
        mon.setAge(50);
        List<Parent> parentList = new ArrayList<>();
        parentList.add(mon);
        person.setParentList(parentList);
        byte[] bytes = ProtoStuffUtils.toByteArr(person);
        Person rsPerson = ProtoStuffUtils.toBeanObj(bytes, Person.class);
        System.out.println(rsPerson);
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
        byte[] bytes = ProtoStuffUtils.toByteArr(personList);
        System.out.println();
    }
}
