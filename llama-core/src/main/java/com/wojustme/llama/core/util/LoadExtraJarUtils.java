package com.wojustme.llama.core.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author xurenhe
 * @date 2018/1/30
 */
public final class LoadExtraJarUtils {

    /**
     * 加载额外的jar包
     * @param jarFilePath
     * @return
     * @throws Exception
     */
    public static URLClassLoader loadJars(String jarFilePath) throws Exception {
        File file = new File(jarFilePath);
        URL url= file.toURI().toURL();
        return new URLClassLoader(new URL[] {url});
    }

    /**
     * 获得对应的类
     * @param jarFilePath
     * @param targetClsName
     * @return
     * @throws Exception
     */
    public static Class getTargetCls(String jarFilePath, String targetClsName) throws Exception {
        return loadJars(jarFilePath).loadClass(targetClsName);
    }
}
