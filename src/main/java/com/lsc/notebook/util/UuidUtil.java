package com.lsc.notebook.util;

import java.util.UUID;

/**
 * 生成UUid
 * @Author: luosc
 * @Description:
 * @Date:created in 9:50 2020-01-08
 */
public class UuidUtil {

    /**
     * 32位UUID
     * return  
     * Author luosc
     * param 
     * Date 2020-01-08 9:51
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
