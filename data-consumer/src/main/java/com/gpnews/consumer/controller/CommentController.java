package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.ArticleService;
import com.gpnews.consumer.service.CommentService;
import com.gpnews.consumer.service.MsgService;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.Comment;
import com.gpnews.pojo.Msg;
import com.gpnews.pojo.vo.CommentVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private MsgService msgService;

    @GetMapping("")
    public CommonResult page(CommentVo comment, String endCreatedTime, Integer currPage, Integer rows) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comment.setCreatedTime(sdf.parse(endCreatedTime));
        List<CommentVo> list = service.page(comment, currPage, rows);
        Integer count = service.count(comment);
        return ResultUtil.successListResult(list, currPage, rows, count);
    }

    @RequiresAuthentication
    @PostMapping("/add")
    public CommonResult add(@RequestBody Comment comment){
        String currId = ShiroUtil.getCurrUserId();
        comment.setUserId(currId);
        comment = service.insert(comment);
        CommentVo commentVo = service.getById(comment.getId());

        articleService.addNum(comment.getArticleId(), 2);

        // 插入信息
        Article article = articleService.load(commentVo.getArticleId());
        Msg msg = new Msg("有人评论了你的新闻",
                            article.getUserId(),
                            currId, "用户" + commentVo.getUsername() + "评论了你的新闻《" + article.getTitle() + "》", 2, false);
        msgService.insert(msg);

        return ResultUtil.successSingleResult(commentVo);
    }

}
