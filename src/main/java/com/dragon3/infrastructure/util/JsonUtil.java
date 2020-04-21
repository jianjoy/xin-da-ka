package com.dragon3.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static String stringify(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    public static String stringify(Object ...params){
        Map<String, Object> map = MapUtil.paramsToMap(params);
        return stringify(map);
    }

    public static <T> T parse(String content, Class<T> valueType){
        try {
            return mapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T getValue(String jsonStr, String key, Class<T> valueType){
        try {
            Map<String, Object> json = parse(jsonStr, Map.class);
            Object value = json.get(key);
            return (T)value;
        }catch(Exception e){
            return null;
        }
    }
}
