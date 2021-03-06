package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.BaseService;
import com.gpnews.pojo.BaseEntity;
import com.gpnews.utils.UUIDUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Override
    public T insert(T t) {
        if (t instanceof BaseEntity){
            if (((BaseEntity) t).getId()==null){
                ((BaseEntity) t).setId(UUIDUtil.create());
            }
            if (((BaseEntity) t).getCreatedTime()==null){
                ((BaseEntity) t).setCreatedTime(new Date());
            }
            if (((BaseEntity) t).getModifiedTime()==null){
                ((BaseEntity) t).setModifiedTime(new Date());
            }
        }
        getMapper().insert(t);
        return t;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean insertBatch(List<T> list) {
        for (T entity: list){
            if(insert(entity)==null){
                return false;
            }
        }
        return true;
    }

    public  T update(T t){
        if (t instanceof BaseEntity){
            ((BaseEntity) t).setModifiedTime(new Date());
        }
        getMapper().updateByPrimaryKey(t);
        return t;
    }

    public T updateNoNull(T t){
        if (t instanceof BaseEntity){
            ((BaseEntity) t).setModifiedTime(new Date());
        }
        getMapper().updateByPrimaryKeySelective(t);
        return t;
    }

    @Transactional(rollbackFor=Exception.class)
    public Boolean updateBatch(List<T> list){
        for (T entity:list){
            if(updateNoNull(entity)==null){
                return false;
            }
        }
        return true;
    }

    public T load(String id){
        return getMapper().selectByPrimaryKey(id);
    }

    public void delById(String id){
        String[] ids=id.split(",");
        for (int i = 0; i < ids.length; i++) {
            getMapper().deleteByPrimaryKey(ids[i]);
        }
    }
}
