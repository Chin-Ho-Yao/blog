package com.yao.web.admin;

import com.yao.po.Blog;
import com.yao.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jack Yao on 2021/5/28 10:07 下午
 */

@Controller
@RequestMapping("/admin")
public class BlogController {

    /*放一組數據過來*/
    @Autowired
    private BlogService blogService;

    /*加上model*/
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        /*listBlog裡面的值，除了blog還要有pageable*/
        /*pageable:分頁的對象，要指定默認的參數@PageableDefault，不指定也可以*/
        /*按updateTime更新時間倒敘排序DESC*/
        /*blog:構造好的對象*/
        /*根據blogService.listBlog(pageable, blog)查詢page對象，放到model模型*/
        /*前端就可以拿到model模型進行數據的渲染*/
        return "admin/blogs";/*blogs方法就是希望訪問到頁面就能看到博客管理頁面*/
    }

}


