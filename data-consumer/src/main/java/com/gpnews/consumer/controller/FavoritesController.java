package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.FavoritesService;
import com.gpnews.pojo.Favorites;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/17
 */
@RequiresAuthentication
@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesService service;

    @RequestMapping("")
    public CommonResult page(String title, String endCreatedTime, Integer currPage, Integer rows){
        String userId = ShiroUtil.getCurrUserId();
        List<ArticleVo> dataList = service.page(userId, title, endCreatedTime, currPage, rows);
        Integer count = service.count(userId, title, endCreatedTime);
        return ResultUtil.successListResult(dataList, currPage, rows, count);
    }


    @RequestMapping("/add")
    public CommonResult add(@NotNull String articleId){
        String id = ShiroUtil.getCurrUserId();
        Favorites favorites = new Favorites(id, articleId);
        service.insert(favorites);
        return ResultUtil.successSingleResult(true);
    }

    @RequestMapping("/isFavorites")
    public CommonResult isFavorites(@NotNull String articleId){
        String id = ShiroUtil.getCurrUserId();
        boolean ret = service.isFavorites(id, articleId);
        return ResultUtil.successSingleResult(ret);
    }


    @RequestMapping("/del")
    public CommonResult del(@NotNull String articleId){
        String id = ShiroUtil.getCurrUserId();
        service.del(id, articleId);
        return ResultUtil.successSingleResult(true);
    }

}
