package com.lsc.notebook.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 大于标记
 * @Author: luosc
 * @Description:
 * @Date:created in 23:24 2020/4/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Gt {
}
