package com.wuyang.yygh.hosp.util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHelper {
    /**
     * 将字符绑定字符数组转换成，字符绑定成Object的形式
     * @param paramMap
     * @return
     */
	public static Map<String, Object> switchMap(Map<String, String[]> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            resultMap.put(param.getKey(), param.getValue()[0]);
        }
        return resultMap;
    }
}