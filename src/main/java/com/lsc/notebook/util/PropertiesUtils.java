package com.lsc.notebook.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 15:26 2020/3/31
 */
public class PropertiesUtils {
    private static String PROPERTY_NAME = "application-dev.yml";

    public static String getCommonYml(Object key){
        Resource resource = new ClassPathResource(PROPERTY_NAME);
        Properties properties = null;
        try {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(resource);
            properties =  yamlFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return (String) properties.get(key);
    }

    public static void main(String[] args) {
        System.out.println(getCommonYml("download.filePath"));
    }

}
