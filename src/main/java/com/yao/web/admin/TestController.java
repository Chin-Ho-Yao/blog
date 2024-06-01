package com.yao.web.admin;

import com.yao.po.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/*
 * @author Jack
 * @date 2023/4/1
 * */
@RestController
@CrossOrigin
public class TestController {
    @PostMapping("/test")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            System.out.println("test");
            return ResponseEntity.ok("test");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("login failed");
        }
    }

}
