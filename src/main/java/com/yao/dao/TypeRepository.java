package com.yao.dao;

import com.yao.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jack Yao on 2021/5/29 3:57 下午
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    /*查詢Type*/
    Type findByName(String name);
}
