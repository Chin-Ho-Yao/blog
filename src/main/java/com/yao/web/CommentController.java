package com.yao.web;

import com.yao.po.Comment;
import com.yao.po.User;
import com.yao.service.BlogService;
import com.yao.service.CommentService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Jack Yao on 2021/9/18 6:27 下午
 */
@Slf4j
@Controller
public class CommentController {
    private final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        log.info(" - OOOOO - blogId - OOOOO - : " + blogId);
        log.info(" - OOOOO - model - OOOOO - : " + model);
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        log.info(" - OOOOO - model - OOOOO - : " + model);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        log.info(" - XXXXX - comment - OOOOO - : " + comment);
        Long blogId = comment.getBlog().getId();
        log.info(" - XXXXX - blogId - OOOOO - : " + blogId);
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User)session.getAttribute("user");
        log.info(" - XXXXX - user - OOOOO - : " + user);
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);
        log.info(" - XXXXX - commentService - OOOOO - : " + commentService);
        return "redirect:/comments/" + blogId;
    }
}
