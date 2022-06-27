package com.yao.web.admin;

import com.yao.po.Blog;
import com.yao.po.User;
import com.yao.service.BlogService;
import com.yao.service.TagService;
import com.yao.service.TypeService;
import com.yao.vo.BlogQuery;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Jack Yao on 2021/5/28 10:07 下午
 */

@Slf4j
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    /*放一組數據過來*/
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /*加上model*/
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
    	log.debug(" - VVVVV - blogs - OOOOO - ");
    	/*BQ為查詢用*/
        /*不需要用分頁方式獲取，直接獲取所有，在typeservice定義List<Type>，這邊就可以用listType*/
        model.addAttribute("types", typeService.listType());
        /*拿到type之後進blog.html渲染*/

        model.addAttribute("page", blogService.listBlog(pageable, blog));
        log.debug(" - OOOOO - model - OOOOO - : " + model);
        /*listBlog裡面的值，除了blog還要有pageable*/
        /*pageable:分頁的對象，要指定默認的參數@PageableDefault，不指定也可以*/
        /*按updateTime更新時間倒敘排序 DESC*/
        /*blog:構造好的對象*/
        /*根據blogService.listBlog(pageable, blog)查詢page對象，放到model模型*/
        /*前端就可以拿到model模型進行數據的渲染*/
    	log.debug(" - _____ - blogs return \"admin/blogs\" - OOOOO - ");
        return "admin/blogs";/*blogs方法就是希望訪問到頁面就能看到博客管理頁面*/
    }

    /*博客點擊新增，返回到博客新增頁面admin/blogs-input*/
    @GetMapping("/blogs/input")
    public String input(Model model){
        /*分類初始化*/
        model.addAttribute("types",typeService.listType());
        /*初始化，修改的時候頁面要初始化取值*/
        model.addAttribute("blog",new Blog());
        /*初始化*/
        model.addAttribute("tags",tagService.listTag());
        /*初始化之後就可以在model拿到數據*/
        return INPUT;
    }

    private  void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        /*初始化，把tagIds處理成字符串*/
        blog.init();
        /*初始化，修改的時候頁面要初始化取值，這樣就拿到tagIds*/
        model.addAttribute("blog", blog);
        /*初始化之後就可以在model拿到數據*/
        return INPUT;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){/*接收Blog對象，session有user用來設置blog*/
        /*當前登入用戶從session拿到user*/
        blog.setUser((User) session.getAttribute("user"));
        /*blogs-input過來的是type.id根據ID初始化type*/
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        /*返回blog對象b*/
//        Blog b = blogService.saveBlog(blog);
        Blog b;

        /*判斷拿到blog有id的話就調用updateBlog方法，先查詢到已有的對象在更新*/
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        /*非空校驗，彈出訊息*/
        if (blog == null) {
            attributes.addFlashAttribute("message","操作失敗");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;/*最後返回列表頁面*/

    }

    /*查詢，上一頁，下一頁才使用*/
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
    	log.debug(" - VVVVV - search - OOOOO - ");
    	
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        log.debug(" - OOOOO - model - OOOOO - : " + model);
        log.debug(" - _____ - search return \"admin/blogs :: blogList - OOOOO - ");
        /*查詢完返回的內容改返回blogs下面的blogList片段，這個片段需要被定義*/
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(RedirectAttributes attributes,@PathVariable Long id){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","刪除成功");
        return REDIRECT_LIST;
    }


}


