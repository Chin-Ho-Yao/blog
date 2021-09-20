package com.yao.web;

import com.yao.po.Comment;
import com.yao.service.BlogService;
import com.yao.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Jack Yao on 2021/9/18 6:27 下午
 */
@Slf4j
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        log.info(" - VVVVV - main - XXXXX - ");
        log.info(" - XXXXX - model - OOOOO - : " + model);
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        log.info(" - XXXXX - model - OOOOO - : " + model);
        log.info(" - _____ - main - XXXXX - ");
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
