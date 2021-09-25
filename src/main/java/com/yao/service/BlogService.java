package com.yao.service;

import com.yao.po.Blog;
import com.yao.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Jack Yao on 2021/5/31 11:38 上午
 */
public interface BlogService {
    /*根據id查詢Blog*/
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    /*分頁查詢，返回一個Page對象，Blog類型，list表查詢一組數據*/
    /*查詢要傳遞的參數。除了pageable，還有查詢時送出的參數（封裝成Blog）*/
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    /*#37首頁最新推薦表格數據獲取*/
    List<Blog> listRecommendBlogTop(Integer size);

    /*新增*/
    Blog saveBlog(Blog blog);

    /*修改要先查詢到id才能改*/
    Blog updateBlog(Long id, Blog blog);

    /*刪除最簡單，返回空，以主鍵來刪除博客對象*/
    void deleteBlog(Long id);
}
/*這是BlogService接口*/