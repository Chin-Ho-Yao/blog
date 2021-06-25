package com.yao.dao;

import com.yao.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Jack Yao on 2021/5/29 3:57 下午
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    /*查詢Type*/
    Tag findByName(String name);

    /*index的tag處理*/
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
