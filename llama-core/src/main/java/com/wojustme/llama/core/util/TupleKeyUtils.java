package com.wojustme.llama.core.util;

import java.util.Random;

/**
 * @author xurenhe
 * @date 2018/2/23
 */
public class TupleKeyUtils {

    /**
     * 利用Java自带的string中hashcode方法
     * @param keyStr
     * @param hashNum
     * @return
     */
    public static int computeHashKey(String keyStr, int hashNum) {
        return  Math.abs(keyStr.hashCode()) % hashNum;
    }

    /**
     * 随机
     * @param num
     * @return
     */
    public static int randomKey(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }
}
