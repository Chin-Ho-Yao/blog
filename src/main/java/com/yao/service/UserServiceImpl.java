package com.yao.service;

import com.yao.dao.UserRepository;
import com.yao.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JackYao.com
 * @date 2021/5/28 7:35 上午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        /*查詢數據庫有無該名字密碼*/
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
}
