package com.yankee.elasticsearch.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/14
 */
public class JsonUtil {
    public static Map<String, Object> parseJsonToMap(String json) {
        Map<String, Object> map = new HashMap<>();
        // 最外层解析
        JSONObject jsonObject = JSONObject.parseObject(json);
        for (String k : jsonObject.keySet()) {
            Object v = jsonObject.get(k);
            // 如果内层是数组，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<>();
                for (Object next : (JSONArray) v) {
                    list.add(parseJsonToMap(next.toString()));
                }
                map.put(k, list);
            } else {
                map.put(k, v);
            }
        }
        return map;
    }
}
