package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.enums.ArticleStatus;
import com.gpnews.admin.service.ArticleService;
import com.gpnews.admin.service.MsgService;
import com.gpnews.admin.service.VisitsService;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.Msg;
import com.gpnews.pojo.dto.ArticleDto;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@RequiresAuthentication
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;
    @Autowired
    private VisitsService visitsService;
    @Autowired
    private MsgService msgService;

    @SystemLog("文章分页查询")
    @RequestMapping("")
    public CommonResult page(ArticleVo article, String userId, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        // 判断权限
        if (userId != null){
            if (!SecurityUtils.getSubject().isPermitted("articleReview") && !ShiroUtil.getCurrUserId().equals(userId)){
                throw new ShiroException();
            }
        }
        article.setUserId(userId);
        List<ArticleVo> articles = service.pageExclContent(article, beginPubTime, endPubTime, currPage, rows);
        Integer count = service.count(article, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }

    @SystemLog("添加文章")
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
        articleDto.setIsComment(true);
        articleDto.setReadNum(0);
        articleDto.setCommentNum(0);
        service.insert(articleDto);
        return ResultUtil.successSingleResult(true, message);
    }

    @SystemLog("修改文章评论状态")
    @RequestMapping("/updateCommStatus")
    public CommonResult updateCommStatus(@RequestBody Article article){
        service.getMapper().updateByPrimaryKeySelective(article);
        return ResultUtil.successSingleResult(true);
    }

    @SystemLog("更新文章")
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

    @SystemLog("审核文章")
    @RequestMapping("/changeStatus")
    public CommonResult changeStatus(String id, Integer status, String comment){
        Article article = service.load(id);
        article.setStatus(status);
        if (status == 2){
            article.setPublishTime(new Date());
            visitsService.addPublish(id);
            msgService.insert(new Msg("文章《"+  article.getTitle() + "》审核通过", article.getUserId(), "您的文章《" + article.getTitle() + "》审核通过", 2, false));
        } else if (status == 3) {
            msgService.insert(new Msg("文章《" + article.getTitle() + "》审核失败", article.getUserId(), "您的文章《" + article.getTitle() + "》审核不通过,原因是：" + comment, 2, false));
        }
        service.updateNoNull(article);
        return ResultUtil.successSingleResult(true);
    }

    // 获取文章内容
    @RequestMapping("/{id}")
    public CommonResult getById(@PathVariable("id") String id){
        Article article = service.load(id);
        if (article != null)
            return ResultUtil.successSingleResult(article);
        else
            return ResultUtil.successSingleResult(false, "没有此文章");
    }

    // 获取用户总阅读数和回复数
    @RequestMapping("/getReadAndComm")
    public CommonResult getReadAndComm(@NotNull String userId){
        return ResultUtil.successSingleResult(service.countReadAndComm(userId));
    }

    @RequestMapping("/eachCount")
    public CommonResult eachCount(){
        return ResultUtil.successSingleResult(service.eachCount());
    }
}
