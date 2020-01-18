package com.gpnews.utils.result;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
public class ListResult<T> extends CommonResult{
    private static final long serialVersionUID = 1L;
    private ListData<T> data;

    public ListResult() {
    }

    public ListResult(String code, String message) {
        super(code, message);
    }

    public ListResult(String code, String message, List<T> dataList) {
        super(code, message);
        this.data = new ListData();
        this.data.setDataList(dataList);
        this.data.setCurrentPage(Integer.valueOf(1));
        this.data.setTotalPage(Integer.valueOf(1));
    }

    public ListResult(String code, String message, List<T> dataList, Integer currentPage, Integer rows, Integer total) {
        super(code, message);
        this.data = new ListData();
        this.data.setDataList(dataList);
        if(null == currentPage) {
            currentPage = Integer.valueOf(1);
        }

        this.data.setCurrentPage(currentPage);
        this.data.setTotalPage(Integer.valueOf(getTotalPage(rows, total)));
        this.data.setTotalCount(total);
        this.data.setRows(rows);
    }
    public ListResult(String code, String message, List<T> dataList, Integer currentPage, Integer rows, Integer total,List<T>meta) {
        super(code, message);
        this.data = new ListData();
        this.data.setDataList(dataList);
        this.data.setMeta(meta);
        if(null == currentPage) {
            currentPage = Integer.valueOf(1);
        }

        this.data.setCurrentPage(currentPage);
        this.data.setTotalPage(Integer.valueOf(getTotalPage(rows, total)));
        this.data.setTotalCount(total);
        this.data.setRows(rows);
    }

    public static int getTotalPage(Integer rows, Integer total) {
        if(null != rows && null != total && rows.intValue() >= 1 && total.intValue() >= 1 && rows.intValue() < total.intValue()) {
            int totalPage = total.intValue() / rows.intValue();
            if(total.intValue() % rows.intValue() > 0) {
                ++totalPage;
            }

            return totalPage;
        } else {
            return 1;
        }
    }

    public ListData<T> getData() {
        return this.data;
    }

    public void setData(ListData<T> data) {
        this.data = data;
    }
}
