package com.yankee.common.utils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Yankee
 * @date 2021/11/7 17:38
 */
public class PropertiesUtils {
    /**
     * 配置属性集合
     */
    private Properties properties = null;

    /**
     * 私有构造方法
     */
    public PropertiesUtils(String fileName) {
        readProperties(fileName);
    }

    /**
     * 读取配置文件
     *
     * @param fileName 配置文件名称
     */
    private void readProperties(String fileName) {
        try {
            properties = new Properties();
            InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * 获取所有的key和value
     *
     * @return map
     */
    public Map<?, ?> getAllWithMap() {
        HashMap<String, String> map = new HashMap<>();
        Enumeration<?> enu = properties.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = properties.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取所有的key和value
     *
     * @return prop
     */
    public Properties getAllWithProperties() {
        Properties prop = new Properties();
        Enumeration<?> enu = properties.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = properties.getProperty(key);
            prop.put(key, value);
        }
        return prop;
    }
}
