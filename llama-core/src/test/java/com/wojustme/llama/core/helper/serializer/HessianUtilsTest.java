package com.wojustme.llama.core.helper.serializer;

import com.wojustme.llama.core.util.HessianUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xurenhe
 * @date 2017/12/16
 */
public class HessianUtilsTest {
    @Test
    public void test_checkMtliObj() throws Exception {
        Person person = new Person();
        person.setName("xurenhe");
        person.setAge(10);
        Map<String, String> map = new HashMap<>();
        Parent mon = new Parent();
        mon.setName("zhu");
        mon.setAge(50);
        List<Parent> parentList = new ArrayList<>();
        parentList.add(mon);
        byte[] bytes = HessianUtils.toByteArr(person);
        Person rsPerson = HessianUtils.toBeanObj(bytes, Person.class);
        System.out.println(rsPerson);
    }

    @Test
    public void test_checkString() throws Exception {
        String s = "Hello";
        byte[] bytes = HessianUtils.toByteArr(s);
        String s1 = HessianUtils.toBeanObj(bytes, String.class);

        System.out.println(s1);
    }
}
