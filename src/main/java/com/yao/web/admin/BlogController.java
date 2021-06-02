package com.yao.web.admin;

import com.yao.service.BlogService;
import com.yao.service.TypeService;
import com.yao.vo.BlogQuery;
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

    @Autowired
    private TypeService typeService;

    /*加上model*/
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){

        /*不需要用分頁方式獲取，直接獲取所有，在typeservice定義List<Type>，這邊就可以用listType*/
        model.addAttribute("types", typeService.listType());
        /*拿到type之後進blog.html渲染*/

                model.addAttribute("page", blogService.listBlog(pageable, blog));
        /*listBlog裡面的值，除了blog還要有pageable*/
        /*pageable:分頁的對象，要指定默認的參數@PageableDefault，不指定也可以*/
        /*按updateTime更新時間倒敘排序DESC*/
        /*blog:構造好的對象*/
        /*根據blogService.listBlog(pageable, blog)查詢page對象，放到model模型*/
        /*前端就可以拿到model模型進行數據的渲染*/
        return "admin/blogs";/*blogs方法就是希望訪問到頁面就能看到博客管理頁面*/
    }

    /*查詢才使用*/
    @GetMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){

        model.addAttribute("page", blogService.listBlog(pageable, blog));
        /*查詢完返回的內容改返回blogs下面的blogList片段，這個片段需要被定義*/
        return "admin/blogs :: blogList";
    }

}


