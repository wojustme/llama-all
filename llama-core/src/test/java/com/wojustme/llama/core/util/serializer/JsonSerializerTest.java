package com.wojustme.llama.core.util.serializer;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xurenhe
 * @date 17/12/12
 */
public class JsonSerializerTest {

    @Test
    public void test_checkMtliObj() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        Map<String, String> map = new HashMap<>();
        map.put("xiaoming", "good");
        map.put("xiaohong", "bad");
        person.setClassmates(map);
        String jsonStr = JsonUtils.toJsonStr(person);
        Person rsPerson = JsonUtils.toBeanObj(jsonStr, Person.class);
        Assert.assertEquals("good", rsPerson.getClassmates().get("xiaoming"));
    }
}
