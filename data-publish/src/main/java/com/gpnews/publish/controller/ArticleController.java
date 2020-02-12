package com.gpnews.publish.controller;

import com.gpnews.pojo.User;
import com.gpnews.pojo.dto.ArticleDto;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.publish.enums.ArticleStatus;
import com.gpnews.publish.service.ArticleService;
import com.gpnews.pojo.Article;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @RequestMapping("")
    public CommonResult page(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        article.setUserId((String) SecurityUtils.getSubject().getSession().getAttribute("id"));
        List<ArticleVo> articles = service.pageExclContent(article, beginPubTime, endPubTime, currPage, rows);
        Integer count = service.count(article, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }

    @RequestMapping("/{id}")
    public CommonResult get(@PathVariable String id){
        Article article = service.load(id);
        if (article != null)
            return ResultUtil.successSingleResult(article);
        else
            return ResultUtil.successSingleResult(false, "没有此文章");
    }


    @RequestMapping("/add")
    public CommonResult add(@RequestBody @Valid ArticleDto articleDto){
        String message;
        articleDto.setUserId((String) SecurityUtils.getSubject().getSession().getAttribute("id"));
        if (articleDto.getDraft()){
            articleDto.setStatus(ArticleStatus.draft.getCode());
            message = "存入草稿成功";
        } else {
            articleDto.setStatus(ArticleStatus.approval.getCode());
            message = "文章发布成功";
        }
        service.insert(articleDto);
        return ResultUtil.successSingleResult(true, message);
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody @Valid ArticleDto articleDto){
        articleDto.setUserId((String) SecurityUtils.getSubject().getSession().getAttribute("id"));
        if (articleDto.getDraft()){
            articleDto.setStatus(ArticleStatus.draft.getCode());
        } else {
            articleDto.setStatus(ArticleStatus.approval.getCode());
        }
        articleDto.setModifiedTime(null);
        service.update(articleDto);
        return ResultUtil.successSingleResult(true, "修改成功");
    }

    @RequestMapping("/updateCommStatus")
    public CommonResult updateCommStatus(@RequestBody Article article){
        service.getMapper().updateByPrimaryKeySelective(article);
        return ResultUtil.successSingleResult(true);
    }

    @RequestMapping("/del/{id}")
    public CommonResult del(@PathVariable String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true, "文章删除成功");
    }
}
