package com.yao.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jack Yao on 2021/5/29 1:50 下午
 */

/*登陸攔截器
繼承並從寫*/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /*需要預處理，在請求還沒到達目的地之前要處理，用preHandle*/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /*攔截後希望的動作*/
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");/*重新定向到/admin路徑*/
            return false;/*不讓他往下執行*/
            /*訪問其他定址沒登陸就讓他回到admin*/
        }
        return true;/*繼續往下執行*/
    }
}
/*但是他還需要配置黨才知道要攔截什麼，也就是WebConfig.java*/