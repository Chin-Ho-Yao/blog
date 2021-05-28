package com.yao.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/*web控制器*/
@Controller
public class indexController {
    /*找路徑*/
    @GetMapping("/")
    public Object index() {
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
