package com.gpnews.utils;

public class PageUtil {

    public static int getStart(Integer page, Integer rows) {
        return null != page && null != rows && page.intValue() >= 1 && rows.intValue() >= 1?(page.intValue() - 1) * rows.intValue():0;
    }
}
