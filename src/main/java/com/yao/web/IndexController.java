package com.yao.web;

import com.yao.service.BlogService;
import com.yao.service.TagService;
import com.yao.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*web控制器*/
@Slf4j
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
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));/*這可以寫在配置文件靈活的運用，指定為6條數據，這邊先寫死*/
        /*index的tag處理*/
        model.addAttribute("tags", tagService.listTagTop(10));/*這可以寫在配置文件靈活的運用，指定為10條數據，這邊先寫死*/
        /*#37首頁最新推薦表格數據獲取*/
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));/*指定為8條數據，這邊先寫死*/

        System.out.println("------index------");
        return "index";/*返回index.html*/
    }

    @PostMapping("/search")
    public String Search(@PageableDefault(size = 8, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        if (query == null) {
            log.warn("query is null.");
        }
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);

        return "search";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        log.info("------index------");
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";/*返回blog.html頁面*/
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }


}
