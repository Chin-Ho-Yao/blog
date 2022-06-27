package com.yao.web;

import com.yao.po.Type;
import com.yao.service.BlogService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Jack Yao on 2021/9/22 10:06 下午
 */
@Slf4j
@Controller
public class TypeShowController {
    private final Logger log = LoggerFactory.getLogger(TypeShowController.class);

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String type(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){

        List<Type> types = typeService.listTypeTop(10000);

        if (id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        log.info(" - XXXXX - model - OOOOO - : " + model);


        log.info(" - XXXXX - return types - OOOOO - : ");
        return "/types";
    }


}
