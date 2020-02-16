package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.ArticleService;
import com.gpnews.consumer.service.CommentService;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.Comment;
import com.gpnews.pojo.vo.CommentVo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/14
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("")
    public CommonResult page(CommentVo comment, String endCreatedTime, Integer currPage, Integer rows) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comment.setCreatedTime(sdf.parse(endCreatedTime));
        List<CommentVo> list = service.page(comment, currPage, rows);
        Integer count = service.count(comment);
        return ResultUtil.successListResult(list, currPage, rows, count);
    }

    @PostMapping("/add")
    public CommonResult add(Comment comment){
        // todo: 身份验证
        comment.setUserId("1");
        service.insert(comment);
        Article article = articleService.load(comment.getArticleId());
        article.setCommentNum(article.getCommentNum() + 1);
        articleService.update(article);
        return ResultUtil.successSingleResult(true, "评论成功");
    }
}
