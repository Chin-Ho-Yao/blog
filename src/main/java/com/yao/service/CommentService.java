package com.yao.service;

import com.yao.po.Comment;

import java.util.List;

/**
 * Created by Jack Yao on 2021/9/18 9:34 下午
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
