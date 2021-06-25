package com.yao.web;

import com.yao.service.BlogService;
import com.yao.service.TagService;
import com.yao.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/*web控制器*/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /*找路徑，製作分頁@PageableDefault*/
    @GetMapping("/")
    public Object index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));/*這可以寫在配置文件靈活的運用，這邊先寫死*/
        /*index的tag處理*/
        model.addAttribute("tags",tagService.listTagTop(10));/*這可以寫在配置文件靈活的運用，這邊先寫死*/
//        int i=9/0 ;
//        String blog = null;
//        if (blog == null){
//            throw new NotFoundException("博客不存在");/*要用throw不能用return*/
//        }
        System.out.println("------index------");
        return "index";/*返回index.html*/
    }
    @GetMapping("/blog")
    public Object blog() {

        System.out.println("------index------");
        return "blog";/*返回blog.html頁面*/
    }
}
