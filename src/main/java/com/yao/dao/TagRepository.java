package com.yao.dao;

import com.yao.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jack Yao on 2021/5/29 3:57 下午
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    /*查詢Type*/
    Tag findByName(String name);
}
