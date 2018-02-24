package com.lzq.econnect.manager;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by lzq on 2017/4/10.
 */

public class JsonManager {

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
