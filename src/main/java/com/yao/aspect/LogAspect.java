package com.yao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author JackYao.com
 * @date 2021/5/26 1:37 下午
 */

/*前面要有@Aspect才可以進行切面的操作@Component開啟組建掃描才能掃描到他*/
@Aspect
@Component
public class LogAspect {

    /*日誌記錄器*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*流是一個線狀過程，所以我們要在這過程切一刀，就是定義一個切面，定義一個方法log可以是空的，
    通過註解@Pointcut(“execution()”)聲明他是一個切面，
    括號內容規定他是攔截哪一些類，我們希望攔截web下所有web控制器，web下面所有類的所有方法*/
    @Pointcut("execution(* com.yao.web.*.*(..))")
    public void log(){}

    /*web之前記錄info，@Before("log()”)，括號裡代表log()這個方法，代表在log()之前這個切面之前執行*/
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        /*在從attribute中拿到request*/
        HttpServletRequest request = attributes.getRequest();
        /*拿到request就可以傳遞參數 ip*/
        String url = request.getRequestURI().toString();
        String ip = request.getRemoteAddr();
        /*可以拿到定義的*/
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        /*請求的參數，也是通過joinpoin拿到*/
        Object[] args = joinPoint.getArgs();
        /*工作對象，把參數傳遞過來*/
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        /*這樣直接輸出就可以了*/
        logger.info("Request : {}", requestLog);
//        logger.info("------doBefore------");
    }

    @After("log()")
    public void doAfter(){
//        logger.info("------doAfter------");
    }

    /*方法返回，返回之後攔截他，定義一個對象類型result
註解    @AfterReturning 裡面定義參數，返回result這個參數作為他的直，才能透過這個參數捕獲
還有切面log()
定義 result作為他的值，才能通過這參數捕獲這方法內容，還有切面*/
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        /*這樣獲取返回內容*/
        logger.info("Result ; {}" + result);
    }


    private  class RequestLog{
        private  String url;
        private  String ip;
        private  String classMethod;
        private  Object[] args;     /*對象數組因為可能是複雜的數據類型*/

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
