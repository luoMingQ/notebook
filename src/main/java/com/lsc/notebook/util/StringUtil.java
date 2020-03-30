package com.lsc.notebook.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 15:31 2020/3/29
 */
public class StringUtil {
    /**
     * 16进制字符
     */
    static String[] chars = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * 生成6位随机数
     * return
     * Author luosc
     * param
     * Date 2020/1/13 18:15
     */
    public static int getRandomNum() {
        return (int)((Math.random()*9+1)*100000);
    }

    /**
     * MD5加密
     * return
     * Author luosc
     * param
     * Date 2020/3/9 16:48
     */
    public static String StringInMd5(String str) {
        // 消息签名（摘要）
        MessageDigest md5 = null;
        try {
            // 参数代表的是算法名称
            md5 = MessageDigest.getInstance("md5");
            byte[] result = md5.digest(str.getBytes());

            StringBuilder sb = new StringBuilder(32);
            // 将结果转为16进制字符  0~9 A~F
            for (int i = 0; i < result.length; i++) {
                // 一个字节对应两个字符
                byte x = result[i];
                // 取得高位
                int h = 0x0f & (x >>> 4);
                // 取得低位
                int l = 0x0f & x;
                sb.append(chars[h]).append(chars[l]);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
