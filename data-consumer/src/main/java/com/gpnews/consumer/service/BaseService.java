package com.gpnews.consumer.service;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
public interface BaseService<T> {
    Mapper<T> getMapper();

    /**
     * 插入数据
     * @param t
     * @return
     */
    T insert(T t);

    /**
     * 批量插入数据
     * @param list
     * @return
     */
    Boolean insertBatch(List<T> list);

    /**
     * 更新全部数据
     * @param t
     * @return
     */
    T update(T t);

    /**
     * 更新不为空的数据
     * @param t
     * @return
     */
    T updateNoNull(T t);

    /**
     * 批量更新数据
     * @param list
     * @return
     */
    Boolean updateBatch(List<T> list);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    T load(String id);

    /**
     * 根据id删除
     * @param id
     */
    void delById(String id);
}
