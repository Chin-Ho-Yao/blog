package com.yao.dao;

import com.yao.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JackYao.com
 * @date 2021/5/28 7:39 上午
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}
