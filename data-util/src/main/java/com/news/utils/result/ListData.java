package com.news.utils.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> dataList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer totalCount;
    private Integer rows;
    private  List<T> meta;

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public ListData() {
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getMeta() {
        return meta;
    }

    public void setMeta(List<T> meta) {
        this.meta = meta;
    }
}
