package com.yao.handler;
//用來攔截所有異常做統一處理

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice/*攔截掉所有有@Controller的控制器*/
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @ExceptionHandler(Exception.class)  /*用來標示這個方法可以做異常處理，(Exception.class)這個級別都可以攔截*/
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURI(),e);
        /*記錄異常訊息Requset URL，
        紀錄異常訊息Exception，傳遞requset.getRequestURL，這樣URL拿到會傳遞到左邊的大括號，
        這是error的用法，e在直接把異常輸出 會直接輸出在控制台*/

            /*但是因為我們攔截所有作物到自定義了，所以要做些判斷
            當有些異常標示這些狀態馬我們就不要處理它，讓他交給springboot本身處理異常，
            通過找註解的工具，如果不存在就拋出exception讓spring boot處理*/
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        ModelAndView mv = new ModelAndView();   /*返回錯誤頁面，new一個對象出來mv*/
        mv.addObject("url",request.getRequestURL());    /*對象中加一個對象 addobject希望放到”url”裡面*/
        mv.addObject("exception",e);    /*獲取異常訊息 “exception”,e*/
        mv.setViewName("error/error");  /*設定要返回哪個頁面setViewName “error/error”*/
        return mv;  /*最後記得返回不是一開始的null，那只是不想看到報錯先用null*/
    }
}
