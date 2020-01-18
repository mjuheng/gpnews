package com.gpnews.utils.result;

import com.gpnews.utils.JsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
public class ResultUtil {
    private static final String SUCCESS_CODE;
    private static final String SUCCESS_MESSAGE;
    private static final String ERROR_CODE;
    private static final String ERROR_MESSAGE;

    public ResultUtil() {
    }

    public static CommonResult successCommonResult() {
        return new CommonResult(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <T> SingleResult<T> successSingleResult(T t) {
        return new SingleResult(SUCCESS_CODE, SUCCESS_MESSAGE, t);
    }

    public static <T> SingleResult<T> successSingleResult(T t,String message) {
        return new SingleResult(SUCCESS_CODE, message, t);
    }

    public static <T> SingleResult<T> errorSingleResult(T t,String message) {
        return new SingleResult(ERROR_CODE, message, t);
    }

    public static <T> SingleResult<T> errorSingleResult(T t) {
        return new SingleResult(ERROR_CODE, ERROR_MESSAGE, t);
    }

    public static <T> SingleResult<T> errorSingleResult(String message) {
        return new SingleResult(ERROR_CODE, message);
    }

    public static <T> ListResult<T> errorListResult(List<T> dataList, String message) {
        return new ListResult(ERROR_CODE, SUCCESS_MESSAGE, dataList);
    }

    public static <T> ListResult<T> successListResult(List<T> dataList) {
        return new ListResult(SUCCESS_CODE, SUCCESS_MESSAGE, dataList);
    }

    public static <T> ListResult<T> successListResult(List<T> dataList, Integer currentPage, Integer rows, Integer total) {
        return new ListResult(SUCCESS_CODE, SUCCESS_MESSAGE, dataList, currentPage, rows, total);
    }
    public static <T> ListResult<T> successListResult(List<T> dataList, Integer currentPage, Integer rows, Integer total,List<T>meta) {
        return new ListResult(SUCCESS_CODE, SUCCESS_MESSAGE, dataList, currentPage, rows, total,meta);
    }


    public static void writeResponse(HttpServletResponse response, String code, String message) {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        try {
            response.getWriter().write(JsonUtil.serialize(new CommonResult(code, message)));
        } catch (IOException var4) {
            response.setStatus(500);
        }

    }
    static {
        SUCCESS_CODE = TemplateBootResultCodeEnum.SUCCESS.getCode();
        SUCCESS_MESSAGE = TemplateBootResultCodeEnum.SUCCESS.getMessage();
        ERROR_CODE = TemplateBootResultCodeEnum.ERROR.getCode();
        ERROR_MESSAGE = TemplateBootResultCodeEnum.ERROR.getMessage();
    }
}
