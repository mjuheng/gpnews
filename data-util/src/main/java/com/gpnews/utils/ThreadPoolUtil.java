package com.gpnews.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author HuangChongHeng
 * @date 2020/4/5
 */
public class ThreadPoolUtil {
    private static ExecutorService service;
    static {
        service = Executors.newFixedThreadPool(5);
    }

    public static ExecutorService instance(){
        return service;
    }
}
