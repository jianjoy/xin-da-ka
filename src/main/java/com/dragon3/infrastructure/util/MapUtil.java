package com.dragon3.infrastructure.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, Object> paramsToMap(Object...params){
        Map<String, Object> data = new HashMap<>();
        for(int i = 0; i < params.length; i+=2){
            data.put((String)params[i], params[i+1]);
        }
        return data;
    }
}
