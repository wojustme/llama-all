package com.wojustme.llama.core.util.serializer;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * yaml文件工具类
 * @author xurenhe
 * @date 2017/12/3
 */
public final class YamlUtils {

    public static <T> T getPropsFromYamlFile(String filePath, Class<T> cls) throws SerializerException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new SerializerException("file not found", e);
        }
        YamlReader reader = new YamlReader(fileReader);
        try {
            return reader.read(cls);
        } catch (YamlException e) {
            throw new SerializerException("can not seriliaize this class type: " + cls.getName(), e);
        }
    }
}
