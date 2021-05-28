package com.yao.service;

import com.yao.po.User;

/**
 * @author JackYao.com
 * @date 2021/5/28 7:33 上午
 */
public interface UserService {
    User checkUser(String username, String password);
}
