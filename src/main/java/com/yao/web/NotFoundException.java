package com.yao.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author JackYao.com
 * @date 2021/5/26 12:08 下午
 */

@ResponseStatus(HttpStatus.NOT_FOUND)   /**/
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
