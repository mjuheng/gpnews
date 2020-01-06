package com.news.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String create(){
        String id=UUID.randomUUID().toString();
        return  id.replace("-","");
    }

}
