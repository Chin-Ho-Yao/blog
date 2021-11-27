package com.yao.web.admin;

import com.yao.po.Tag;
import com.yao.service.TagService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Jack Yao on 2021/5/29 4:48 下午
 */
@Slf4j
@Controller
@RequestMapping("/admin")/*後台管理*/
public class TagController {

    @Autowired
    private TagService tagService;/*TagService注入就知道要傳什麼參數了*/

    @GetMapping("/tags")/*通過get訪問*/
    public String tags(@PageableDefault(size = 20, sort = {"name"}, direction = Sort.Direction.ASC)
                                Pageable pageable, Model model){
        /*根據前端頁面自動構造好的會封裝載pageable裡面，這是Spring Boot裡面封裝好的方法*/
        /*向每個分頁有多少參數排序，要用註解的方式指定參數size默認指定一頁十條*/
        /*根據id排序，倒序DESC排序*/
        /*前端頁面展示模型model*/
        /*要傳Pageable，要選擇springframework.data.domain的*/
        model.addAttribute("page", tagService.listTag(pageable));
        /*這樣查出來的東西放到前端展示模型，這樣模型就能拿到前端頁面對象，來做頁面分頁處理*/

        return "admin/tags";/*返回頁面*/
    }
    @GetMapping("/tags/input")         /*tags.html的href="#" th:href="@{/admin/tags/input點擊後會跳到這}" */
    public String input(Model model){              /*返回新增的頁面*/
        model.addAttribute("tag", new Tag());
    	log.info(" - XXXXX - new Tag() - OOOOO - : "+model);
        return "admin/tags-input";     /*返回這個檔案*/
    }

    /*查到修改的紀錄，@GetMapping要另外傳一個id，要加上@PathVariable才能把id才可以把id接收過來*/
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        /*根據id查詢的tag*/
        model.addAttribute("tag", tagService.getTag(id));
        /*這樣要修改的tag，就會放到model，然後返回頁面上*/

        return "admin/tags-input";
    }


    @PostMapping("/tags")              /*這邊是用post所以不會衝突*/
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
    	log.info(" - VVVVV - post tags - OOOOO - ");
        /*先查詢，傳遞過來的tag.getName*/
        Tag tag1 = tagService.getTagByName(tag.getName());
        /*根據tag檢查有沒有這個Name，有就不通過，返回錯誤，用result，手動加錯誤驗證。@NotBlank是綁定的驗證*/
        if(tag1 != null){
            /*加個驗證的結果:驗證name，要跟@NotBlank名字一樣,驗證結果nameError,
            最終返回的消息（給前端），在tags-input.html的提交信息不符合规则接收*/
            result.rejectValue("name", "nameError", "不能重複添加分類");
        }

        if (result.hasErrors()){
            log.info(" - _____ - return \"admin/tags-input\" - OOOOO - ");
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);        /*傳遞對象自動會把name封裝到Tag對象裡面*/
    	log.info(" - OOOOO - t - OOOOO - : " + t);
        if(t == null){
            attributes.addFlashAttribute("message", "新增失敗");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        log.info(" - _____ - return \"redirect:/admin/tags\" - OOOOO - ");
        return "redirect:/admin/tags";         /*redirect重定向*/
    }

    /*提交後修改，傳進來的路徑要有id， tag後面一定加BindingResult，他們是bonding*/
    /*如果中間有加其他東西隔開，那這個校驗就沒效果了，所以要加其他參數要加在BindingResult後面*/
    /*加一個Long id，根據它來修改tag，一樣要加上@PathVariable才能把@PostMapping的id傳進來*/
    /*然後用在tagService.updateTag(id, tag);裡面*/
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
    	log.info(" - VVVVV - editPost - OOOOO - ");
    	
    	Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name", "nameError", "不能重複添加分類");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        /*以上一樣先校驗*/

        /*這邊就不同了，用update，然後用id傳進來*/
        Tag t = tagService.updateTag(id, tag);
        if(t == null){
            attributes.addFlashAttribute("message", "更新失敗");
        }else {
            attributes.addFlashAttribute("message", "更新成功");
        }
    	log.info(" - _____ - editPost - OOOOO - ");
        return "redirect:/admin/tags";
    }

    /*刪除，一樣透過id。加上校驗attributes*/
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        /*直接透過id刪除tag*/
        tagService.deleteTag(id);
        /*刪除後沒拋錯就直接出現這個訊息*/
        attributes.addFlashAttribute("message", "刪除成功");

    	log.info(" - XXXXX - delete id- OOOOO - : " + id);
    	log.info(" - XXXXX - attributes- OOOOO - : " + attributes);

        /*返回列表*/
        return "redirect:/admin/tags";
    }

}
