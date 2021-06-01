package com.yao.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Jack Yao on 2021/5/29 2:03 下午
 */
/*登錄攔截器的配置*/

@Configuration/*加這個註解才是配置累springboot才會認定他是配置才會啟用*/
public class WebConfig extends WebMvcConfigurerAdapter {
    /*重寫addInterceptors這個方法*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")/*過濾路徑，不管admin後面是什麼都攔截，
這樣訪問admin就會一直循環，還有表單提交時訪問的事admin/login這樣就提交不上去了，所以要排除掉一些路徑*/
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
/*所以沒登錄情況下會跳轉到admin無法進去blogs，登錄後因為session是共享所以可以跳到blogs*/
