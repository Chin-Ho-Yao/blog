package com.yao.web.admin;

import com.yao.po.User;
import com.yao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author JackYao.com
 * @date 2021/5/28 8:14 上午
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping        /*請求的時候就會跳轉到admin/login這個頁面*/
    public String loginPage() {
        return "admin/login";
    }



    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,/*把帳密放到session*/
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);/*帳號密碼傳進來*/
        if (user != null) {
            user.setPassword(null);/*不要把密碼傳到前端去，在頁面拿到密碼很不安全，所以要這樣處理*/
            session.setAttribute("user", user);/*user放進來 session裡面就有user已經登錄了*/
            return "admin/index";/*然後回到登錄首頁*/
        } else {/*帳密不對的情況，返回登錄頁，不能用return admin/login這樣之後再次登錄路徑會有問題*/
            attributes.addFlashAttribute("message", "用戶名和密碼錯誤");/*用戶名密碼不對的話，這邊加上提示，用RedirectAttributes attributes*/
            /*這面跳出密碼錯誤提示不能用Model model model.addAttribute("Message")因為這邊路徑是返回所以會拿不到*/
            return "redirect:/admin";
        }
    }
    /*註銷當前登錄用戶*/
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
