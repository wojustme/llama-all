package com.wojustme.llama.test;

import com.wojustme.llama.util.HttpFileUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xurenhe
 * @date 2018/2/4
 */
public class HttpFileUtilsTest {

    @Test
    public void test_postFile() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hello", "world");
        HttpFileUtils.uploadFile("http://localhost:10001/upload", map, new File("/Users/xurenhe/Downloads/test-upload.zip"));
    }
}
